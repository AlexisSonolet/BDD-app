import java.sql.*;

public class Expert extends Table {

    Expert(Connection con, Database db) {
        super(con, db);
    }

    public void register(int idArtiste){
		// Check if this is actually an artist :

		try {
			PreparedStatement stm = connection.prepareStatement("SELECT * from Artistes WHERE idArtiste=?");
            stm.setString(1,""+idArtiste);
            ResultSet res = stm.executeQuery();
            if (!res.next()){
                throw new IllegalArgumentException("Unknown artist");
            }
		} catch (SQLException e){
			System.err.println("failed");
			e.printStackTrace(System.err);
        }
    }
    
}