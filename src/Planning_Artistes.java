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
	    }
    }
}
