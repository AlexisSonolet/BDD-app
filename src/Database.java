import java.util.ArrayList;
import java.sql.*;
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
        //planning_numero = new Planning_Numero(con,this);
        specialite_artiste = new Specialite(con,this);
        pseudo_artiste = new Pseudo_Artiste(con,this);
        expert = new Expert(con,this);
        evaluation = new Evaluation(con,this);

        this.sc = new Scanner(System.in);
    }

    public void prepareArtist() {
        String[] columns = new String[] {"idArtiste", "nomArtiste", "prenomArtiste", "dateNaissance", "cirqueArtiste", "telephoneArtiste"};
        String[] values;

        System.out.println("***** Ajout d'une entrée dans la table artiste *****");
        values = this.getValues(columns);

        this.artiste.ajoutArtiste(Integer.parseInt(values[0]), values[1], values[2], values[3], values[4], values[5]);
    }

    public void prepareSupprimeArtist() {
    	String[] columns = new String[] {"idArtiste"};
    	String[] values;
    	System.out.println("***** Suppression d'une entrée dans la table artiste *****");
    	values = this.getValues(columns);

    	this.artiste.suppressionArtiste(Integer.parseInt(values[0]));
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
        	this.pseudo_artiste.ajoutPseudoArtiste(idArtiste, pseudo);
        }
    }
    
    public void prepareSupprimePseudo() {
    	String[] columns = new String[] {"idArtiste", "Pseudo"};
    	String[] values;
    	System.out.println("***** Suppression d'une entrée dans la table pseudo_artiste *****");
    	values = this.getValues(columns);
    	
    	this.pseudo_artiste.suppressionPseudo(Integer.parseInt(values[0]), values[1]);
    }
    
    public void prepareSuppAllPseudo() {
    	String[] columns = new String[] {"idArtiste"};
    	String[] values;
    	System.out.println("***** Suppression d'un artiste dans la table pseudo_artiste *****");
    	values = this.getValues(columns);
    	
    	this.pseudo_artiste.suppAllPseudo(Integer.parseInt(values[0]));
    }
    
// AJOUT, SUPPRESSION DANS LA TABLE NUMERO, aucune testée
    public void prepareNumero() {
        String[] columns = new String[] {"idNumero", "Theme", "Nom", "Resume", "Duree", "NbArtistes", "idArtistePrincipal"};
        String[] values;     

        System.out.println("***** Ajout d'une entrée dans la table numéro *****");
        values = this.getValues(columns);

        this.numero.insert(values[1], values[2], values[3], Integer.parseInt(values[4]), Integer.parseInt(values[5]),
        		Integer.parseInt(values[6]), Integer.parseInt(values[0]));
    }

    public void prepareSupprimeNumero() {
    	String[] columns = new String[] {"idNumero"};
    	String[] values;
    	System.out.println("***** Suppression d'une entrée dans la table numéro *****");
    	values = this.getValues(columns);

    	this.numero.suppressionNumero(Integer.parseInt(values[0]));
    }
    
// AJOUT, SUPPRESSION DANS LA TABLE EXPERT testee
    
    public void prepareExpert() {
        String[] columns = new String[] {"idArtiste"};
        String[] values;

        System.out.println("***** Ajout d'une entrée dans la table expert *****");
        values = this.getValues(columns);

        this.expert.register(Integer.parseInt(values[0]));
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
        	this.specialite_artiste.ajoutSpecialite(idArtiste, specialite);
        }
    }
    
    public void prepareSupprimeSpecialite() {
    	String[] columns = new String[] {"idArtiste", "Specialite"};
    	String[] values;
    	System.out.println("***** Suppression d'une entrée dans la table specialite_artiste *****");
    	values = this.getValues(columns);
    	
    	this.specialite_artiste.suppressionSpecialite(Integer.parseInt(values[0]), values[1]);
    }
    
    public void prepareSuppAllSpecialite() {
    	String[] columns = new String[] {"idArtiste"};
    	String[] values;
    	System.out.println("***** Suppression d'un artiste dans la table specialite_artiste *****");
    	values = this.getValues(columns);
    	
    	this.specialite_artiste.suppAllSpecialite(Integer.parseInt(values[0]));
    }
 
//SPECTACLES

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
            
        spectacle.insert(date, heure, theme, presentateur, prix, listeNumeros);
        
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
                    for (int j=0;j<line.get(i).length()-sizes[i];i++){
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
}
