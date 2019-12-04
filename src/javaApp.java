import java.sql.*;
public class javaApp {
	public static void main(String[] args) {
		try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			String url = "jdbc:oracle:thin:@Oracle1.ensimag.fr:" + "1521:oracle1";
			String user = "pereirap";
			String passwd = "pereirap";
			Connection connection = DriverManager.getConnection(url, user, passwd);

			Database db = new Database(connection);
			//db.prepareArtist();
			db.prepareSuppAllSpecialite();
            PreparedStatement c1stm = connection.prepareStatement("SELECT * from specialite_artiste");
            ResultSet res1 = c1stm.executeQuery();
            db.printTable(res1);
            res1.close();
            c1stm.close();
		} catch (SQLException e) {
			e. printStackTrace ();
		}
	}
}
