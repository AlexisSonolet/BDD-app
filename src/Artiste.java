import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;

public class Artiste extends Table {
  Artiste(Connection con, Database db){
    super(con, db);
  }

  public void ajoutArtiste(int idArtiste, String nomArtiste, String prenomArtiste, String dateNaissance, String cirqueArtiste, String telephoneArtiste){
    try{
        PreparedStatement istm = connection.prepareStatement("INSERT into artiste VALUES (?, ?, ?, TO_DATE(?, 'YYYY-MM-DD'), ?, ?)");
        istm.setInt(1, idArtiste);
        istm.setString(2, nomArtiste);
        istm.setString(3, prenomArtiste);
        istm.setString(4, dateNaissance);
        istm.setString(5, cirqueArtiste);
        istm.setString(6, telephoneArtiste);
        istm.executeQuery();
        istm.close();
	} catch (SQLException e) {
        System.err.println("failed");
        e.printStackTrace(System.err);
    }
  }
  
  public void suppressionArtiste(int idArtiste) {
	  try{
	        PreparedStatement istm = connection.prepareStatement("DELETE FROM artiste WHERE idArtiste = ?");
	        istm.setInt(1, idArtiste);
	        istm.executeQuery();
	        istm.close();
		} catch (SQLException e) {
	        System.err.println("failed");
	        e.printStackTrace(System.err);
	    }
  }
}
