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
    Table specialite_artiste;
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
        // planning_numero = new Planning_Numero(con,this);
        // specialite_artiste = new Specialite_Artiste(con,this);
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
