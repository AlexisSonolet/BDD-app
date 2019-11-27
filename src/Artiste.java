import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Artiste extends Table {
  Artiste(Connection con, Database db){
    super(con, db);
  }

  public void AjoutArtiste(int idArtiste, String nomArtiste, String prenomArtiste, String dateNaissance, String cirqueArtiste, String telephoneArtiste){
    try{
      Statement stmt = this.con.createStatement();
      stmt.executeQuery("INSERT into table artiste VALUES ('idArtiste', 'nomArtiste', 'prenomArtiste', 'dateNaissance', 'cirqueArtiste', 'telephoneArtiste')");
      stmt.close();
    } catch (SQLException e) {
      System.err.println("failed");
      e.printStackTrace(System.err);
    }
}
