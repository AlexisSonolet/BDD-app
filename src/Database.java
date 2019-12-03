import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

public class Database{

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
                sizes[i] = structure.getColumnDisplaySize(i);
            }
            ArrayList<String> lines = new ArrayList<String>();
            String names = " | ";
            for (int i=0;i<m;i++){
                names += structure.getColumnLabel(i).substring(0, sizes[i]);
            }
            lines.add(names);
            lines.add(" ");
            while (res.next()){
                String line = " | ";
                for (int i=0;i<m;i++){
                    String col = res.getString(i);
                    for (int j=0;j<sizes[i]-col.length();j++){
                        col+=" ";
                    }
                    line+=col+" | ";
                }
                lines.add(line);
            }
            for (String line : lines){
                System.out.println(line);
            }
            res.close();
        } catch (SQLException e){
            System.err.println("failed");
			e.printStackTrace(System.err);
        }
    }

}