import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Pseudo_Artiste extends Table{
	Pseudo_Artiste(Connection con, Database db){
	    super(con, db);
	  }
	
	public void ajoutPseudoArtiste(int idArtiste, String pseudo) {
		try{
	        PreparedStatement istm = connection.prepareStatement("INSERT into pseudo_artiste VALUES (?, ?)");
	        istm.setInt(1, idArtiste);
	        istm.setString(2, pseudo);
	        istm.executeQuery();
	        istm.close();
		} catch (SQLException e) {
	        System.err.println("failed");
	        e.printStackTrace(System.err);
	    }
	}
}
