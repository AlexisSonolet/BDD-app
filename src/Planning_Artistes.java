import java.sql.*;

public class Planning_Artistes extends Table {
	
	public Planning_Artistes(Connection con, Database db) {
		super(con, db);
	}
	
	/**
	 * Relie les idArtistes avec les num�ros auxquels ils participent

	 * # Param�tres
	 * @param idArtiste : id d'un artiste
	 * @param idNumero : id d'un num�ro
	 */
	public void insert(Integer idArtiste, Integer idNumero) {
		// Etape 1
		// 		1. v�rifier que idArtiste est bien l'id d'un artiste
		// 		2. v�rifier que idNumero est bien l'id d'un num�ro
		
		
		// Ins�rer les donn�es dans la table
		try {
			// Prepare the request
			String sql = "INSERT INTO planning_artiste (idArtiste, idNumero) VALUES (?, ?)";
			PreparedStatement prstmt = connection.prepareStatement(sql);
			// Fill the variables
			prstmt.setInt(1, idArtiste);
			prstmt.setInt(2, idNumero);
			
			ResultSet res = prstmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}
