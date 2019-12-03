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
        PreparedStatement istm = connection.prepareStatement("INSERT into table artiste VALUES ('?', '?', '?', '?', '?', '?');");
        istm.setString(0, ""+idArtiste);
        istm.setString(1, ""+nomArtiste);
        istm.setString(2, ""+prenomArtiste);
        istm.setString(3, ""+dateNaissance);
        istm.setString(4, ""+cirqueArtiste);
        istm.setString(5, ""+telephoneArtiste);
        istm.executeQuery();
        istm.close();
	} catch (SQLException e) {
        System.err.println("failed");
        e.printStackTrace(System.err);
    }
  }
}
