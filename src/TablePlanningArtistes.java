import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TablePlanningArtistes extends Table {
	
	public TablePlanningArtistes(Connection con, Database db) {
		super(con, db);
	}
	
	/**
	 * Relie les idArtistes avec les numéros auxquels ils participent

	 * # Paramètres
	 * @param idArtiste : id d'un artiste
	 * @param idNumero : id d'un numéro
	 */
	public void insert(Integer idArtiste, Integer idNumero) {
		// Etape 1
		// 		1. vérifier que idArtiste est bien l'id d'un artiste
		// 		2. vérifier que idNumero est bien l'id d'un numéro
		
		
		// Insérer les données dans la table
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
