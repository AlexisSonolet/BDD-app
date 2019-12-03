import java.sql.*;
public class javaApp {
	public static void main(String[] args) {
		try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			String url = "jdbc:oracle:thin:@Oracle1.ensimag.fr:" + "1521:oracle1";
			String user = "sonoleta";
			String passwd = "asonolet";
			Connection connection = DriverManager.getConnection(url, user, passwd);
			Database db = new Database(connection);
			db.prepareArtist();
		} catch (SQLException e) {
			e. printStackTrace ();
		}
	}
}
