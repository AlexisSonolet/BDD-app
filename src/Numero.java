import java.sql.*;

public class Numero extends Table {
	
	public Numero(Connection con, Database db) {
		super(con, db);
	}
	
	/**
	 * Ins�rer un nouveau num�ro
	 * 
	 * # Contraintes
	 * Un num�ro dure entre 10 et 30 minutes
	 * Il y a au moins un artiste par num�ro
	 * 
	 * # Param�tres
	 * @param Theme : th�me du num�ro
	 * @param Nom : nom du num�ro
	 * @param Resume : r�sum� du spectacle
	 * @param Duree : dur�e du num�ro
	 * @param NbArtistes : nombre d'artistes
	 * @param idArtistePrincipal : idArtiste de l'artiste principal
	 */
	public void insert(String Theme, String Nom, String Resume, int Duree, 
			int NbArtistes, int idArtistePrincipal) {
		// Contraintes
		if (Duree > 30 || Duree < 10) {
			throw new IllegalArgumentException("Mauvaise dur�e du num�ro !");
		}
		if (NbArtistes < 1) {
			throw new IllegalArgumentException("Il faut au moins un artiste !");
		}
		// V�rifier que idArtistePrincipal est dans idArtistes
		// Ou alors changer le mode de rentr�e de l'artiste principal
		
		
		// Requ�te
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
			prstmt.close();
			ResultSet res = prstmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
