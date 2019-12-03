import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class javaApp {
	public static void main(String[] args) {
		try {
			String url = "jdbc:oracle:thin:@oracle1.imag.fr:" + "1521:oracle1";
			String user = "sonoleta";
			String passwd = "asonolet";
			Connection connection = DriverManager.getConnection(url, user, passwd);
		} catch (SQLException e) {
			e. printStackTrace ();
		}

        Database db = new Database();
        db.prepareArtist();
	}
}
