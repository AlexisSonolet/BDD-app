import java.util.ArrayList;
import java.rmi.activation.UnknownObjectException;
import java.sql.*;
import java.text.ParseException;
import java.util.Scanner;

public class Database{
    Scanner sc;
    Artiste artiste;
    Numero numero;
    Spectacle spectacle;
    Planning_Artistes planning_artiste;
    Table planning_numero;
    Specialite specialite_artiste;
    Pseudo_Artiste pseudo_artiste;
    Expert expert;
    Evaluation evaluation;

    /**
     * Sets up a database via the given connection.
     * @param con   The connection to the database
     */
    Database(Connection con){
        artiste = new Artiste(con,this);
        numero = new Numero(con,this);
        spectacle = new Spectacle(con,this);
        planning_artiste = new Planning_Artistes(con,this);
        specialite_artiste = new Specialite(con,this);
        pseudo_artiste = new Pseudo_Artiste(con,this);
        expert = new Expert(con,this);
        evaluation = new Evaluation(con,this);

        this.sc = new Scanner(System.in);
    }

    public void prepareArtist() {
        String[] columns = new String[] {"nomArtiste", "prenomArtiste", "dateNaissance (YYYY-MM-DD)", "cirqueArtiste", "telephoneArtiste"};
        String[] values;

        System.out.println("***** Ajout d'une entrée dans la table artiste *****");
        values = this.getValues(columns);

        this.artiste.ajoutArtiste(values[0], values[1], values[2], values[3], values[4]);
    }

    public void prepareSupprimeArtist() {
    	String[] columns = new String[] {"idArtiste"};
    	String[] values;
    	System.out.println("***** Suppression d'une entrée dans la table artiste *****");
    	values = this.getValues(columns);
        try {
    	    this.artiste.suppressionArtiste(Integer.parseInt(values[0]));
        } catch (IllegalArgumentException e) {
            System.out.println("Erreur, abandon de la transaction");
            System.out.println(e.getMessage());
        }
    }

// AJOUT, SUPPRESSION DANS LA TABLE PSEUDO_ARTISTE, toutes testées
    public void preparePseudo_Artiste() {
    	int idArtiste;
    	int nombre_pseudos;
    	System.out.println("Artiste pour lequel vous souhaitez ajouter le ou les pseudo(s): ");
        idArtiste = Integer.parseInt(this.sc.nextLine());
    	System.out.println("Nombre de pseudos que vous souhaitez entrer: ");
        nombre_pseudos = Integer.parseInt(this.sc.nextLine());
        
        for (int i = 0; i < nombre_pseudos; i ++) {
        	System.out.println("Pseudo n°" + (i+1) +" que vous souhaitez ajouter");
        	String pseudo = this.sc.nextLine();
        	try {
                this.pseudo_artiste.ajoutPseudoArtiste(idArtiste, pseudo);
            } catch (IllegalArgumentException e) {
                System.out.println("Erreur, abandon de la transaction");
                System.out.println(e.getMessage());
            }
        }
    }
    
    public void prepareSupprimePseudo() {
    	String[] columns = new String[] {"idArtiste", "Pseudo"};
    	String[] values;
    	System.out.println("***** Suppression d'une entrée dans la table pseudo_artiste *****");
    	values = this.getValues(columns);
    	try {
    	    this.pseudo_artiste.suppressionPseudo(Integer.parseInt(values[0]), values[1]);
        } catch (IllegalArgumentException e) {
            System.out.println("Erreur, abandon de la transaction");
            System.out.println(e.getMessage());
        }
    }
    
    public void prepareSuppAllPseudo() {
    	String[] columns = new String[] {"idArtiste"};
    	String[] values;
    	System.out.println("***** Suppression d'un artiste dans la table pseudo_artiste *****");
    	values = this.getValues(columns);
    	try {
    	    this.pseudo_artiste.suppAllPseudo(Integer.parseInt(values[0]));
        } catch (IllegalArgumentException e) {
            System.out.println("Erreur, abandon de la transaction");
            System.out.println(e.getMessage());
        }
    }
    
// AJOUT, SUPPRESSION DANS LA TABLE NUMERO, aucune testée
    public void prepareNumero() {
        String[] columns = new String[] {"Theme", "Nom", "Resume", "Duree", "NbArtistes", "idArtistePrincipal"};
        String[] values;

        System.out.println("***** Ajout d'une entrée dans la table numéro *****");
        values = this.getValues(columns);

        this.numero.insert(values[0], values[1], values[2], Integer.parseInt(values[3]), Integer.parseInt(values[4]),
        		Integer.parseInt(values[5]));
    }

    public void prepareSupprimeNumero() {
    	String[] columns = new String[] {"idNumero"};
    	String[] values;
    	System.out.println("***** Suppression d'une entrée dans la table numéro *****");
    	values = this.getValues(columns);
        try {
    	    this.numero.suppressionNumero(Integer.parseInt(values[0]));
        } catch (IllegalArgumentException e) {
            System.out.println("Erreur, abandon de la transaction");
            System.out.println(e.getMessage());
        }
    }
    
// AJOUT, SUPPRESSION DANS LA TABLE EXPERT testee
    
    public void prepareExpert() {
        String[] columns = new String[] {"idArtiste"};
        String[] values;

        System.out.println("***** Ajout d'une entrée dans la table expert *****");
        values = this.getValues(columns);
        try{
            this.expert.register(Integer.parseInt(values[0]));
        } catch (IllegalArgumentException e) {
            System.out.println("Erreur, abandon de la transaction");
            System.out.println(e.getMessage());
        }
    }

    public void prepareSupprimeExpert() {
        String[] columns = new String[] {"idExpert"};
        String[] values;

        System.out.println("***** Suppression d'une entrée dans la table expert *****");
        values = this.getValues(columns);
        try {
            this.expert.supprimerExpert(Integer.parseInt(values[0]));
        } catch (IllegalArgumentException e) {
            System.out.println("Erreur, abandon de la transaction");
            System.out.println(e.getMessage());
        }
    }

// AJOUT, SUPPRESSION DES 5 EVALUATIONS DANS LA TABLE EVALUATIONS

    public void prepareEvaluation() {

        this.evaluation.cancel();

        while (true){
            System.out.println("Numéro à évaluer :");

            int idNum = Integer.parseInt(this.sc.nextLine());

            try{
                this.evaluation.begin(idNum);
                break;
            } catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
            }
        }
        
        int n=0;
        String[] num = {"premier","deuxième","troisième","quatrième","cinquième"};
        String[] columns = new String[] {"idExpert","noteExpert","evaluationExpert"};
        String[] values;
        while (n<5){

            System.out.println("***** Entrée de l'évaluation du "+num[n]+" expert *****");
            values = this.getValues(columns);
            try {
                this.evaluation.evaluate(Integer.parseInt(values[0]), Float.parseFloat(values[1]), values[2]);
                n++;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        this.evaluation.commit();
    }

    public void prepareSupprimeEvaluation() {
        try {
            System.out.println("Entrez le numéro dont l'évaluation doit être effacée (pressez Entrée pour annuler) :");
            this.evaluation.removeEval(Integer.parseInt(this.sc.nextLine()));
        } catch (NumberFormatException e){
            return;
        } catch (IllegalArgumentException e) {
            System.out.println("Numéro non évalué");
        }
    }
    
// AJOUT, SUPPRESSION DANS LA TABLE SPECIALITE, toutes testees
    
    public void prepareSpecialite() {
    	int idArtiste;
    	int nombre_specialites;
    	System.out.println("Artiste pour lequel vous souhaitez ajouter la ou les spécialité(s): ");
        idArtiste = Integer.parseInt(this.sc.nextLine());
    	System.out.println("Nombre de spécialités que vous souhaitez entrer: ");
        nombre_specialites = Integer.parseInt(this.sc.nextLine());
        
        for (int i = 0; i < nombre_specialites; i ++) {
        	System.out.println("Spécialité n°" + (i+1) +" que vous souhaitez ajouter");
        	String specialite = this.sc.nextLine();
        	try {
                this.specialite_artiste.ajoutSpecialite(idArtiste, specialite);
            } catch (IllegalArgumentException e) {
                System.out.println("Erreur, abandon de la transaction");
                System.out.println(e.getMessage());
            }
        }
    }
    
    public void prepareSupprimeSpecialite() {
    	String[] columns = new String[] {"idArtiste", "Specialite"};
    	String[] values;
    	System.out.println("***** Suppression d'une entrée dans la table specialite_artiste *****");
    	values = this.getValues(columns);
    	try {
    	    this.specialite_artiste.suppressionSpecialite(Integer.parseInt(values[0]), values[1]);
        } catch (IllegalArgumentException e) {
            System.out.println("Erreur, abandon de la transaction");
            System.out.println(e.getMessage());
        }
    }
    
    public void prepareSuppAllSpecialite() {
    	String[] columns = new String[] {"idArtiste"};
    	String[] values;
    	System.out.println("***** Suppression d'un artiste dans la table specialite_artiste *****");
    	values = this.getValues(columns);
    	try {
    	    this.specialite_artiste.suppAllSpecialite(Integer.parseInt(values[0]));
        } catch (IllegalArgumentException e) {
            System.out.println("Erreur, abandon de la transaction");
            System.out.println(e.getMessage());
        }
    }
 
//SPECTACLES toutes VALIDEES
    public void prepareSpectacle() {
        String date;
        int heure;
        String theme;
        int presentateur;
        int prix;
        int[] listeNumeros;
        
    	
        System.out.println("***** Ajout d'un spectacle dans la table spectacle, "
                + "et de son planning dans la table planning_numero *****");
        
    	String[] columns = new String[] {"Date (AAAA-MM-JJ)", "Heure (9 ou 14)", "Theme", "Presentateur", "Prix"};
        String[] values = this.getValues(columns);
        
        date = values[0];
        heure = Integer.parseInt(values[1]);
        theme = values[2];
        presentateur = Integer.parseInt(values[3]);
        prix = Integer.parseInt(values[4]);

        System.out.println("Nombre de numeros a ajouter : ");
        int nb = Integer.parseInt(this.sc.nextLine());
        listeNumeros = new int[nb];

        for (int i = 0; i < nb ; i++) {
            System.out.println("Id d'un numero a ajouter : ");
            listeNumeros[i] = Integer.parseInt(this.sc.nextLine());
        }
        try {           
            spectacle.insert(date, heure, theme, presentateur, prix, listeNumeros);
        } catch (IllegalArgumentException e) {
            System.out.println("Erreur, abandon de la transaction");
            System.out.println(e.getMessage());
        }
        
    }
    
    public void prepareInsertNumerosDansSpectacle(){
        String date;
        int heure;
        int[] listeNumeros;
    	System.out.println("***** Ajout de numeros dans un spectacle *****");
        String[] columns = new String[] {"Date du spectacle", "Heure du spectacle"};
        String[] values = this.getValues(columns);

        date = values[0];
        heure = Integer.parseInt(values[1]);
        
        System.out.println("Nombre de numeros a ajouter : ");
        int nb = Integer.parseInt(this.sc.nextLine());
        listeNumeros = new int[nb];

        for (int i = 0; i < nb ; i++) {
            System.out.println("Id d'un numero a ajouter : ");
            listeNumeros[i] = Integer.parseInt(this.sc.nextLine());
        }
        try {
            this.spectacle.insertNewNumerosToSpectacle(date, heure, listeNumeros);
        } catch (IllegalArgumentException e) {
            System.out.println("Erreur, abandon de la transaction");
            System.out.println(e.getMessage());
        }
    }

    public void prepareSuppressionNumerosDansSpectacle() {
        int[] listeNumeros;

    	System.out.println("***** Suppression de numeros dans un spectacle *****");
        
        System.out.println("Nombre de numeros a supprimer : ");
        int nb = Integer.parseInt(this.sc.nextLine());
        listeNumeros = new int[nb];

        for (int i = 0; i < nb ; i++) {
            System.out.println("Id d'un numero a supprimer : ");
            listeNumeros[i] = Integer.parseInt(this.sc.nextLine());
        }
        try {
            this.spectacle.deleteNumerosFromPlanning(listeNumeros);
        } catch (IllegalArgumentException e) {
            System.out.println("Erreur, abandon de la transaction");
            System.out.println(e.getMessage());
        }
    }


// AFFICHAGE TABLES
    /**
     * Displays a table given by an sql ResultSet.
     * @param res   The result of the request.
     */
    public void printTable(ResultSet res){
        try {
            ResultSetMetaData structure = res.getMetaData();
            int m = structure.getColumnCount();
            int[] sizes = new int[m];
            for (int i=0;i<m;i++){
                sizes[i] = 0;
            }
            ArrayList<ArrayList<String>> lines = new ArrayList<ArrayList<String>>();
            ArrayList<String> names = new ArrayList<String>();
            for (int i=0;i<m;i++){
                names.add(structure.getColumnLabel(i+1));
                sizes[i]=structure.getColumnLabel(i+1).length();
            }
            lines.add(names);
            while (res.next()){
                ArrayList<String> line = new ArrayList<String>();
                for (int i=0;i<m;i++){
                    String col = res.getString(i+1);
                    if (col.length()>sizes[i]){
                        sizes[i]=col.length();
                    }
                    line.add(col);
                }
                lines.add(line);
            }
            for (ArrayList<String> line : lines) {
                String sline = " | ";
                for (int i=0;i<m;i++){
                    sline+=line.get(i);
                    for (int j=0;j<sizes[i]-line.get(i).length();j++){
                        sline+=" ";
                    }
                    sline+=" | ";
                }
                System.out.println(sline);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'affichage d'une table");
			e.printStackTrace(System.err);
        }
    }

    private String[] getValues(String[] columns) {
        String[] values = new String[columns.length];
        for (int i = 0; i < columns.length; i++) {
            System.out.println("Valeur de la colonne " + columns[i] + " : ");
            values[i] = this.sc.nextLine();
        }

        return values;
    }

    /**
     * Tries to find an artist by asking the user.
     * @return  The artist's ID
     * @throws UnknownObjectException   If no artist is found
     */

    public int getArtist() throws UnknownObjectException {
        System.out.println("Entrez le nom de l'artiste :");
        ArrayList<Integer> results1 = this.artiste.recherche_nom(this.sc.nextLine());
        if (results1.size()==0){
            System.out.println("Artiste inconnu...");
            throw new UnknownObjectException("Unknown artist");
        }
        if (results1.size()==1){
            return results1.get(0);
        }
        System.out.println("Entrez le prénom de l'artiste :");
        ArrayList<Integer> results2 = this.artiste.recherche_prenom(this.sc.nextLine());
        if (results2.size()==0){
            System.out.println("Artiste inconnu...");
            throw new UnknownObjectException("Unknown artist");
        }
        if (results2.size()==1 || results1.contains(results2.get(0))){
            return results1.get(0);
        }
        ArrayList<Integer> result = new ArrayList<Integer>();
        for (Integer artiste : results1){
            if (results2.contains(artiste)){
                result.add(artiste);
            }
        }
        if (result.size()==1){
            return result.get(0);
        }
        if (result.size()==0){
            System.out.println("Artiste inconnu...");
            throw new UnknownObjectException("Unknown artist");
        }
        try{
            System.out.println("Plusieurs artistes correspondent. Quel est son identifiant ?");
            String s = "";
            for (Integer artiste : result){
                s+="?, ";
            }
            PreparedStatement pstm = this.evaluation.connection.prepareStatement("SELECT * FROM Artiste WHERE idArtiste IN ("+s.substring(0,s.length()-2)+")");
            int i = 0;
            for (Integer artiste : result){
                pstm.setInt(i++,artiste);
            }
            printTable(pstm.executeQuery());
        } catch (SQLException e){
            System.err.println("Erreur lors de l'affichage des artistes");
            e.printStackTrace(System.err);
            throw new UnknownObjectException("Unknown artist");
        }
        try{
            int a = Integer.parseInt(this.sc.nextLine());
            if (!result.contains(a)){
                throw new UnknownObjectException("Unknown artist");
            }
            return a;
        } catch (NumberFormatException e){
            System.out.println("Not a number");
            throw new UnknownObjectException("Unknown artist");
        }
    }
}
