import java.sql.*;
import java.util.Scanner;

public class javaApp {
    private static String list_tables;
	public static void main(String[] args) {
        // Init list_tables
        list_tables = "1. Artiste\n";
        list_tables  += "2. Numero\n";
        list_tables += "3. Spectacle\n";
        list_tables += "4. Evaluation\n";
        list_tables += "5. Specialité\n";
        list_tables += "6. Pseudonyme\n";
        list_tables += "7. Expert\n";

		try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			String url = "jdbc:oracle:thin:@Oracle1.ensimag.fr:" + "1521:oracle1";
			String user = "pereirap";
			String passwd = "pereirap";
			Connection connection = DriverManager.getConnection(url, user, passwd);
            connection.setAutoCommit(false);

			Database db = new Database(connection);
            javaApp.menu(db, connection);
		} catch (SQLException e) {
			e. printStackTrace ();
		}
	}

    private static void menu(Database db, Connection conn) {
        Scanner sc = new Scanner(System.in);
        String menu_str = "*************** MENU PRINCIPAL ***************\n";
        menu_str += "1. Afficher une table\n";
        menu_str += "2. Ajouter une entrée\n";
        menu_str += "3. Supprimer une entrée\n";
        menu_str += "4. Quitter\n";

        int choix = 0;
        while (choix != 4) {
            System.out.println(menu_str);
            System.out.print("Votre choix : ");
            choix = Integer.parseInt(sc.nextLine());

            switch (choix) {
                case 1: // Afficher table
                    javaApp.afficher_table(db, conn, sc);
                    break;
                case 2: // Ajouter entree
                    javaApp.ajouter_table(db, sc);
                    break;
                case 3: // Supprimer entree
                    javaApp.supprimer_table(db, sc);
                    break;
                case 4: // Quitter
                    break;
                default:
                    System.out.println("Mauvaise entrée ...\n\n");
            }
        }
    }

    private static void supprimer_table(Database db, Scanner sc) {
        String menu_str = "******* Supprimer une transaction dans une table *******\n";
        menu_str += javaApp.list_tables;
        menu_str += "8. Supprimer tous les pseudos d'un artiste\n";
        menu_str += "9. Supprimer toutes les spécialités d'un artiste\n";
        menu_str += "10. Planning des spectacles\n";
        menu_str += "11. Retour arrière\n";

        int choix = 0;
        while (choix != 2) {
            System.out.println("1. Supprimer un numero du planning d'un spectacle\n2. Retour arrière");
            System.out.print("Votre choix : ");
            choix = Integer.parseInt(sc.nextLine());

            switch (choix) {
                case 1: // Artiste
                    db.prepareSuppressionNumerosDansSpectacle();
                    //db.prepareSupprimeArtist();
                    break;
                case 2:
                    break; // Numero
                /*
                	db.prepareSupprimeNumero();
                    break;
                case 3: // Spectacle
                    break;
                case 4: // Evaluation
                    db.prepareSupprimeEvaluation();
                    break;
                case 5: // Specialite
                    db.prepareSupprimeSpecialite();
                    break;
                case 6: // Pseudo
                    db.prepareSupprimePseudo();
                    break;
                case 7: // Expert
                    db.prepareSupprimeExpert();
                    break;
                case 8: // All pseudos
                    db.prepareSuppAllPseudo();
                    break;
                case 9:
                    db.prepareSuppAllSpecialite();
                    break;
                case 10: // Retour
                    db.prepareSuppressionNumerosDansSpectacle();
                    break;
                case 11: //Retour
                    break; */
                default:
                    System.out.println("Mauvaise entrée ...\n\n");
            }
        }
    }

    private static void ajouter_table(Database db, Scanner sc) {
        String menu_str = "******* Ajouter une transaction dans une table *******\n";
        menu_str += javaApp.list_tables;
        menu_str += "8. Ajouter un numero au planning d'un spectacle\n";
        menu_str += "9. Evaluation globale d'un numéro\n";
        menu_str += "10. Ajouter un artiste au planning d'un numero\n";
        menu_str += "11. Retour arrière\n";

        int choix = 0;
        while (choix != 11) {
            System.out.println(menu_str);
            System.out.print("Votre choix : ");
            choix = Integer.parseInt(sc.nextLine());

            switch (choix) {
                case 1: // Artiste
                    db.prepareArtist();
                    break;
                case 2: // Numero
                    db.prepareNumero();
                    break;
                case 3: // Spectacle
                    db.prepareSpectacle();
                    break;
                case 4: // Evaluation
                    db.prepareEvaluation();
                    break;
                case 5: // Specialite
                    db.prepareSpecialite();
                    break;
                case 6: // Pseudo
                    db.preparePseudo_Artiste();
                    break;
                case 7: // Expert
                    db.prepareExpert();
                    break;
                case 8: // Planning
                    db.prepareInsertNumerosDansSpectacle();
                    break;
                case 9: // Evaluation globale
                    db.prepareEvaluationGlobale();
                    break;
                case 10: // Evaluation globale
                    db.prepareInsertArtisteDansPlanning();
                    break;
                case 11: // Retour
                    break;
                default:
                    System.out.println("Mauvaise entrée ...\n\n");
            }
        }
    }

    private static void afficher_table(Database db, Connection conn, Scanner sc) {
        String menu_str = "******* Afficher le contenu d'une table *******\n";
        menu_str += javaApp.list_tables;
        menu_str += "8. Planning des spectacles\n";
        menu_str += "9. Planning des numeros\n";
        menu_str += "10. Numéros (groupés par thème et par notes)\n";
        menu_str += "11. Experts et spécialités\n";
        menu_str += "12. Retour arrière\n";

        int choix = 0;
        PreparedStatement stmt = null;
        try {
            while (choix != 12) {
                System.out.println(menu_str);
                System.out.print("Votre choix : ");
                choix = Integer.parseInt(sc.nextLine());

                switch (choix) {
                    case 1: // Artiste
                        stmt = conn.prepareStatement("SELECT * FROM artiste ORDER BY idArtiste");
                        break;
                    case 2: // Numero
                        stmt = conn.prepareStatement("SELECT * FROM numero ORDER BY idNumero");
                        break;
                    case 3: // Spectacle
                        stmt = conn.prepareStatement("SELECT * FROM spectacle ORDER BY dateSpectacle, heureSpectacle");
                        break;
                    case 4: // Evaluation
                        stmt = conn.prepareStatement("SELECT * FROM evaluation ORDER BY idNumero");
                        break;
                    case 5: // Specialite
                        stmt = conn.prepareStatement("SELECT * FROM specialite_artiste ORDER BY idArtiste");
                        break;
                    case 6: // Pseudo
                        stmt = conn.prepareStatement("SELECT * FROM pseudo_artiste ORDER BY idArtiste");
                        break;
                    case 7: // Expert
                        stmt = conn.prepareStatement("SELECT * FROM expert ORDER BY idExpert");
                        break;
                    case 8: // Spectacle
                        stmt = conn.prepareStatement("SELECT * FROM planning_numero ORDER BY dateSpectacle, heureSpectacle");
                        break;
                    case 9: // Planning numero
                        stmt = conn.prepareStatement("SELECT * FROM planning_artiste ORDER BY idNumero");
                        break;
                    case 10: // Numero GROUP BY themeNumero
                        stmt = conn.prepareStatement("SELECT * FROM numero ORDER BY themeNumero, noteNumero DESC");
                        break;
                    case 11: // Experts et spécialités
                        stmt = conn.prepareStatement("SELECT specialiteartiste, idExpert FROM specialite_artiste JOIN expert ON idArtiste=idExpert ORDER BY specialiteArtiste");
                        break;
                    case 12: // Retour
                        break;
                    default:
                        System.out.println("Mauvaise entrée ...\n\n");
                }

                if (choix >= 1 && choix <= 11) { // On a une query à executer et à afficher
                    ResultSet res = stmt.executeQuery();
                    db.printTable(res);
                    res.close();
                    stmt.close();
                    System.out.println("\n");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
    }
}
