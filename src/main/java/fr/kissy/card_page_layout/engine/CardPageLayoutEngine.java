package fr.kissy.card_page_layout.engine;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import fr.kissy.card_page_layout.engine.event.ImportInputConfig;
import fr.kissy.card_page_layout.engine.event.WorkingDocumentImported;
import fr.kissy.card_page_layout.engine.model.WorkingDocument;
import fr.kissy.card_page_layout.engine.model.WorkingImage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CardPageLayoutEngine {
    private static final Logger LOGGER = LoggerFactory.getLogger(CardPageLayoutEngine.class);
    private final Path workDirectory;
    private final EventBus eventBus;

    public CardPageLayoutEngine(Path workDirectory, EventBus eventBus) {
        this.eventBus = eventBus;
        this.workDirectory = workDirectory;
        eventBus.register(this);
    }

    @Subscribe
    public void on(ImportInputConfig event) {
        try {
            LOGGER.info("Loading pdf file {}", event.getInputConfig());
            File inputFile = new File(event.getInputConfig().getPath());
            PDDocument document = PDDocument.load(inputFile);
            PDFRenderer pdfRenderer = new PDFRenderer(document);
            String documentName = com.google.common.io.Files.getNameWithoutExtension(inputFile.getName());
            Path documentWorkDirectory = workDirectory.resolve(documentName);
            if (!Files.exists(documentWorkDirectory)) {
                Files.createDirectories(documentWorkDirectory);
            }

            List<WorkingImage> images = new ArrayList<>();
            for (int pageIndex = 0; pageIndex < document.getNumberOfPages(); pageIndex++) {
                Path workingImagePath = documentWorkDirectory.resolve(pageIndex + ".png");
                BufferedImage bufferedImage = null;
                if (!Files.exists(workingImagePath)) {
                    bufferedImage = pdfRenderer.renderImageWithDPI(pageIndex, 300, ImageType.RGB);
                    ImageIO.write(bufferedImage, "png", workingImagePath.toFile());
                }
                images.add(new WorkingImage(workingImagePath, bufferedImage));
            }
            document.close();

            WorkingDocument workingDocument = new WorkingDocument(event.getInputConfig(), images);
            LOGGER.info("Found {} images from file {}", images.size(), event.getInputConfig());
            eventBus.post(new WorkingDocumentImported(workingDocument));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
