package gui;

import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JButton;

import java.awt.Graphics;

import java.util.Hashtable;

public class InsertPanel extends JPanel {
        private String[] tables;
        private Hashtable entries;

        private JComboBox comboTables;
        private JLabel titre;
        private JButton execButton;

        public InsertPanel(int width, int height) {
                super();
                this.setSize(width, height);
                this.init();
                this.paintPanel();
        }

        private void init() {
                this.titre = new JLabel("INSERT TRANSACTIONS");

                this.tables = new String[]{"Table 1", "Table 2", "Table 3"};
                this.comboTables = new JComboBox(tables);

                this.entries = new Hashtable();
                for (String t : tables)
                        entries.put(t, new String[]{"Entry 1", "Entry 2"});

                execButton = new JButton("Exec");
        }

        private void paintPanel() {
                this.removeAll();
                this.add(this.titre);
                this.add(this.comboTables);

                this.add(this.execButton);
        }


        public void paintComponent(Graphics g) {
                super.paintComponent(g);
                paintPanel();
                this.revalidate();
        }
}
