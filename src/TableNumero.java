import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TableNumero extends Table {
	
	public TableNumero(Connection con, Database db) {
		super(con, db);
	}
	
	/**
	 * Insérer un nouveau numéro
	 * 
	 * # Contraintes
	 * Un numéro dure entre 10 et 30 minutes
	 * Il y a au moins un artiste par numéro
	 * 
	 * # Paramètres
	 * @param Theme : thème du numéro
	 * @param Nom : nom du numéro
	 * @param Resume : résumé du spectacle
	 * @param Duree : durée du numéro
	 * @param NbArtistes : nombre d'artistes
	 * @param idArtistePrincipal : idArtiste de l'artiste principal
	 */
	public void insert(String Theme, String Nom, String Resume, int Duree, 
			int NbArtistes, int idArtistePrincipal) {
		// Contraintes
		if (Duree > 30 || Duree < 10) {
			throw new IllegalArgumentException("Mauvaise durée du numéro !");
		}
		if (NbArtistes < 1) {
			throw new IllegalArgumentException("Il faut au moins un artiste !");
		}
		// Vérifier que idArtistePrincipal est dans idArtistes
		// Ou alors changer le mode de rentrée de l'artiste principal
		
		
		// Requête
		try {
			// Prepare the request
			String sql = "INSERT INTO numero (themeNumero, nomNumero, resumeNumero, dureeNumero, nbartistes, artistePrincipalNumero) VALUES (?, ?, ?, ?, ?, ?)";
			PreparedStatement prstmt = connection.prepareStatement(sql);
			// Fill the variables
			prstmt.setString(1, Theme);
			prstmt.setString(2, Nom);
			prstmt.setString(3, Resume);
			prstmt.setInt(4, Duree);
			prstmt.setInt(5, NbArtistes);
			prstmt.setInt(6, idArtistePrincipal);
			
			ResultSet res = prstmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
