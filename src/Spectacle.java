import java.sql.*;

public class Spectacle extends Table{

	public Spectacle(Connection con, Database db){
		super(con, db);
	}

	/**
	 * Insert un nouveau spectacle.
	 * La liste des artistes doit être non-vide.
	 * Les numeros doivent être rentrés au préalable.
	 * @param date : date du spectacle
	 * @param heure : heure du spectacle
	 * @param theme : theme du spectacle
	 * @param presentateur : idArtiste du presentateur du spectacle
	 * @param prix : prix du spectacle
	 * @param listeNumeros : liste des idNumeros du spectacle
	 * @throws IllegalArgumentException : si : heure n'est pas 9 ou 14 ; le prix est nul
	 * ou négatif ; la liste des numeros du spectacle est vide ; au moins 1 numero a un
	 * theme different de celui du spectacle ; l'id du presentateur est invalide ; l'id
	 * d'au moins 1 des spectacles est invalide
	 */
	public void insert(String date, int heure, String theme, int presentateur,
			int prix, int[] listeNumeros) throws IllegalArgumentException {
		//Verification des contraintes
		if (heure != 9 && heure != 14) {
			throw new IllegalArgumentException("L'heure d'un spectacle doit etre 9 ou 14, et non : " + heure);
		}
		if (prix <= 0) {
			throw new IllegalArgumentException("Le prix d'un spectacle "
					+ "doit être strictement positif. Prix donné : " + prix);
		}
		if (listeNumeros.length == 0) {
			throw new IllegalArgumentException("la liste des numeros d'un spectacle ne peut pas etre vide");
		}

		try {
			PreparedStatement stmt = connection.prepareStatement("SELECT * FROM artiste WHERE idArtiste = ? ", presentateur);
			ResultSet res = stmt.executeQuery();
            stmt.close();
            res.close();
			if (!res.next()) {
				throw new IllegalArgumentException("idArtiste du presentateur invalide : " + presentateur);
			}
			for (int num : listeNumeros) {
				stmt = connection.prepareStatement("SELECT * FROM numero WHERE idNumero = ? ", num);
				res = stmt.executeQuery();
				if (!res.next()) {
					throw new IllegalArgumentException("au moins un idNumero invalide : " + num);
				}
                stmt.close();
                res.close();

				stmt = connection.prepareStatement("SELECT theme FROM numero WHERE idNumero = ? ", num);
				res = stmt.executeQuery();
				res.next();
				String themeNum = res.getString(1);
				if (theme.equals(themeNum)) {
					throw new IllegalArgumentException("au moins un numero a un theme different de "
							+ "celui du spectacle. Theme du spectacle : " + theme + " ; theme du "
							+ "numero : " + themeNum);
				}
                stmt.close();
                res.close();
			}

            // Creation de la requete
            for (int num : listeNumeros) {
                stmt = connection.prepareStatement("INSERT INTO planning_numero VALUES (TO_DATE(?, 'YYYY-MM-DD'), ?, ?)");
                stmt.setString(1, date);
                stmt.setInt(2, heure);
                stmt.setInt(3, num);
                res = stmt.executeQuery();
                res.close();
                stmt.close();
            }
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
