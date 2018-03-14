import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestDb {

	public static void main(String[] args) {

		String[] connectStringLocalDb= {"jdbc:postgresql://localhost:5432/Cities", "postgres", "admin"};
		String[] connectStringElSqlDb= {"jdbc:postgresql://horton.elephantsql.com:5432/cmfyzzed", "cmfyzzed", "QTbepW2yi-z0DIYYABKY2gvDNcavPHbD"};


		/*
		try {
			System.out.println("test connexion base locale : " + connectStringLocalDb[0]);
			Connection connLocal= DriverManager.getConnection(connectStringLocalDb[0], connectStringLocalDb[1], connectStringLocalDb[2]);
			System.out.println("connexion ouverte : " + connLocal);
			connLocal.close();

		} catch (SQLException e) {
			System.out.println("connexion à la base KO");
			e.printStackTrace();
		}
		 */

		try {
			Connection connLocal= connectDB(connectStringLocalDb);
			connLocal.createStatement();
			connLocal.close();
			
		} catch(SQLException e) {
			System.out.println("connexion à la base KO");
			e.printStackTrace();
		}

		System.out.println();

		try {
			Connection connRemote= connectDB(connectStringElSqlDb);
			connRemote.close();
		} catch(SQLException e) {
			System.out.println("connexion à la base KO");
			e.printStackTrace();
		}

	}

	public static Connection connectDB(String[] connectStr) throws SQLException {
		System.out.println("test connexion base : " + connectStr[0]);
		try (Connection conn= DriverManager.getConnection(connectStr[0], connectStr[1], connectStr[2])) {
			System.out.println("connexion ouverte : " + conn);
		} catch ()
		//Connection conn= DriverManager.getConnection(connectStr[0], connectStr[1], connectStr[2]);
		return conn;
	}

}
