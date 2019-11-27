import java.sql.Connection;

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
        numero = new Numero(con,this);
        spectacle = new Spectacle(con,this);
        planning_artiste = new Planning_Artiste(con,this);
        planning_numero = new Planning_Numero(con,this);
        specialite_artiste = new Specialite_Artiste(con,this);
        pseudo_artiste = new Pseudo_Artiste(con,this);
        expert = new Expert(con,this);
        evaluation = new Evaluation(con,this);
    }

}