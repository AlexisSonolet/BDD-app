import java.sql.*;

/* TODO
 * vérifier que les numeros/spectacles n'existent pas deja
 * verifier que les presentateurs ne jouent pas : FAIT
 */

public class Spectacle extends Table{

	public Spectacle(Connection con, Database db){
		super(con, db);
	}
    
    /**
     * Verifie que le numero a un theme et un idnumero valide
     * @param num : id du numero
     * @param theme : theme du spectacle dans lequel on veut inserer le numero
     * @throws IllegalArgumentException : si le numero a un theme invalide, si idNumero n'existe pas ou si le
     * presentateur du spectacle joue dans l'un des numeros
     * @throws SQLException : si une erreur SQL se produit
     */
    private void checkOneNumero(int num, String theme, int presentateur) throws IllegalArgumentException, SQLException {
        //On verifie que l'id du numero est valide
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM numero WHERE idNumero = ? ");
        stmt.setInt(1, num);
        ResultSet res = stmt.executeQuery();
        if (!res.isBeforeFirst()) {
            throw new IllegalArgumentException("au moins un idNumero invalide : " + num);
        }
        stmt.close();
        res.close();

        //On verifie que le theme du numero est valide
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

        //On verifie que le presentateur du spectacle ne joue pas dans le numero
        stmt = connection.prepareStatement("SELECT idArtiste FROM planning_artiste WHERE idNumero = ?");
        stmt.setInt(1, num);
        res = stmt.executeQuery();
        while(res.next()){
            int artiste = res.getInt("idArtiste");
            if (artiste == presentateur) {
                throw new IllegalArgumentException("Le presentateur joue dans le numero " + num);
            }
        }
    }


    /**
     * Verifie que les numeros ont un theme et un idnumero valide, et que le presentateur ne joue pas dans les numeros
     * @param listeNumeros : liste des ids des numeros
     * @param theme : theme du spectacle dans lequel on veut inserer le numero
     * @throws IllegalArgumentException : si un numero a un theme invalide, si idNumero n'existe pas, ou si le
     * presentateur joue dans l'un des numeros
     * @throws SQLException : si une erreur SQL se produit
     */
    private void checkMultipleNumeros(int[] listeNumeros, String theme, int presentateur) throws IllegalArgumentException, SQLException {
		for (int num : listeNumeros) {
		    checkOneNumero(num, theme, presentateur);
        }   
    }

    
    /**
     * Verifie que le prix est valide
     * @param prix : prix du spectacle
     * @throws IllegalArgumentException : si le prix n'est pas valide
     */
    private void checkPrix(int prix) throws IllegalArgumentException{
		if (prix <= 0) {
			throw new IllegalArgumentException("Le prix d'un spectacle "
					+ "doit être strictement positif. Prix donné : " + prix);
		}
    }

    /**
     * Verifie que l'heure est valide
     * @param heure : heure du spectacle
     * @throws IllegalArgumentException : si l'heure n'est pas 9 ou 14
     */
    private void checkHeure(int heure) throws IllegalArgumentException {
		if (heure != 9 && heure != 14) {
			throw new IllegalArgumentException("L'heure d'un spectacle doit etre 9 ou 14, et non : " + heure);
		}
    }

    /**
     * Verifie si le spectacle existe. Si oui, renvoie son theme.
     * @param date : date du spectacle
     * @param heure : heure du spectacle
     * @throws IllegalArgumentException : si le spectacle n'existe pas
     * @throws SQLException : si une erreur SQL se produit
     */
    private String checkIfSpectacleExists(String date, int heure) throws SQLException, IllegalArgumentException{
        PreparedStatement stmt;
        ResultSet res;
        
        //On verifie que le spectacle existe
        stmt = connection.prepareStatement("SELECT themeSpectacle FROM spectacle WHERE dateSpectacle = "
                + "TO_DATE(?, 'YYYY-MM-DD') AND heureSpectacle = ?");
        stmt.setString(1, date);
        stmt.setInt(2, heure);

        res = stmt.executeQuery();
        if (!res.isBeforeFirst()) {
            throw new IllegalArgumentException("Spectacle invalide. Heure donnee : " + heure + "date "
                    + "donnee : " + date);
        }


        //On recupere le theme du spectacle
        res.next();
        String theme = res.getString("themeSpectacle");
        res.close();
        stmt.close();
        return theme;
    }


    /**
     * Verifie que l'id du presentateur existe.
     * @param presentateur : l'id du presentateur
     * @throws IllegalArgumentException : si l'id du presentateur est invalide
     * @throws SQLException : si une erreur SQL se produit
     */
    private void checkPresentateur(int presentateur) throws SQLException, IllegalArgumentException {
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM artiste WHERE idArtiste = ? ");
        stmt.setInt(1, presentateur);
        ResultSet res = stmt.executeQuery();

        if (!res.isBeforeFirst()) {
            throw new IllegalArgumentException("idArtiste du presentateur invalide : " + presentateur);
        }
        res.close();
        stmt.close();
    }
    
    private int getPresentateur(String date, int heure) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("SELECT presentateurSpectacle FROM spectacle "
                + "WHERE dateSpectacle = TO_DATE(?, YYYY-MM-DD) AND heureSpectacle = ?");
        stmt.setString(1, date);
        stmt.setInt(2, heure);
        ResultSet res = stmt.executeQuery();

        res.next();
        int presentateur = res.getInt("presentateurSpectacle");

        return presentateur;
    }

    /**
     * Insert un nouveau numero unique dans le planning
     * @param date : String au format AAAA-MM-JJ
     * @param heure : Heure du spectacle (9 ou 14)
     * @param num : id du numero a inserer
     * @throws SQLException : si une erreur SQL se produit
     */
    private void insertOneNumeroIntoPlanning(String date, int heure, int num) throws SQLException{
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO planning_numero "
                + "VALUES (TO_DATE(?, 'YYYY-MM-DD'), ?, ?)");
        stmt.setString(1, date);
        stmt.setInt(2, heure);
        stmt.setInt(3, num);

        stmt.executeUpdate();
        stmt.close();
    }

    /**
     * Insert de nouveaux numeros dans le planning
     * @param date : String au format AAAA-MM-JJ
     * @param heure : Heure du spectacle (9 ou 14)
     * @param listeNumeros : liste des ids des numeros a inserer
     */
    private void insertMultipleNumerosIntoPlanning(String date, int heure, int[] listeNumeros) throws SQLException{
        for (int num : listeNumeros) {
            insertOneNumeroIntoPlanning(date, heure, num);
        }
    }
    
    /**
     * Insert un nouveau Spectacle (sans verifications)
     * @param date : date du spectacle (format AAAA-MM-JJ)
     * @param heure : heure du spectacle (9 ou 14)
     * @param theme : theme du spectacle
     * @param presentateur : id du presentateur
     * @param prix : prix du spectacle
     * @throws SQLException : si une erreur SQL se produit
     */
    private void insertNewSpectacle(String date, int heure, String theme, int presentateur, int prix) throws SQLException{
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO spectacle VALUES"
                + " (TO_DATE(?, 'YYYY-MM-DD'), ?, ?, ?, ?)");
        stmt.setString(1, date);
        stmt.setInt(2, heure);
        stmt.setString(3, theme);
        stmt.setInt(4, presentateur);
        stmt.setInt(5, prix);

        stmt.executeUpdate();
        stmt.close();
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
	 * d'au moins 1 des spectacles est invalide ; le presentateur joue dans l'un des
     * numeros
	 */
    public void insert(String date, int heure, String theme, int presentateur,
			int prix, int[] listeNumeros) throws IllegalArgumentException {
		//Verification des contraintes
        checkPrix(prix);
		checkHeure(heure);
        
        if (listeNumeros.length == 0) {
			throw new IllegalArgumentException("la liste des numeros d'un spectacle ne peut pas etre vide");
		}

		try {
            checkPresentateur(presentateur);
            checkMultipleNumeros(listeNumeros, theme, presentateur);
            
            //Les contraintes sont toutes verifiees
            // Creation de la requete
            insertNewSpectacle(date, heure, theme, presentateur, prix);
            insertMultipleNumerosIntoPlanning(date, heure, listeNumeros);

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
     * numeros est vide, le theme d'un des numeros n'est pas celui du spectacle, le numero n'existe pas, le 
     * presentateur joue dans au moins un des numeros
     */ 
    public void insertNewNumerosToSpectacle(String dateSpectacle, int heureSpectacle, 
            int[] listeNumeros) throws IllegalArgumentException {
        
		if (listeNumeros.length == 0) {
			throw new IllegalArgumentException("la liste des numeros d'un spectacle ne peut pas etre vide");
        }
        checkHeure(heureSpectacle);

        try {
            //On verifie que le spectacle existe, et si oui on recupere son theme
            String theme = checkIfSpectacleExists(dateSpectacle, heureSpectacle);

            //On verifie que les numeros qu'on ajoute ont le bon theme et qu'ils sont valides
            //Pour cela on recupere le presentateur du spectacle
            int presentateur = getPresentateur(dateSpectacle, heureSpectacle);
            checkMultipleNumeros(listeNumeros, theme, presentateur);

            //Les contraintes sont verifiees, on cree la requete d'insertion
            insertMultipleNumerosIntoPlanning(dateSpectacle, heureSpectacle, listeNumeros);
        
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
