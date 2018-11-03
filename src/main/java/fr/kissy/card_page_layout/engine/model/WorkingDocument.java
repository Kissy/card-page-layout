package fr.kissy.card_page_layout.engine.model;

import fr.kissy.card_page_layout.config.CardSize;
import fr.kissy.card_page_layout.config.DocumentProperties;
import fr.kissy.card_page_layout.config.GlobalConfig;
import fr.kissy.card_page_layout.config.GridSize;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class WorkingDocument {
    private final DocumentProperties documentProperties;
    private final List<WorkingImage> images;

    public WorkingDocument(DocumentProperties documentProperties, List<WorkingImage> images) {
        this.documentProperties = documentProperties;
        this.images = images;
    }

    public Stream<Card> getCards() {
        GridSize gridSize = documentProperties.getGrid();
        CardSize cardSize = documentProperties.getCard();

        Stream.Builder<Card> cards = Stream.builder();

        for (WorkingImage image : images) {
            int y = (image.getBufferedImage().getHeight() - (cardSize.getHeight() * gridSize.getRows())) / 2;
            for (int rows = 0; rows < gridSize.getRows(); rows++) {
                int x = (image.getBufferedImage().getWidth() - (cardSize.getWidth() * gridSize.getCols())) / 2;
                for (int cols = 0; cols < gridSize.getCols(); cols++) {
                    cards.add(new Card(image, cardSize, x, y));
                    x += cardSize.getWidth();
                }
                y += cardSize.getHeight();
            }
        }

        return cards.build();
    }
}
