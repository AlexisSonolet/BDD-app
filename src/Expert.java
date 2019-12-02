import java.sql.*;

public class Expert extends Table {

    Expert(Connection con, Database db) {
        super(con, db);
    }

    /**
     * Registers Artist as an Expert if allowed. Raises Exception if not allowed.
     * @param idArtiste The id of the artist to be registered as an expert.
     */

    public void register(int idArtiste){
		try {
            // Check if this is actually an artist.
			PreparedStatement c1stm = connection.prepareStatement("SELECT * from Artistes WHERE idArtiste=?");
            c1stm.setString(1,""+idArtiste);
            ResultSet res1 = c1stm.executeQuery();
            if (!res1.next()){
                throw new IllegalArgumentException("Unknown artist");
            }
            // Check that the artist does not play in a show.
            PreparedStatement c2stm = connection.prepareStatement("SELECT * from Planning_Artiste WHERE idArtiste=?");
            c2stm.setString(1,""+idArtiste);
            ResultSet res2 = c1stm.executeQuery();
            if (res2.next()){
                throw new IllegalArgumentException("Artist already plays in a show");
            }
            // Check if this is not already an expert. (maybe not throw an exception but do nothing instead)
            PreparedStatement c3stm = connection.prepareStatement("SELECT * from Expert WHERE idExpert=?");
            c3stm.setString(1,""+idArtiste);
            ResultSet res3 = c3stm.executeQuery();
            if (res3.next()){
                throw new IllegalArgumentException("Already an expert");
            }
            // Insert idArtiste into the expert table.
            PreparedStatement istm = connection.prepareStatement("INSERT INTO Expert VALUES (?)");
            istm.setString(1, ""+idArtiste);
            istm.executeQuery();
		} catch (SQLException e){
			System.err.println("failed");
			e.printStackTrace(System.err);
        }
    }
}