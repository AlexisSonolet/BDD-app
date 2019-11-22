import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class javaApp {
	public static void main(String[] args) {
		try {
			String url = "jdbc:oracle:thin:@ensioracle1.imag.fr:" + "1521:ensioracle1";
			String user = "sonoleta";
			String passwd = "asonolet";
			Connection connection = DriverManager.getConnection(url, user, passwd);
		} catch (SQLException e) {
			e. printStackTrace ();
		}
	}
}
