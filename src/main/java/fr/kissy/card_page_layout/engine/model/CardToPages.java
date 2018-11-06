package fr.kissy.card_page_layout.engine.model;

import com.google.common.collect.Sets;
import fr.kissy.card_page_layout.config.DocumentProperties;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CardToPages implements Collector<FrontAndBackCard, Deque<List<FrontAndBackCard>>, List<Page>> {

    private final int numberPerPages;
    private DocumentProperties outputDocumentProperties;

    public CardToPages(DocumentProperties outputDocumentProperties) {
        this.outputDocumentProperties = outputDocumentProperties;
        this.numberPerPages = outputDocumentProperties.getGrid().getCols() * outputDocumentProperties.getGrid().getRows();
    }

    @Override
    public Supplier<Deque<List<FrontAndBackCard>>> supplier() {
        return ArrayDeque::new;
    }

    @Override
    public BiConsumer<Deque<List<FrontAndBackCard>>, FrontAndBackCard> accumulator() {
        return ((cards, frontAndBackCard) -> {
            if (cards.isEmpty() || cards.peekLast().size() == numberPerPages) {
                cards.add(new ArrayList<>());
            }
            cards.peekLast().add(frontAndBackCard);
        });
    }

    @Override
    public BinaryOperator<Deque<List<FrontAndBackCard>>> combiner() {
        return (firstCards, secondCards) -> {
            throw new UnsupportedOperationException("Cannot combine pages");
        };
    }

    @Override
    public Function<Deque<List<FrontAndBackCard>>, List<Page>> finisher() {
        return (cards) -> cards.stream()
                .flatMap(pageCards -> {
                    Stream.Builder<Page> builder = Stream.builder();
                    builder.add(new Page(outputDocumentProperties, pageCards.stream().map(FrontAndBackCard::getFrontCard).collect(Collectors.toList()), false));
                    if (pageCards.stream().map(FrontAndBackCard::getBackCard).allMatch(Objects::nonNull)) {
                        builder.add(new Page(outputDocumentProperties, pageCards.stream().map(FrontAndBackCard::getBackCard).collect(Collectors.toList()), true));
                    }
                    return builder.build();
                })
                .collect(Collectors.toList());
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Sets.newHashSet();
    }

}
