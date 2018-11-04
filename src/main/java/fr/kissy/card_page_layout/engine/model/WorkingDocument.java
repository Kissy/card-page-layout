package fr.kissy.card_page_layout.engine.model;

import fr.kissy.card_page_layout.config.DocumentProperties;
import fr.kissy.card_page_layout.engine.use_case.ForGrid;

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
        Stream.Builder<Card> cards = Stream.builder();

        for (WorkingImage image : images) {
            new ForGrid(documentProperties, image.getBufferedImage())
                    .execute((cardIndex, offset) -> {
                        Card card = new Card(image, documentProperties.getCard(), offset.getWidth(), offset.getHeight());
                        cards.add(card);
                    });
        }

        return cards.build();
    }
}
