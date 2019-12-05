import java.sql.*;

public class Spectacle extends Table{

	public Spectacle(Connection con, Database db){
		super(con, db);
	}

	/**
	 * Insert un nouveau spectacle.
	 * La liste des artistes doit être non-vide.
	 * Les numeros doivent être rentrés au préalable.
	 * @param date : date du spectacle, au format AAAA-MM-JJ
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
			PreparedStatement stmt = connection.prepareStatement("SELECT * FROM artiste WHERE idArtiste = ? ");
            stmt.setInt(1, presentateur);
			ResultSet res = stmt.executeQuery();

			if (!res.isBeforeFirst()) {
				throw new IllegalArgumentException("idArtiste du presentateur invalide : " + presentateur);
			}
            res.close();
            stmt.close();

			for (int num : listeNumeros) {
				stmt = connection.prepareStatement("SELECT * FROM numero WHERE idNumero = ? ");
                stmt.setInt(1, num);
				res = stmt.executeQuery();
				if (!res.isBeforeFirst()) {
					throw new IllegalArgumentException("au moins un idNumero invalide : " + num);
				}
                stmt.close();
                res.close();

				stmt = connection.prepareStatement("SELECT themeNumero FROM numero WHERE idNumero = ? ");
                stmt.setInt(1, num);
				res = stmt.executeQuery();
				res.next();
				String themeNum = res.getString("themeNumero");
				if (!theme.equals(themeNum)) {
					throw new IllegalArgumentException("au moins un numero a un theme different de "
							+ "celui du spectacle. Theme du spectacle : " + theme + " ; theme du "
							+ "numero : " + themeNum);
				}
                stmt.close();
                res.close();
			}

            // Creation de la requete
            stmt = connection.prepareStatement("INSERT INTO spectacle VALUES (TO_DATE(?, 'YYYY-MM-DD'), ?, ?, ?, ?)");
            stmt.setString(1, date);
            stmt.setInt(2, heure);
            stmt.setString(3, theme);
            stmt.setInt(4, presentateur);
            stmt.setInt(5, prix);

            stmt.executeUpdate();
            stmt.close();
            

            for (int num : listeNumeros) {
                stmt = connection.prepareStatement("INSERT INTO planning_numero VALUES (TO_DATE(?, 'YYYY-MM-DD'), ?, ?)");
                stmt.setString(1, date);
                stmt.setInt(2, heure);
                stmt.setInt(3, num);

                stmt.executeUpdate();
                stmt.close();
            }

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
    

    /**
     * Insert de nouveaux numeros a un spectacle.
     * @param dateSpctacle : Date du spectacle, string sous format AAAA-MM-JJ
     * @param heureSpectacle : heure du spectacle (9 ou 14)
     * @param listeNumeros : liste des id des numeros a ajouter
     * @throws IllegalArgumentException : si le spectacle n'existe pas, l'heure n'est pas 9 ou 14, la liste des
     * numeros est vide, le theme d'un des numeros n'est pas celui du spectacle, le numero n'existe pas
     */ 
    public void insertNewNumerosToSpectacle(String dateSpectacle, int heureSpectacle, 
            int[] listeNumeros) throws IllegalArgumentException {
        
		if (listeNumeros.length == 0) {
			throw new IllegalArgumentException("la liste des numeros d'un spectacle ne peut pas etre vide");
        }
		if (heureSpectacle != 9 && heureSpectacle != 14) {
			throw new IllegalArgumentException("L'heure d'un spectacle doit etre 9 ou 14, et non : " + heureSpectacle);
		}

        try {
            PreparedStatement stmt;
            ResultSet res;
            
            //On verifie que le spectacle existe
            stmt = connection.prepareStatement("SELECT themeSpectacle FROM spectacle WHERE dateSpectacle = "
                    + "TO_DATE(?, 'YYYY-MM-DD') AND heureSpectacle = ?");
            stmt.setString(1, dateSpectacle);
            stmt.setInt(2, heureSpectacle);

            res = stmt.executeQuery();
            if (!res.isBeforeFirst()) {
                throw new IllegalArgumentException("Spectacle invalide. Heure donnee : " + heureSpectacle + "date "
                        + "donnee : " + dateSpectacle);
            }
    

            //On recupere le theme du spectacle
            res.next();
            String theme = res.getString("themeSpectacle");

            res.close();
            stmt.close();

            //On verifie que les numeros qu'on ajoute ont le bon theme et qu'ils sont valides
			for (int num : listeNumeros) {
				stmt = connection.prepareStatement("SELECT * FROM numero WHERE idNumero = ? ");
                stmt.setInt(1, num);
				res = stmt.executeQuery();
				if (!res.isBeforeFirst()) {
					throw new IllegalArgumentException("au moins un idNumero invalide : " + num);
				}
                stmt.close();
                res.close();

				stmt = connection.prepareStatement("SELECT themeNumero FROM numero WHERE idNumero = ? ");
                stmt.setInt(1, num);
				res = stmt.executeQuery();
				res.next();
				String themeNum = res.getString("themeNumero");
				if (!theme.equals(themeNum)) {
					throw new IllegalArgumentException("au moins un numero a un theme different de "
							+ "celui du spectacle. Theme du spectacle : " + theme + " ; theme du "
							+ "numero : " + themeNum);
				}
                stmt.close();
                res.close();
			}
            //Les contraintes sont verifiees, on cree la requete d'insertion
            for (int num : listeNumeros) {
                stmt = connection.prepareStatement("INSERT INTO planning_numero VALUES (TO_DATE(?, 'YYYY-MM-DD'), ?, ?)");
                stmt.setString(1, dateSpectacle);
                stmt.setInt(2, heureSpectacle);
                stmt.setInt(3, num);

                stmt.executeUpdate();
                stmt.close();
            }

        
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
