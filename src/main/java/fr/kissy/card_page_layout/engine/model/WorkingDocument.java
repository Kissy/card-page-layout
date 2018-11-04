package fr.kissy.card_page_layout.engine.model;

import fr.kissy.card_page_layout.config.DocumentProperties;
import fr.kissy.card_page_layout.engine.use_case.ForGrid;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class WorkingDocument {
    private final DocumentProperties documentProperties;
    private final List<WorkingImage> frontImages;
    private final List<WorkingImage> backImages;

    public WorkingDocument(DocumentProperties documentProperties, List<WorkingImage> frontImages, List<WorkingImage> backImages) {
        this.documentProperties = documentProperties;
        this.frontImages = frontImages;
        this.backImages = backImages;
    }

    public Stream<FrontAndBackCard> getCards() {
        List<Card> frontCards = new ArrayList<>();
        for (WorkingImage image : frontImages) {
            new ForGrid(documentProperties, image.getBufferedImage(), false)
                    .execute((cardIndex, offset) -> {
                        Card front = new Card(image, documentProperties.getCard(), offset.getWidth(), offset.getHeight());
                        frontCards.add(front);
                    });
        }

        List<Card> backCards = new ArrayList<>();
        if (backImages != null && !backImages.isEmpty()) {
            DocumentProperties documentPropertiesBack = documentProperties.getBack();
            for (WorkingImage image : backImages) {
                new ForGrid(documentPropertiesBack, image.getBufferedImage(), true)
                        .execute((cardIndex, offset) -> {
                            Card back = new Card(image, documentPropertiesBack.getCard(), offset.getWidth(), offset.getHeight());
                            backCards.add(back);
                        });
            }
        }

        Stream.Builder<FrontAndBackCard> cards = Stream.builder();
        for (int index = 0; index < frontCards.size(); index++) {
            Card backCard = backCards.isEmpty() ? null : backCards.get(index % backCards.size());
            cards.add(new FrontAndBackCard(frontCards.get(index), backCard));
        }
        return cards.build();
    }
}
