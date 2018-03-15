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

				printAllCities(connLocal);

				// create and store city
					City myCity= new City("Chelles", 48.883, 2.6);
					try {
						insertCity(myCity, stmt);
					} catch (SQLException e) {
						e.printStackTrace();
					}
					printAllCities(connLocal);

			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			System.out.println("connexion à la base KO");
			e.printStackTrace();
		}

	}

	public static void printAllCities(Connection conn) {
		// list of cities to store select result
		ArrayList<City> listCities= new ArrayList<City>();

		try (Statement stmt= conn.createStatement()) {

			// select and print all cities
			try (ResultSet result= stmt.executeQuery("SELECT * FROM public.\"City\"")) {
				while (result.next()) {
					listCities.add(new City(result.getLong("id"), result.getString("name"), result.getDouble("latitude"), result.getDouble("longitude")));
				}
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

	public static boolean insertCity(City pCity, Statement pStmt) throws SQLException {
		boolean res= false;
		String myInsert= "INSERT INTO public.\"City\" (name, latitude, longitude) VALUES ('" + pCity.getName() + "'," + pCity.getLatitude() + "," + pCity.getLongitude() + ")";
		System.out.println(myInsert);
		try {
			res= pStmt.execute(myInsert, Statement.RETURN_GENERATED_KEYS);
			ResultSet rs= pStmt.getGeneratedKeys();
			rs.next();
			System.out.println("Generated key = " + rs.getLong("id"));

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}



}
