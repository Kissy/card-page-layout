package fr.kissy.card_page_layout.engine.use_case;

import fr.kissy.card_page_layout.config.CardSize;
import fr.kissy.card_page_layout.config.DocumentProperties;
import fr.kissy.card_page_layout.config.GridSize;
import fr.kissy.card_page_layout.engine.model.Card;
import fr.kissy.card_page_layout.engine.model.Page;
import fr.kissy.card_page_layout.engine.model.WorkingDocument;

import javax.imageio.ImageIO;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

// Move to Page ?
public class CreateOutputImage {
    private DocumentProperties documentProperties;

    public CreateOutputImage(DocumentProperties documentProperties) {
        this.documentProperties = documentProperties;
    }

    public BufferedImage execute(Page page) {
        BufferedImage bufferedImage = new BufferedImage(documentProperties.getPage().getWidth(), documentProperties.getPage().getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        Graphics2D graphics = bufferedImage.createGraphics();
        drawBackground(bufferedImage, graphics);
        drawCards(page.getCards(), bufferedImage, graphics);
        drawCropMarks(bufferedImage, graphics);
        graphics.dispose();
        return bufferedImage;
    }

    private void drawBackground(BufferedImage bufferedImage, Graphics2D graphics) {
        graphics.setPaint(Color.WHITE);
        graphics.fillRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
    }

    private void drawCards(List<Card> cards, BufferedImage bufferedImage, Graphics2D graphics) {
        GridSize gridSize = documentProperties.getGrid();
        CardSize cardSize = documentProperties.getCard();

        int cardIndex = 0;
        int y = (bufferedImage.getHeight() - (cardSize.getHeight() * gridSize.getRows())) / 2;
        for (int rows = 0; rows < gridSize.getRows(); rows++) {
            int x = (bufferedImage.getWidth() - (cardSize.getWidth() * gridSize.getCols())) / 2;
            for (int cols = 0; cols < gridSize.getCols(); cols++) {
                if (cardIndex < cards.size()) {
                    Card card = cards.get(cardIndex);
                    graphics.drawImage(card.getImage(), x, y, cardSize.getWidth(), cardSize.getHeight(), null);
                    cardIndex++;
                }
                x += cardSize.getWidth();
            }
            y += cardSize.getHeight();
        }
    }

    private void drawCropMarks(BufferedImage bufferedImage, Graphics2D graphics) {
        GridSize gridSize = documentProperties.getGrid();
        CardSize cardSize = documentProperties.getCard();

        graphics.setPaint(Color.BLACK);
        graphics.setStroke(new BasicStroke(3));

        int startingY = (bufferedImage.getHeight() - (cardSize.getHeight() * gridSize.getRows())) / 2;
        for (int rows = 0; rows <= gridSize.getRows(); rows++) {
            int startingX = (bufferedImage.getWidth() - (cardSize.getWidth() * gridSize.getCols())) / 2;
            for (int cols = 0; cols <= gridSize.getCols(); cols++) {
                if (rows == 0) {
                    graphics.drawLine(startingX - 3 / 2, startingY - 20 - 80, startingX - 3 / 2, startingY - 20);
                }
                if (cols == 0) {
                    graphics.drawLine(startingX - 20 - 80, startingY - 3 / 2, startingX - 20, startingY - 3 / 2);
                }
                if (rows == gridSize.getRows()) {
                    graphics.drawLine(startingX + 3 / 2, startingY + 20, startingX + 3 / 2, startingY + 20 + 80);
                }
                if (cols == gridSize.getCols()) {
                    graphics.drawLine(startingX + 20, startingY + 3 / 2, startingX + 20 + 80, startingY + 3 / 2);
                }
                startingX += cardSize.getWidth();
            }
            startingY += cardSize.getHeight();
        }
    }
}
