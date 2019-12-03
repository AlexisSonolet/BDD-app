package gui;

import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JLabel;


public class SelectPanel extends JPanel {
        private JComboBox selectOptions;

        public SelectPanel(int width, int height) {
                super();
                this.setSize(width, height);
                this.init();
        }

        private void init() {
                this.add(new JLabel("SELECT TRANSACTIONS"));

                String[] options = new String[]{"Select option 1", "Select option 2"};
                selectOptions = new JComboBox(options);
                this.add(selectOptions);

                JButton execButton = new JButton("Exec");
                this.add(execButton);
        }
}
