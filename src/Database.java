import java.sql.*;
import java.util.Scanner;

public class Database{
    Scanner sc;
    Table artiste;
    Table numero;
    Table spectacle;
    Table planning_artiste;
    Table planning_numero;
    Table specialite_artiste;
    Table pseudo_artiste;
    Table expert;
    Table evaluation;

    /**
     * Sets up a database via the given connection
     * @param con   The connection to the database
     */
    Database(Connection con){
        artiste = new Artiste(con,this);
        numero = new TableNumero(con,this);
        spectacle = new Spectacle(con,this);
        planning_artiste = new Planning_Artiste(con,this);
        planning_numero = new Planning_Numero(con,this);
        specialite_artiste = new Specialite_Artiste(con,this);
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

    public void prepareExpert() {
        String[] columns = new String[] {"idArtiste"};
        String[] values;

        System.out.println("***** Ajout d'une entrée dans la table expert *****");
        values = this.getValues(columns);

        this.expert.register(Integer.parseInt(values[0]));
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
