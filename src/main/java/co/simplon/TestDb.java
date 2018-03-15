package co.simplon;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class TestDb {


	public static void main(String[] args) {

		// connection string for local DB (PostgreSQL)
		String[] connectStringLocalDb= {"jdbc:postgresql://localhost:5432/Cities", "postgres", "admin"};

		// connection string for remote DB (PostgreSQL hosted on ElephantSQL)
		//String[] connectStringElSqlDb= {"jdbc:postgresql://horton.elephantsql.com:5432/cmfyzzed", "cmfyzzed", "QTbepW2yi-z0DIYYABKY2gvDNcavPHbD"};

		System.out.println("test connexion base locale : " + connectStringLocalDb[0]);

		try(Connection connLocal= DriverManager.getConnection(connectStringLocalDb[0], connectStringLocalDb[1], connectStringLocalDb[2])) {
			//Connection connLocal= DriverManager.getConnection(connectStringLocalDb[0], connectStringLocalDb[1], connectStringLocalDb[2]);
			System.out.println("connexion ouverte : " + connLocal);

			try (Statement stmt= connLocal.createStatement()) {

				/*
				// create and store city
				City myCity= new City("Chelles", 48.883, 2.6);
				try {
					myCity.setId(insertCity(myCity, stmt));
				} catch (SQLException e) {
					e.printStackTrace();
				}
				printAllCities(connLocal);

				// update a city
				myCity.setName("Tombouctou");
				myCity.setLatitude(5.9);
				try {
					updateCity(myCity, stmt);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				printAllCities(connLocal);
				*/
				
				// delete a city
				printAllCities(connLocal);
				deleteCity(new Long(15), stmt);
				printAllCities(connLocal);
				

			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			System.out.println("connexion à la base KO");
			e.printStackTrace();
		}

	}

	// method for printing all cities
	public static void printAllCities(Connection conn) {
		// list of cities to store select result
		ArrayList<City> listCities= new ArrayList<City>();

		try (Statement stmt= conn.createStatement()) {

			// select and print all cities
			try (ResultSet result= stmt.executeQuery("SELECT * FROM public.\"City\"")) {
				while (result.next()) {
					// add a City to the list
					listCities.add(new City(result.getLong("id"), result.getString("name"), result.getDouble("latitude"), result.getDouble("longitude")));
				}
				// print cities
				for (City cit : listCities) {
					System.out.println(cit);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// method for inserting a city
	public static Long insertCity(City pCity, Statement pStmt) {
		long res= 0;
		String sql= "INSERT INTO public.\"City\" (name, latitude, longitude) VALUES ('" + pCity.getName() + "'," + pCity.getLatitude() + "," + pCity.getLongitude() + ")";
		System.out.println(sql);
		try {
			pStmt.execute(sql, Statement.RETURN_GENERATED_KEYS);
			ResultSet rs= pStmt.getGeneratedKeys();
			rs.next();
			res= rs.getLong("id");
			System.out.println("Generated key = " + res);

		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		return (new Long(res));

	}

	public static void updateCity(City pCity, Statement pStmt) {
		String sql= "UPDATE public.\"City\" SET name= '" + pCity.getName() + "', latitude= " + pCity.getLatitude() + ", longitude= " + pCity.getLongitude() + " WHERE id=" + pCity.getId().longValue();
		System.out.println(sql);
		try {
			pStmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void deleteCity(Long id, Statement pStmt) {
		String sql= "DELETE FROM public.\"City\" WHERE id=" + id;
		System.out.println(sql);
		try {
			System.out.println("nb rows deleted = " + pStmt.executeUpdate(sql));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
