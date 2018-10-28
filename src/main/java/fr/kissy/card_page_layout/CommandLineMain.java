package fr.kissy.card_page_layout;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParametersDelegate;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import fr.kissy.card_page_layout.config.DimensionConverter;
import fr.kissy.card_page_layout.config.DimensionConverterFactory;
import fr.kissy.card_page_layout.config.GlobalConfig;
import fr.kissy.card_page_layout.engine.CardPageLayoutEngine;
import fr.kissy.card_page_layout.engine.event.PdfFileOpened;
import fr.kissy.card_page_layout.engine.event.WorkingDocumentImported;
import fr.kissy.card_page_layout.engine.model.WorkingImage;

import javax.imageio.ImageIO;
import javax.swing.UnsupportedLookAndFeelException;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class CommandLineMain {

    @Parameter(names = "--help", help = true)
    private boolean help;
    @ParametersDelegate
    private GlobalConfig globalConfig = new GlobalConfig();

    public static void main(String[] args) {
        System.setProperty("sun.java2d.cmm", "sun.java2d.cmm.kcms.KcmsServiceProvider");
        CommandLineMain commandLineMain = new CommandLineMain();
        JCommander.newBuilder()
                .addConverterFactory(new DimensionConverterFactory())
                .addObject(commandLineMain)
                .build()
                .parse(args);
        commandLineMain.run();
    }

    private void run() {
        EventBus eventBus = new EventBus();
        eventBus.register(this);

        new CardPageLayoutEngine(globalConfig, eventBus);
        eventBus.post(new PdfFileOpened(Paths.get("input.pdf")));
    }

    @Subscribe
    public void on(WorkingDocumentImported event) {
        WorkingImage workingImage = event.getWorkingDocument().getImages().get(0);
        List<BufferedImage> croppedCards = workingImage.getCroppedCards(globalConfig);

        int i = 0;
        for (BufferedImage croppedCard : croppedCards) {
            try {
                Path resolve = workingImage.getPath().getParent().resolve(workingImage.getPath().getFileName() + "." + i + ".png");
                ImageIO.write(croppedCard, "png", resolve.toFile());
            } catch (IOException e) {
                throw new RuntimeException("Impossible to write image", e);
            }
            i++;
        }


        /*BufferedImage bufferedImage = workingImage.getBufferedImage();
        BufferedImage rendered = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), bufferedImage.getType());
        Graphics2D graphics = rendered.createGraphics();
        graphics.drawImage(bufferedImage, 0, 0, null);
        graphics.setColor(Color.RED);
        graphics.setStroke(new BasicStroke(4));

        Dimension gridSize = globalConfig.inputConfig.gridSize;
        Dimension cardSize = globalConfig.inputConfig.cardSize;

        int startingY = (bufferedImage.getHeight() - (cardSize.height * gridSize.height)) / 2;
        for (int rows = 0; rows < gridSize.height; rows++) {
            int startingX = (bufferedImage.getWidth() - (cardSize.width * gridSize.width)) / 2;
            for (int cols = 0; cols < gridSize.width; cols++) {
                graphics.drawRect(startingX, startingY, cardSize.width, cardSize.height);
                startingX += cardSize.width;
            }
            startingY += cardSize.height;
        }

        graphics.dispose();*/
    }
}
