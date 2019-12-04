import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Specialite extends Table {
	Specialite(Connection con, Database db) {
	    super(con, db);
	  }

	public void ajoutSpecialite(int idArtiste, String specialite) {
		try{
	        PreparedStatement istm = connection.prepareStatement("INSERT into specialite_artiste VALUES (?, ?)");
	        istm.setInt(1, idArtiste);
	        istm.setString(2, specialite);
	        istm.executeQuery();
	        istm.close();
		} catch (SQLException e) {
	        System.err.println("failed");
	        e.printStackTrace(System.err);
	    }
	}
}
