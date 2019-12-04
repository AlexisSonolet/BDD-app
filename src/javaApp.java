import java.sql.*;
import java.util.Scanner;

public class javaApp {
	public static void main(String[] args) {
		try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			String url = "jdbc:oracle:thin:@Oracle1.ensimag.fr:" + "1521:oracle1";
			String user = "pereirap";
			String passwd = "pereirap";
			Connection connection = DriverManager.getConnection(url, user, passwd);

			Database db = new Database(connection);
			//db.prepareArtist();
			//db.preparePseudo_Artiste();
            PreparedStatement c1stm = connection.prepareStatement("SELECT * from pseudo_artiste");
            ResultSet res1 = c1stm.executeQuery();
            db.printTable(res1);
            res1.close();
            c1stm.close();
		} catch (SQLException e) {
			e. printStackTrace ();
		}
	}

    private static void menu(Database db, Connection conn) {
        Scanner sc = new Scanner(System.in);
        String menu_str = "*************** MENU PRINCIPAL ***************\n";
        menu_str += "1. Afficher une table\n";
        menu_str += "2. Ajouter entrée\n";
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

                    break;
                case 3: // Supprimer entree

                    break;
                case 4: // Quitter
                    break;
                default:
                    System.out.println("Mauvaise entrée ...\n\n");
            }
        }
    }

    private static void afficher_table(Database db, Connection conn, Scanner sc) {
        String menu_str = "******* Afficher le contenu d'une table *******\n";
        menu_str += "1. Artiste\n";
        menu_str += "2. Numero\n";
        menu_str += "3. Spectacle\n";
        menu_str += "4. Retour arrière\n";

        int choix = 0;
        PreparedStatement stmt = null;
        try {
            while (choix != 4) {
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
                        stmt = conn.prepareStatement("SELECT * FROM spectacle ORDER BY (dateSpectacle, heureSpectacle)");
                        break;
                    case 4: // Retour
                        break;
                    default:
                        System.out.println("Mauvaise entrée ...\n\n");
                }

                if (choix >= 1 && choix <= 3) { // On a une query à executer et à afficher
                    ResultSet res = stmt.executeQuery();
                    db.printTable(res);
                    res.close();
                    stmt.close();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
    }
}
