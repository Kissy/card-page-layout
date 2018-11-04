package fr.kissy.card_page_layout.engine.model;

import fr.kissy.card_page_layout.config.CardSize;
import fr.kissy.card_page_layout.config.DocumentProperties;
import fr.kissy.card_page_layout.config.GridSize;
import fr.kissy.card_page_layout.engine.use_case.ForGrid;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;

public class Page {
    private DocumentProperties documentProperties;
    private final List<Card> cards;
    private boolean reversed;

    public Page(DocumentProperties documentProperties, List<Card> cards, boolean reversed) {
        this.documentProperties = documentProperties;
        this.cards = cards;
        this.reversed = reversed;
    }

    public BufferedImage getImage() {
        BufferedImage bufferedImage = new BufferedImage(documentProperties.getPage().getWidth(), documentProperties.getPage().getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        Graphics2D graphics = bufferedImage.createGraphics();
        drawBackground(bufferedImage, graphics);
        drawCards(bufferedImage, graphics);
        drawCropMarks(bufferedImage, graphics);
        graphics.dispose();
        return bufferedImage;
    }

    private void drawBackground(BufferedImage bufferedImage, Graphics2D graphics) {
        graphics.setPaint(Color.WHITE);
        graphics.fillRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
    }

    private void drawCards(BufferedImage bufferedImage, Graphics2D graphics) {
        CardSize cardSize = documentProperties.getCard();
        new ForGrid(documentProperties, bufferedImage, reversed).execute((cardIndex, offset) -> {
            if (cards.size() > cardIndex) {
                Card card = cards.get(cardIndex);
                graphics.drawImage(card.getImage(), offset.getWidth(), offset.getHeight(), cardSize.getWidth(), cardSize.getHeight(), null);
            }
        });
    }

    private void drawCropMarks(BufferedImage bufferedImage, Graphics2D graphics) {
        GridSize gridSize = documentProperties.getGrid();
        CardSize cardSize = documentProperties.getCard();

        graphics.setPaint(Color.BLACK);
        graphics.setStroke(new BasicStroke(3));

        int y = (bufferedImage.getHeight() - (cardSize.getHeight() * gridSize.getRows())) / 2;
        for (int rows = 0; rows <= gridSize.getRows(); rows++) {
            int x = (bufferedImage.getWidth() - (cardSize.getWidth() * gridSize.getCols())) / 2;
            for (int cols = 0; cols <= gridSize.getCols(); cols++) {
                if (rows == 0) {
                    graphics.drawLine(x - 3 / 2, y - 20 - 80, x - 3 / 2, y - 20);
                }
                if (cols == 0) {
                    graphics.drawLine(x - 20 - 80, y - 3 / 2, x - 20, y - 3 / 2);
                }
                if (rows == gridSize.getRows()) {
                    graphics.drawLine(x + 3 / 2, y + 20, x + 3 / 2, y + 20 + 80);
                }
                if (cols == gridSize.getCols()) {
                    graphics.drawLine(x + 20, y + 3 / 2, x + 20 + 80, y + 3 / 2);
                }
                x += cardSize.getWidth();
            }
            y += cardSize.getHeight();
        }
    }
}
