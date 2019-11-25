import javax.swing.JPanel;
import javax.swing.JComboBox;

public class SelectPanel extends JPanel {
        private JComboBox selectOptions;

        public SelectPanel(int width, int height) {
                super();
                this.setSize(width, height);
                this.init();
        }

        private void init() {
                Object[] options = new Object[]{"Select option 1", "Select option 2"};
                selectOptions = new JComboBox(options);
                this.add(selectOptions);
        }

        public String getOption() {
                return selectOptions.getSelectedItem().toString();
        }
}
