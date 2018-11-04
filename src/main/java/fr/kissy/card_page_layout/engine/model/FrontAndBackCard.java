package fr.kissy.card_page_layout.engine.model;

public class FrontAndBackCard {
    private final Card frontCard;
    private final Card backCard;

    public FrontAndBackCard(Card frontCard, Card backCard) {
        this.frontCard = frontCard;
        this.backCard = backCard;
    }

    public Card getFrontCard() {
        return frontCard;
    }

    public Card getBackCard() {
        return backCard;
    }
}
