package fr.kissy.card_page_layout.engine.use_case;

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
            File inputFile = new File(documentProperties.getPath());
            LOGGER.info("Importing pdf file {} ({})", inputFile, documentProperties.getPages());
            PDDocument document = PDDocument.load(inputFile);
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
            document.close();

            WorkingDocument workingDocument = new WorkingDocument(documentProperties, images);
            LOGGER.info("Imported {} images from {} pages as {}", images.size(), document.getNumberOfPages(), documentProperties.isBack() ? "back" : "front");
            return workingDocument;
        } catch (IOException e) {
            throw new RuntimeException("Exception while trying to import document", e);
        }
    }
}
