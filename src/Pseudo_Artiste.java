import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.*;

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
            connection.commit();
		} catch (SQLException e) {
	        System.err.println("failed");
	        e.printStackTrace(System.err);
	    }
	}

    public void suppressionPseudo(int idArtiste, String pseudo) {
        try {
	        PreparedStatement istm = connection.prepareStatement("DELETE FROM pseudo_artiste WHERE idArtiste = ? AND pseudoArtiste = ?");
	        istm.setInt(1, idArtiste);
            istm.setString(2, pseudo);
	        istm.executeQuery();
	        istm.close();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
    }

    /**
     * Look for all artist's pseudos and delete them
     * from the table.
     */
    public void suppAllPseudo(int idArtiste) {
        try {
            ArrayList<String> pseudos = new ArrayList<String>();

            // Get the specialities
            PreparedStatement istm = connection.prepareStatement("SELECT pseudoArtiste FROM pseudo_artiste WHERE idArtiste = ?");
            istm.setInt(1, idArtiste);
            ResultSet res = istm.executeQuery();

            while(res.next())
                pseudos.add(res.getString(1));

            istm.close();

            // Delete them
            for (String s : pseudos)
                this.suppressionPseudo(idArtiste, s);

        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
    }

    public ArrayList<Integer> recherche_pseudo(String pseudo){
        try{
            PreparedStatement stm = connection.prepareStatement("SELECT idArtiste FROM pseudo_artiste WHERE pseudoArtiste = ?");
            stm.setString(1, pseudo);
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
