package fr.kissy.card_page_layout.gui.preview;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import fr.kissy.card_page_layout.engine.event.WorkingDocumentImported;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PreviewPanel extends JPanel implements ActionListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(PreviewPanel.class);
    private final ImagePreviewPanel imagePreviewPanel;
    private final JLabel infoLabel;
    private int currentIndex = 0;
    private int imageSize = 0;

    public PreviewPanel(EventBus eventBus) {
        super(new BorderLayout());
        eventBus.register(this);

        JPanel actionsPanel = new JPanel();
        infoLabel = new JLabel("0/0");
        actionsPanel.add(infoLabel);

        createButton(actionsPanel, "<");
        createButton(actionsPanel, ">");
        createButton(actionsPanel, "Zoom in");
        createButton(actionsPanel, "Zoom out");
        add(actionsPanel, BorderLayout.NORTH);

        imagePreviewPanel = new ImagePreviewPanel(eventBus);
        add(imagePreviewPanel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        switch (event.getActionCommand()) {
            case "Zoom in":
                imagePreviewPanel.zoomIn();
                break;
            case "Zoom out":
                imagePreviewPanel.zoomOut();
                break;
            default:
                LOGGER.warn("Action command {} is not handled", event.getActionCommand());
        }
    }

    @Subscribe
    public void on(WorkingDocumentImported event) {
        //imageSize = event.getWorkingDocument().size();
        updateInfoLabelText();
    }

    private void updateInfoLabelText() {
        infoLabel.setText((currentIndex + 1) + "/" + imageSize);
    }

    private void createButton(JPanel actionsPanel, String s) {
        JButton button = new JButton(s);
        button.addActionListener(this);
        actionsPanel.add(button);
    }
}