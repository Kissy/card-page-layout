package fr.kissy.card_page_layout.engine.model;

import fr.kissy.card_page_layout.config.CardSize;
import fr.kissy.card_page_layout.config.DocumentProperties;
import fr.kissy.card_page_layout.config.GlobalConfig;
import fr.kissy.card_page_layout.config.GridSize;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class WorkingDocument {
    private final DocumentProperties documentProperties;
    private final List<WorkingImage> images;
    public WorkingDocument(DocumentProperties documentProperties, List<WorkingImage> images) {
        this.documentProperties = documentProperties;
        this.images = images;
    }

    public DocumentProperties getDocumentProperties() {
        return documentProperties;
    }

    public List<WorkingImage> getImages() {
        return images;
    }

    public List<BufferedImage> getCards() {
        List<BufferedImage> cards = new ArrayList<>();

        GridSize gridSize = documentProperties.getGrid();
        CardSize cardSize = documentProperties.getCard();

        for (WorkingImage image : images) {
            int startingY = (image.getBufferedImage().getHeight() - (cardSize.getHeight() * gridSize.getRows())) / 2;
            for (int rows = 0; rows < gridSize.getRows(); rows++) {
                int startingX = (image.getBufferedImage().getWidth() - (cardSize.getWidth() * gridSize.getCols())) / 2;
                for (int cols = 0; cols < gridSize.getCols(); cols++) {
                    BufferedImage cropped = image.getBufferedImage().getSubimage(startingX, startingY, cardSize.getWidth(), cardSize.getHeight());
                    cards.add(cropped);
                    startingX += cardSize.getWidth();
                }
                startingY += cardSize.getHeight();
            }
        }

        return cards;
    }
}
