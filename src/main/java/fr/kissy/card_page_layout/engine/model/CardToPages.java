package fr.kissy.card_page_layout.engine.model;

import com.google.common.collect.Sets;
import com.sun.jmx.remote.internal.ArrayQueue;
import fr.kissy.card_page_layout.config.DocumentProperties;
import fr.kissy.card_page_layout.config.GridSize;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class CardToPages implements Collector<Card, Deque<List<Card>>, List<Page>> {

    private final int numberPerPages;
    private DocumentProperties outputDocumentProperties;

    public CardToPages(DocumentProperties outputDocumentProperties) {
        this.outputDocumentProperties = outputDocumentProperties;
        this.numberPerPages = outputDocumentProperties.getGrid().getCols() * outputDocumentProperties.getGrid().getRows();
    }

    @Override
    public Supplier<Deque<List<Card>>> supplier() {
        return ArrayDeque::new;
    }

    @Override
    public BiConsumer<Deque<List<Card>>, Card> accumulator() {
        return ((cards, card) -> {
            if (cards.isEmpty() || cards.peekLast().size() == numberPerPages) {
                cards.add(new ArrayList<>());
            }
            cards.peekLast().add(card);
        });
    }

    @Override
    public BinaryOperator<Deque<List<Card>>> combiner() {
        return (firstCards, secondCards) -> {
            throw new UnsupportedOperationException("Cannot combine pages");
        };
    }

    @Override
    public Function<Deque<List<Card>>, List<Page>> finisher() {
        return (cards) -> cards.stream()
                .map(pageCards -> new Page(outputDocumentProperties, pageCards))
                .collect(Collectors.toList());
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Sets.newHashSet();
    }
}
