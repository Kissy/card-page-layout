package fr.kissy.card_page_layout.engine.use_case;

import fr.kissy.card_page_layout.config.CardSize;
import fr.kissy.card_page_layout.config.DocumentProperties;
import fr.kissy.card_page_layout.config.GridSize;
import fr.kissy.card_page_layout.config.Margin;
import fr.kissy.card_page_layout.config.Offset;

import java.awt.image.BufferedImage;
import java.util.function.BiConsumer;

public class ForGrid {
    private DocumentProperties documentProperties;
    private BufferedImage bufferedImage;

    public ForGrid(DocumentProperties documentProperties, BufferedImage bufferedImage) {
        this.documentProperties = documentProperties;
        this.bufferedImage = bufferedImage;
    }

    public void execute(BiConsumer<Integer, Offset> callback) {
        GridSize gridSize = documentProperties.getGrid();
        CardSize cardSize = documentProperties.getCard();
        Offset offset = documentProperties.getOffset();
        Margin margin = documentProperties.getMargin();

        int colsDirection = documentProperties.isBack() ? 1 : -1;
        int colsOrder = documentProperties.isBack() ? gridSize.getCols() - 1 : 0;

        int cardIndex = 0;
        int startingY = (bufferedImage.getHeight() - (cardSize.getHeight() * gridSize.getRows())) / 2 + offset.getHeight();
        for (int rows = 0; rows < gridSize.getRows(); rows++) {
            int startingX = (bufferedImage.getWidth() - (cardSize.getWidth() * gridSize.getCols())) / 2 + offset.getWidth();
            int rowY = startingY + rows * (cardSize.getHeight() + margin.getHeight());
            for (int cols = 0; cols < gridSize.getCols(); cols++) {
                int rowX = startingX + (colsOrder - cols * colsDirection) * (cardSize.getWidth() + margin.getWidth());
                callback.accept(cardIndex, new Offset(rowX, rowY));
                cardIndex++;
            }
        }
    }
}
