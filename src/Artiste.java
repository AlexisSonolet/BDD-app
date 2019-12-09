import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
        istm.setString(5, cirqueArtiste.toLowerCase());
        istm.setString(6, telephoneArtiste);
        istm.executeQuery();
        istm.close();
        connection.commit();
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
            connection.commit();
		} catch (SQLException e) {
	        System.err.println("failed");
	        e.printStackTrace(System.err);
	    }
  }

  public ArrayList<Integer> recherche_prenom(String prenom){
    try{
      PreparedStatement stm = connection.prepareStatement("SELECT idArtiste FROM artiste WHERE prenomArtiste = ?");
      stm.setString(1, prenom);
      ResultSet res = stm.executeQuery();
      ArrayList<Integer> artists = new ArrayList<Integer>();
      while (res.next()){
        artists.add(res.getInt(1));
      }
      res.close();
      stm.close();
      return artists;
    } catch (SQLException e){
      System.err.println("failed");
      e.printStackTrace(System.err);
      return new ArrayList<Integer>();
    }
  }

  public ArrayList<Integer> recherche_nom(String nom){
    try{
      PreparedStatement stm = connection.prepareStatement("SELECT idArtiste FROM artiste WHERE nomArtiste = ?");
      stm.setString(1, nom);
      ResultSet res = stm.executeQuery();
      ArrayList<Integer> artists = new ArrayList<Integer>();
      while (res.next()){
        artists.add(res.getInt(1));
      }
      res.close();
      stm.close();
      return artists;
    } catch (SQLException e){
      System.err.println("failed");
      e.printStackTrace(System.err);
      return new ArrayList<Integer>();
    }
  }
}
