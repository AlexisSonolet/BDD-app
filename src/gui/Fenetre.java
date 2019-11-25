import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Fenetre extends JFrame {
        private SelectPanel selectPanel;
        private InsertPanel insertPanel;

        public Fenetre() {
                super();
                this.setTitle("BD Festival");
                this.setSize(600, 400);
                this.setLocationRelativeTo(null);
                this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                this.initFenetre();

                this.setVisible(true);
        }

        private void initFenetre() {
                JPanel container = new JPanel();
                container.setSize(this.getWidth(), this.getHeight());
                this.setContentPane(container);

                this.selectPanel = new SelectPanel(this.getWidth(), 100);
                container.add(selectPanel);

                this.insertPanel = new InsertPanel(this.getWidth(), 100);
                container.add(insertPanel);
        }

        public static void main(String[] args) {
                Fenetre f = new Fenetre();
        }
}
