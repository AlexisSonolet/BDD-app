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
            PreparedStatement c1stm = connection.prepareStatement("SELECT * from Artiste WHERE idArtiste=?");
            c1stm.setInt(1, idArtistePrincipal);
            ResultSet res1 = c1stm.executeQuery();
            if (!res1.next()) {
                throw new IllegalArgumentException("Unknown artiste");
            }

            c1stm.close();
            res1.close();

		    // Requête
			// Prepare the request
			String sql = "INSERT INTO numero (idNumero, themeNumero, nomNumero, resumeNumero, dureeNumero, nbartistesnumero, artistePrincipalNumero) VALUES (?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement prstmt = connection.prepareStatement(sql);
			// Fill the variables
			prstmt.setInt(1, idNumero);
			prstmt.setString(2, Theme);
			prstmt.setString(3, Nom);
			prstmt.setString(4, Resume);
			prstmt.setInt(5, Duree);
			prstmt.setInt(6, NbArtistes);
			prstmt.setInt(7, idArtistePrincipal);
            prstmt.executeQuery();
			prstmt.close();
		} catch (SQLException e) {
			System.err.println("failed");
	        e.printStackTrace(System.err);
		}
	}
	
	public void suppressionNumero(int idNumero) {
		try {
            // Vérifie que idNumero est dans numero
            PreparedStatement c1stm = connection.prepareStatement("SELECT * from Numero WHERE idNumero=?");
            c1stm.setInt(1, idNumero);
            ResultSet res1 = c1stm.executeQuery();
            if (!res1.next()) {
                throw new IllegalArgumentException("Ce numéro n'existe pas");
            }

            c1stm.close();
            res1.close();

		    // Requête
			// Prepare the request
			String sql = "DELETE FROM numero WHERE idNumero = ?";
			PreparedStatement prstmt = connection.prepareStatement(sql);
			// Fill the variables
			prstmt.setInt(1, idNumero);
            prstmt.executeQuery();
			prstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Rentre l'évaluation et la note globale du numéro. Permet aussi de la modifier.
	 * @param idNumero	L'identifiant du numéro à évaluer
	 * @param evaluation	Son évaluation
	 * @param note	Sa note
	 */

	public void evaluationNumero(int idNumero, String evaluation, float note){
		try {
            // Vérifie que idNumero est dans numero
            PreparedStatement c1stm = connection.prepareStatement("SELECT * from Numero WHERE idNumero=?");
            c1stm.setInt(1, idNumero);
            ResultSet res1 = c1stm.executeQuery();
            if (!res1.next()) {
                throw new IllegalArgumentException("Ce numéro n'existe pas");
            }

            c1stm.close();
			res1.close();
			
			// Vérifie que la note est entre 0 et 10
			if (note<0 || note>10){
				throw new IllegalArgumentException("La note doit être entre 0 et 10");
			}

			// Prepare the request
			String sql = "UPDATE Numero SET (noteNumero = ?, evaluationNumero = ?) WHERE idNumero = ?)";
			PreparedStatement prstmt = connection.prepareStatement(sql);

			prstmt.setFloat(1, note);
			prstmt.setString(2, evaluation);
			prstmt.setInt(3, idNumero);
            prstmt.executeQuery();
			prstmt.close();
		} catch (SQLException e) {
			System.err.println("failed");
			e.printStackTrace();
		}
	}
}
