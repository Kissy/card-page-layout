package fr.kissy.card_page_layout.gui;

import com.google.common.eventbus.EventBus;
import fr.kissy.card_page_layout.engine.event.ImportInputConfig;

import javax.swing.AbstractAction;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.awt.event.ActionEvent;
import java.nio.file.Paths;

public class MainMenuBar extends JMenuBar {
    public MainMenuBar(final EventBus eventBus) {
        super();
        JMenu fileMenu = new JMenu("File");
        final JMenuItem menuItem = new JMenuItem("Open...");
        menuItem.setActionCommand("file-open");
        menuItem.setAction(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                //eventBus.post(new ImportInputConfig(Paths.get("input.pdf")));
            }
        });
        fileMenu.add(menuItem);
        add(fileMenu);
    }
}
