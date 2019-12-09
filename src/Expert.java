import java.sql.*;

public class Expert extends Table {

    Expert(Connection con, Database db) {
        super(con, db);
    }

    /**
     * Delete the expert from the table.
     */
    public void supprimerExpert(int idArtiste) {
        try {
	        PreparedStatement istm = connection.prepareStatement("DELETE FROM expert WHERE idExpert = ?");
	        istm.setInt(1, idArtiste);
	        istm.executeQuery();
            istm.close();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace(System.err);
            try {
                connection.rollback();
            } catch (SQLException e2) {
			    e2.printStackTrace();
                
            }
        }
    }

    /**
     * Registers Artist as an Expert if allowed. Raises Exception if not allowed.
     * @param idArtiste The id of the artist to be registered as an expert.
     */
    public void register(int idArtiste){
		try {
            // Check if this is actually an artist.
			PreparedStatement c1stm = connection.prepareStatement("SELECT * from artiste WHERE idArtiste=?");
            c1stm.setInt(1,idArtiste);
            ResultSet res1 = c1stm.executeQuery();
            if (!res1.next()){
                throw new IllegalArgumentException("Unknown artist");
            }
            c1stm.close();
            res1.close();
            // Check that the artist does not play in a show.
            PreparedStatement c2stm = connection.prepareStatement("SELECT * from Planning_Artiste WHERE idArtiste=?");
            c2stm.setInt(1, idArtiste);
            ResultSet res2 = c2stm.executeQuery();
            if (res2.next()){
                throw new IllegalArgumentException("Artist already plays in a show");
            }
            c2stm.close();
            res2.close();
            // Check if this is not already an expert. (maybe not throw an exception but do nothing instead)
            PreparedStatement c3stm = connection.prepareStatement("SELECT * from Expert WHERE idExpert=?");
            c3stm.setInt(1,idArtiste);
            ResultSet res3 = c3stm.executeQuery();
            if (res3.next()){
                throw new IllegalArgumentException("Already an expert");
            }
            c3stm.close();
            // Insert idArtiste into the expert table.
            PreparedStatement istm = connection.prepareStatement("INSERT INTO Expert VALUES (?)");
            istm.setInt(1, idArtiste);
            istm.executeQuery();
            istm.close();
            res3.close();
            connection.commit();
		} catch (SQLException e){
			System.err.println("failed");
			e.printStackTrace(System.err);
            try {
                connection.rollback();
            } catch (SQLException e2) {
			    e2.printStackTrace();
                
            }
        }
    }
}
