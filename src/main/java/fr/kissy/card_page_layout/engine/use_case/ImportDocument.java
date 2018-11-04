package fr.kissy.card_page_layout.engine.use_case;

import com.google.common.base.Strings;
import fr.kissy.card_page_layout.config.DocumentProperties;
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

public class ImportDocument {
    private static final Logger LOGGER = LoggerFactory.getLogger(ImportDocument.class);
    private final Path workDirectory;

    public ImportDocument(Path workDirectory) {
        this.workDirectory = workDirectory;
    }

    public WorkingDocument execute(DocumentProperties documentProperties) {
        try {
            File frontFile = new File(documentProperties.getPath());
            PDDocument frontDocument = PDDocument.load(frontFile);
            List<WorkingImage> frontImages = getImagesFrontDocument(documentProperties, frontFile, frontDocument);
            frontDocument.close();

            List<WorkingImage> backImages = new ArrayList<>();
            DocumentProperties documentPropertiesBack = documentProperties.getBack();
            if (documentPropertiesBack != null) {
                File backFile = new File(documentPropertiesBack.getPath());
                if (backFile.exists()) {
                    PDDocument backDocument = PDDocument.load(backFile);
                    backImages = getImagesFrontDocument(documentPropertiesBack, backFile, backDocument);
                    backDocument.close();
                }
            }

            WorkingDocument workingDocument = new WorkingDocument(documentProperties, frontImages, backImages);
            LOGGER.info("Imported {} front images from {} pages", frontImages.size(), frontDocument.getNumberOfPages());
            return workingDocument;
        } catch (IOException e) {
            throw new RuntimeException("Exception while trying to import document", e);
        }
    }

    private List<WorkingImage> getImagesFrontDocument(DocumentProperties documentProperties, File inputFile, PDDocument document) throws IOException {
        LOGGER.info("Importing pdf file {} ({})", inputFile, documentProperties.getPages());

        PDFRenderer pdfRenderer = new PDFRenderer(document);
        String documentName = com.google.common.io.Files.getNameWithoutExtension(inputFile.getName());
        Path documentWorkDirectory = workDirectory.resolve(documentName);
        if (!Files.exists(documentWorkDirectory)) {
            Files.createDirectories(documentWorkDirectory);
        }

        List<WorkingImage> images = new ArrayList<>();
        documentProperties.getPagesRange(document.getNumberOfPages()).forEach(pageIndex -> {
            Path workingImagePath = documentWorkDirectory.resolve(pageIndex + ".png");
            BufferedImage bufferedImage = null;
            if (!Files.exists(workingImagePath)) {
                try {
                    bufferedImage = pdfRenderer.renderImageWithDPI(pageIndex - 1, 300, ImageType.RGB);
                    ImageIO.write(bufferedImage, "png", workingImagePath.toFile());
                } catch (Exception e) {
                    throw new RuntimeException("Error while converting image from pdf", e);
                }
            }
            images.add(new WorkingImage(workingImagePath, bufferedImage));
        });

        return images;
    }
}
