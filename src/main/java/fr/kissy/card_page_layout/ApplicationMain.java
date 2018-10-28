package fr.kissy.card_page_layout;

import com.beust.jcommander.Parameter;
import com.google.common.eventbus.EventBus;
import fr.kissy.card_page_layout.engine.event.PdfFileOpened;
import fr.kissy.card_page_layout.gui.settings.SettingsPanel;
import fr.kissy.card_page_layout.gui.MainMenuBar;
import fr.kissy.card_page_layout.gui.preview.PreviewPanel;

import javax.swing.JFrame;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.nio.file.Paths;

public class ApplicationMain extends JFrame {

    @Parameter(names = "--help", help = true)
    private boolean help;

    private static final int WIDTH = 1059;
    private static final int HEIGHT = 736;

    ApplicationMain(EventBus eventBus) {
        setTitle("Card page layout");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setSize(WIDTH, HEIGHT);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(screenSize.width / 2 - WIDTH / 2, screenSize.height / 2 - HEIGHT / 2);
        setVisible(true);

        setJMenuBar(new MainMenuBar(eventBus));
        getContentPane().add(new PreviewPanel(eventBus), BorderLayout.CENTER);
        getContentPane().add(new SettingsPanel(), BorderLayout.EAST);

        // TODO move that to on open file
        eventBus.post(new PdfFileOpened(Paths.get("input.pdf")));
    }

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        System.setProperty("sun.java2d.cmm", "sun.java2d.cmm.kcms.KcmsServiceProvider");
        /*UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        final EventBus eventBus = new AsyncEventBus(Executors.newFixedThreadPool(4));
        new CardPageLayoutEngine(eventBus);

        EventQueue.invokeLater(() -> new ApplicationMain(eventBus));*/
    }
}
