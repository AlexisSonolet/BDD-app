import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.*;

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

    public void suppressionSpecialite(int idArtiste, String specialite) {
        try {
	        PreparedStatement istm = connection.prepareStatement("DELETE FROM specialite_artiste WHERE idArtiste = ? AND specialiteArtiste = ?");
	        istm.setInt(1, idArtiste);
            istm.setString(2, specialite);
	        istm.executeQuery();
	        istm.close();
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
    }

    /**
     * Look for all artist' specialities and delete them
     * from the table.
     */
    public void suppAllSpecialite(int idArtiste) {
        try {
            ArrayList<String> specs = new ArrayList<String>();

            // Get the specialities
            PreparedStatement istm = connection.prepareStatement("SELECT specialiteArtiste FROM specialite_artiste WHERE idArtiste = ?");
            istm.setInt(1, idArtiste);
            ResultSet res = istm.executeQuery();

            while(res.next())
                specs.add(res.getString(1));

            istm.close();

            // Delete them
            for (String s : specs)
                this.suppressionSpecialite(idArtiste, s);

        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
    }
}
