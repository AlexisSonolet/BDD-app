import java.sql.*;

public class Evaluation extends Table{

    private int idNumero;
    private int registered;
    private float[] notes;
    private String[] evaluations;
    private int[] experts;
    private int specialistes;
    private String theme;

    Evaluation(Connection con, Database db) {
        super(con, db);

        notes = new float[5];
        evaluations = new String[5];
        experts = new int[5];
        specialistes = 0;
        registered = -1;    // Indicates that we are not evaluating a show;
    }

    public void cancel(){
        registered = -1;
        specialistes = 0;
    }

    public void begin(int idNum){
        try {
            // Check that we are not already evaluating a show.
            if (registered!=-1){
                throw new IllegalArgumentException("Already avaluating a show");
            }
            // Check that this show exists.
            PreparedStatement c1stm = connection.prepareStatement("SELECT * from Numero WHERE idNumero=?");
            c1stm.setString(1,""+idNum);
            ResultSet res1 = c1stm.executeQuery();
            if (!res1.next()){
                throw new IllegalArgumentException("Unknown show");
            }
            // Check that it has not already been evaluated.
            PreparedStatement c2stm = connection.prepareStatement("SELECT * from Evaluation WHERE idNumero=?");
            c2stm.setString(1,""+idNum);
            ResultSet res2 = c1stm.executeQuery();
            if (res2.next()){
                throw new IllegalArgumentException("Show already evaluated");
            }
            // We can evaluate this show.
            idNumero = idNum;
            registered = 0;
            // Getting the topic of the show.
            PreparedStatement c3stm = connection.prepareStatement("SELECT ThemeNumero from Numero WHERE idNumero=?");
            c3stm.setString(1,""+idNum);
            ResultSet res3 = c3stm.executeQuery();
            theme = res3.getString(1);
        } catch (SQLException e) {
            System.err.println("failed");
			e.printStackTrace(System.err);
        }
    }

    public void evaluate(int idExpert, float note, String eval){
        try {
            // Check that we're evaluating a show.
            if (registered<0){
                throw new IllegalArgumentException("Not evaluating a show");
            }
            // Check that we don't already have five experts.
            if (registered>=5){
                throw new IllegalArgumentException("Evaluation complete");
            }
            // Check that the note has an acceptable value.
            if (note<0 || note>10){
                throw new IllegalArgumentException("Note must be between 0 and 10");
            }
            // Check that this is indeed an expert.
            PreparedStatement c1stm = connection.prepareStatement("SELECT * from Expert WHERE idExpert=?");
            c1stm.setString(1,""+idExpert);
            ResultSet res1 = c1stm.executeQuery();
            if (!res1.next()){
                throw new IllegalArgumentException("Unknown expert");
            }
            // Check that this expert has not already evaluated this show.
            for (int i = 0;i<registered;i++){
                if (experts[i]==idExpert){
                    throw new IllegalArgumentException("Already typed in the evaluation of that expert");
                }
            }
            // Check that the expert has not evaluated too many shows.
            PreparedStatement c2stm = connection.prepareStatement("SELECT Count(*) from Evaluation WHERE idExpert=?");
            c2stm.setString(1,""+idExpert);
            ResultSet res2 = c1stm.executeQuery();
            if (res2.getInt(1)>=15){
                throw new IllegalArgumentException("Expert has already evaluated too many shows");
            }
            // Check that we won't have too many specialists.
            PreparedStatement c3stm = connection.prepareStatement("SELECT * from Specialite_Artiste WHERE idArtiste=? AND SpecialiteArtiste=?");
            c3stm.setString(1,""+idExpert);
            c3stm.setString(2, theme);
            ResultSet res3 = c3stm.executeQuery();
            int res = 0;
            if (res3.next()){
                // It is a specialist.
                if (specialistes+1>3){
                    // Too many specialists.
                    throw new IllegalArgumentException("Too many specialists for the evaluation of this show");
                }
                res++;
            } else {
                // It is not a specialist
                if (registered+1-specialistes > 2){
                    // Too many non-specialists.
                    throw new IllegalArgumentException("Too many non-specialists for the evaluation of this show");
                }
            }
            // We can save the evaluation.
            specialistes+=res;
            notes[registered] = note;
            evaluations[registered] = eval;
            experts[registered] = idExpert;
        } catch (SQLException e) {
            System.err.println("failed");
			e.printStackTrace(System.err);
        }
    }

    public void commit(){
        if (registered==-1){
            throw new IllegalArgumentException("Not evenn evaluating");
        }
        if (registered<5){
            throw new IllegalArgumentException("Not all experts have send their evaluation");
        }
        try {
            for (int i=0;i<5;i++){
                PreparedStatement stm = connection.prepareStatement("INSERT INTO Evaluation VALUES (?,?,?,?)");
                stm.setString(1, ""+experts[i]);
                stm.setString(2, ""+idNumero);
                stm.setString(3, ""+notes[i]);
                stm.setString(4, evaluations[i]);
                stm.executeQuery();
            }
            cancel();
        } catch (SQLException e) {
            System.err.println("failed");
			e.printStackTrace(System.err);
        }
    }

}