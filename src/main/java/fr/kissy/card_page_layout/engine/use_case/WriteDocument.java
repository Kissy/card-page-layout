package fr.kissy.card_page_layout.engine.use_case;

import fr.kissy.card_page_layout.config.DocumentProperties;
import fr.kissy.card_page_layout.config.PageSize;
import fr.kissy.card_page_layout.engine.model.Page;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.JPEGFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.io.IOException;
import java.util.List;

public class WriteDocument {
    private static final int RESOLUTION = 300;
    private static final float POINTS_PER_PIXEL = 72f / RESOLUTION;
    private DocumentProperties documentProperties;

    public WriteDocument(DocumentProperties documentProperties) {
        this.documentProperties = documentProperties;
    }

    public void execute(List<Page> pages) throws IOException {
        try (PDDocument document = new PDDocument()) {

            for (Page page : pages) {
                PageSize pageSize = documentProperties.getPage();
                PDPage pdPage = new PDPage(new PDRectangle(pageSize.getWidth() * POINTS_PER_PIXEL, pageSize.getHeight() * POINTS_PER_PIXEL));
                document.addPage(pdPage);

                PDImageXObject pdImage = JPEGFactory.createFromImage(document, page.getImage(), 1, RESOLUTION);
                try (PDPageContentStream contentStream = new PDPageContentStream(document, pdPage)) {
                    contentStream.drawImage(pdImage, 0, 0, pdImage.getWidth() * POINTS_PER_PIXEL, pdImage.getHeight() * POINTS_PER_PIXEL);
                }
            }

            document.save(documentProperties.getPath());
        }
    }
}
