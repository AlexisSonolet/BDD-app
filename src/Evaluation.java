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

    /**
     * Cancels the current evaluation
     */

    public void cancel(){
        registered = -1;
        specialistes = 0;
    }

    /**
     * Begins the evaluation of a show.
     * @param idNum The id of the show to evaluate
     */

    public void begin(int idNum){
        try {
            // Check that we are not already evaluating a show.
            if (registered!=-1){
                throw new IllegalArgumentException("Evaluation en cours");
            }
            // Check that this show exists.
            PreparedStatement c1stm = connection.prepareStatement("SELECT * from Numero WHERE idNumero=?");
            c1stm.setString(1,""+idNum);
            ResultSet res1 = c1stm.executeQuery();
            if (!res1.next()){
                throw new IllegalArgumentException("Numéro inconnu");
            }
            c1stm.close();
            res1.close();
            // Check that it has not already been evaluated.
            PreparedStatement c2stm = connection.prepareStatement("SELECT * from Evaluation WHERE idNumero=?");
            c2stm.setInt(1, idNum);
            ResultSet res2 = c2stm.executeQuery();
            if (res2.next()){
                throw new IllegalArgumentException("Numéro déjà évalué");
            }
            c2stm.close();
            res2.close();
            // We can evaluate this show.
            idNumero = idNum;
            registered = 0;
            // Getting the topic of the show.
            PreparedStatement c3stm = connection.prepareStatement("SELECT ThemeNumero from Numero WHERE idNumero=?");
            c3stm.setString(1,""+idNum);
            ResultSet res3 = c3stm.executeQuery();
            res3.next();
            theme = res3.getString(1);
            c3stm.close();
            res3.close();
            connection.commit();
        } catch (SQLException e) {
            System.err.println("failed");
			e.printStackTrace(System.err);
            try {
                connection.rollback();
            } catch (SQLException e2) {
			    e2.printStackTrace();
                
            }
        }
    }

    /**
     * Saves an evaluation for the current show. Checks that it satisfies all the constrains.
     * @param idExpert  The id of the expert evaluating
     * @param note  The note he/she has given to this show
     * @param eval  The evaluation he/she has given to this show
     */

    public void evaluate(int idExpert, float note, String eval){
        try {
            // Check that we're evaluating a show.
            if (registered<0){
                throw new IllegalArgumentException("Aucune évaluation en cours");
            }
            // Check that we don't already have five experts.
            if (registered>=5){
                throw new IllegalArgumentException("Evaluation complète");
            }
            // Check that the note has an acceptable value.
            if (note<0 || note>10){
                throw new IllegalArgumentException("La note doit être entre 0 et 10");
            }
            // Check that this is indeed an expert.
            PreparedStatement c1stm = connection.prepareStatement("SELECT * from Expert WHERE idExpert=?");
            c1stm.setInt(1, idExpert);
            ResultSet res1 = c1stm.executeQuery();
            if (!res1.next()){
                throw new IllegalArgumentException("Expert inconnu");
            }
            c1stm.close();
            res1.close();
            // Check that this expert has not already evaluated this show.
            for (int i = 0;i<registered;i++){
                if (experts[i]==idExpert){
                    throw new IllegalArgumentException("Cet expert a déjà évalué ce numéro");
                }
            }
            // Check that the expert has not evaluated too many shows.
            PreparedStatement c2stm = connection.prepareStatement("SELECT Count(*) from Evaluation WHERE idExpert=?");
            c2stm.setInt(1, idExpert);
            ResultSet res2 = c2stm.executeQuery();
            res2.next();
            if (res2.getInt(1)>=15){
                throw new IllegalArgumentException("Cet expert a évalué trop de numéros");
            }
            c2stm.close();
            res2.close();
            // Check that the expert's circus is not one of the show's artists
            String sbig_stm1 = "SELECT cirqueArtiste FROM Artiste JOIN Planning_Artiste ON Artiste.idArtiste = Planning_Artiste.idArtiste WHERE Planning_Artiste.idNumero = ?";
            PreparedStatement big_stm1 = connection.prepareStatement(sbig_stm1);
            big_stm1.setInt(1, idNumero);
            ResultSet bres1 = big_stm1.executeQuery();
            String sbig_stm2 = "SELECT cirqueArtiste FROM Artiste WHERE idArtiste = ?";
            PreparedStatement big_stm2 = connection.prepareStatement(sbig_stm2);
            big_stm2.setInt(1, idExpert);
            ResultSet bres2 = big_stm2.executeQuery();

            while (bres2.next()){
                String cirExp = bres2.getString(1);
                while (bres1.next()){
                    String cirArt = bres1.getString(1);
                    if (cirArt.equals(cirExp)){
                        throw new IllegalArgumentException("Cet expert est du même cirque qu'un artiste du numéro");
                    }
                }
            }
            bres1.close();
            bres2.close();
            big_stm1.close();
            big_stm2.close();
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
                    throw new IllegalArgumentException("Trop de spécialistes");
                }
                res++;
            } else {
                // It is not a specialist
                if (registered+1-specialistes > 2){
                    // Too many non-specialists.
                    throw new IllegalArgumentException("Trop de non spécialistes");
                }
            }
            c3stm.close();
            res3.close();
            // We can save the evaluation.
            specialistes+=res;
            notes[registered] = note;
            evaluations[registered] = eval;
            experts[registered] = idExpert;
            
            this.registered += 1;
        } catch (SQLException e) {
            System.err.println("failed");
			e.printStackTrace(System.err);
            try {
                connection.rollback();
            } catch (SQLException e2) {
			    e2.printStackTrace();
                
            }
        }
    }

    /**
     * Commits the evaluations given to the current show
     */

    public void commit(){
        if (registered==-1){
            throw new IllegalArgumentException("Numéro à évaluer non spécifié");
        }
        if (registered<5){
            throw new IllegalArgumentException("Il manque des évaluations pour le numéro");
        }
        try {
            for (int i=0;i<5;i++){
                PreparedStatement stm = connection.prepareStatement("INSERT INTO Evaluation VALUES (?,?,?,?)");
                stm.setString(1, ""+experts[i]);
                stm.setInt(2, idNumero);
                stm.setFloat(3, notes[i]);
                stm.setString(4, evaluations[i]);
                stm.executeQuery();
                stm.close();
            }
            float m = (notes[0]+notes[1]+notes[2]+notes[3]+notes[4])/5;
            database.numero.evaluationNumero(idNumero, "", m);
            cancel();
            connection.commit();
        } catch (SQLException e) {
            System.err.println("failed");
			e.printStackTrace(System.err);
            try {
                connection.rollback();
            } catch (SQLException e2) {
			    e2.printStackTrace();
                
            }
        }
    }

    public void removeEval(int idNum){
        try{
            // Check that we have evaluated this show
            PreparedStatement c1stm = connection.prepareStatement("SELECT * from Evaluation WHERE idNumero=?");
            c1stm.setString(1,""+idNum);
            ResultSet res1 = c1stm.executeQuery();
            if (!res1.next()){
                throw new IllegalArgumentException("Numéro inconnu");
            }
            c1stm.close();
            res1.close();

            // Delete the evaluation

            PreparedStatement c2stm = connection.prepareStatement("DELETE * from Evaluation WHERE idNumero=?");
            c2stm.setString(1,""+idNum);
            c2stm.executeQuery();
            c2stm.close();
            connection.commit();
        } catch (SQLException e) {
            System.err.println("failed");
			e.printStackTrace(System.err);
            try {
                connection.rollback();
            } catch (SQLException e2) {
			    e2.printStackTrace();
                
            }
        }
    }
}
