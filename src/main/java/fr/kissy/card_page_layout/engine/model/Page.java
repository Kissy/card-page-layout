package fr.kissy.card_page_layout.engine.model;

import java.util.List;

public class Page {
    private final List<Card> cards;

    public Page(List<Card> cards) {
        this.cards = cards;
    }

    public List<Card> getCards() {
        return cards;
    }
}
