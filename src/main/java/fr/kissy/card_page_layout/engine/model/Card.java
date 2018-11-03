package fr.kissy.card_page_layout.engine.model;

import fr.kissy.card_page_layout.config.CardSize;

import java.awt.image.BufferedImage;

public class Card {
    private final WorkingImage workingImage;
    private final CardSize cardSize;
    private final int x;
    private final int y;

    public Card(WorkingImage workingImage, CardSize cardSize, int x, int y) {
        this.workingImage = workingImage;
        this.cardSize = cardSize;
        this.x = x;
        this.y = y;
    }

    public BufferedImage getImage() {
        return workingImage.getBufferedImage().getSubimage(x, y, cardSize.getWidth(), cardSize.getHeight());
    }
}
