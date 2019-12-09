import java.sql.*;

public class Planning_Artistes extends Table {

	public Planning_Artistes(Connection con, Database db) {
		super(con, db);
	}

	/**
	 * Relie les idArtistes avec les numéros auxquels ils participent.

	 * # Paramètres
	 * @param idArtiste : id d'un artiste
	 * @param idNumero : id d'un numéro
	 */
	public void insert(int idArtiste, int idNumero) {
		try {
            // Verify idArtiste
            PreparedStatement c1stm = connection.prepareStatement("SELECT * from Artiste WHERE idArtiste=?");
            c1stm.setString(1,""+idArtiste);
            ResultSet res1 = c1stm.executeQuery();
            if (!res1.next()) {
                throw new IllegalArgumentException("Unknown artiste");
            }
            res1.close();
            c1stm.close();

            // Verify idNumero
            c1stm = connection.prepareStatement("SELECT * from Numero WHERE idNumero=?");
            c1stm.setString(1,""+idNumero);
            res1 = c1stm.executeQuery();
            if (!res1.next()) {
                throw new IllegalArgumentException("Unknown numero");
            }
            res1.close();
            c1stm.close();
            
            //On verifie que l'artiste n'est pas du meme cirque que l'un des evaluateurs du numero 
            String sbig_stm1 = "SELECT cirqueArtiste FROM Artiste JOIN evaluation ON Artiste.idArtiste = evaluation.idExpert WHERE evaluation.idNumero = ?";
            PreparedStatement big_stm1 = connection.prepareStatement(sbig_stm1);
            big_stm1.setInt(1, idNumero);
            ResultSet bres1 = big_stm1.executeQuery();
            String sbig_stm2 = "SELECT cirqueArtiste FROM Artiste WHERE idArtiste = ?";
            PreparedStatement big_stm2 = connection.prepareStatement(sbig_stm2);
            big_stm2.setInt(1, idArtiste);
            ResultSet bres2 = big_stm2.executeQuery();

            while (bres2.next()){
                String cirExp = bres2.getString(1);
                while (bres1.next()){
                    String cirArt = bres1.getString(1);
                    if (cirArt.equals(cirExp)){
                        throw new IllegalArgumentException("Cet artiste est du meme cirque que l'un des evaluateurs de ce numero");
                    }
                }
            }
            bres1.close();
            bres2.close();
            big_stm1.close();
            big_stm2.close();

			// Prepare the request
			String sql = "INSERT INTO planning_artiste (idArtiste, idNumero) VALUES (?, ?)";
			PreparedStatement prstmt = connection.prepareStatement(sql);
			// Fill the variables
			prstmt.setInt(1, idArtiste);
			prstmt.setInt(2, idNumero);
		    prstmt.executeQuery();
            prstmt.close();
            connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e2) {
			    e2.printStackTrace();
                
            }
	    }
    }
}
