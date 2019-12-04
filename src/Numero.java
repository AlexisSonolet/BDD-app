import java.sql.*;

public class Numero extends Table {

	public Numero(Connection con, Database db) {
		super(con, db);
	}

	/**
	 * Insère un nouveau numéro.
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
			int NbArtistes, int idArtistePrincipal, int idNumero) {
		// Contraintes
		if (Duree > 30 || Duree < 10) {
			throw new IllegalArgumentException("Mauvaise durée du numéro !");
		}
		if (NbArtistes < 1) {
			throw new IllegalArgumentException("Il faut au moins un artiste !");
		}

		try {
            // Vérifie que idArtistePrincipal est dans idArtistes
            PreparedStatement c1stm = connection.prepareStatement("SELECT * from Artiste WHERE idArtiste=?;");
            c1stm.setString(1, ""+idArtistePrincipal);
            ResultSet res1 = c1stm.executeQuery();
            if (!res1.next()) {
                throw new IllegalArgumentException("Unknown artiste");
            }

            c1stm.close();
            res1.close();

		    // Requête
			// Prepare the request
			String sql = "INSERT INTO numero (themeNumero, nomNumero, resumeNumero, dureeNumero, nbartistes, artistePrincipalNumero, idNumero) VALUES (?, ?, ?, ?, ?, ?, ?);";
			PreparedStatement prstmt = connection.prepareStatement(sql);
			// Fill the variables
			prstmt.setString(1, Theme);
			prstmt.setString(2, Nom);
			prstmt.setString(3, Resume);
			prstmt.setInt(4, Duree);
			prstmt.setInt(5, NbArtistes);
			prstmt.setInt(6, idArtistePrincipal);
            prstmt.setInt(7, idNumero);
            prstmt.executeQuery();
			prstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
