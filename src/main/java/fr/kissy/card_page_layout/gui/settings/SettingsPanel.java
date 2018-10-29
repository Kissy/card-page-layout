package fr.kissy.card_page_layout.gui.settings;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import java.awt.Color;
import java.awt.GridLayout;

public class SettingsPanel extends JPanel {

    public SettingsPanel() {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JPanel cardsSection = new JPanel();
        cardsSection.setLayout(new BoxLayout(cardsSection, BoxLayout.Y_AXIS));
        cardsSection.setBorder(BorderFactory.createTitledBorder("Cards"));
        JPanel jPanel = new JPanel();
        jPanel.setBackground(Color.YELLOW);
        jPanel.add(new JLabel("Width"));
        jPanel.add(new JSpinner(new SpinnerNumberModel(750, 0, Integer.MAX_VALUE, 1)));
        cardsSection.add(jPanel);
        JPanel jPanel2 = new JPanel();
        jPanel2.add(new JLabel("Height"));
        jPanel2.add(new JSpinner(new SpinnerNumberModel(1050, 0, Integer.MAX_VALUE, 1)));
        cardsSection.add(jPanel2);
        add(cardsSection);

        JPanel gridSection = new JPanel(new GridLayout(2, 2, 0, 5));
        gridSection.setBorder(BorderFactory.createTitledBorder("GridSize"));
        gridSection.add(new JLabel("Rows"));
        gridSection.add(new JTextField(10));
        gridSection.add(new JLabel("Columns"));
        gridSection.add(new JTextField(10));
        add(gridSection);

        JPanel offsetSection = new JPanel(new GridLayout(2, 2, 0, 5));
        offsetSection.setBorder(BorderFactory.createTitledBorder("Offset"));
        offsetSection.add(new JLabel("Horizontal"));
        offsetSection.add(new JTextField(10));
        offsetSection.add(new JLabel("Vertical"));
        offsetSection.add(new JTextField(10));
        add(offsetSection);

        JPanel innerMarginSection = new JPanel(new GridLayout(2, 2, 0, 5));
        innerMarginSection.setBorder(BorderFactory.createTitledBorder("Inner margin"));
        innerMarginSection.add(new JLabel("Width"));
        innerMarginSection.add(new JTextField(10));
        innerMarginSection.add(new JLabel("Height"));
        innerMarginSection.add(new JTextField(10));
        add(innerMarginSection);

        /*JPanel yamlPanel = new JPanel();
        yamlPanel.setBorder(BorderFactory.createTitledBorder("Settings"));
        yamlSettings = new JTextArea(50, 50);
        yamlSettings.setFont(yamlSettings.getFont().deriveFont(11f));
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/default-settings.yaml")))) {
            yamlSettings.setText(reader.lines().collect(Collectors.joining(System.lineSeparator())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        yamlSettings.getDocument().addDocumentListener(this);
        yamlPanel.add(yamlSettings);
        add(yamlPanel);*/
    }
}