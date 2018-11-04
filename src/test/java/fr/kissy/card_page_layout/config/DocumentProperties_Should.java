package fr.kissy.card_page_layout.config;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class DocumentProperties_Should {

    @Test
    void return_all_range_from_empty_pages() {
        DocumentProperties documentProperties = new DocumentProperties(null, null, null, null, null, null, null, null, null);
        assertArrayEquals(IntStream.of(1, 2, 3, 4, 5).toArray(), documentProperties.getPagesRange(5).toArray());
    }

    @Test
    void return_selected_range_from_pages_with_comma() {
        DocumentProperties documentProperties = new DocumentProperties(null, null, null, "2, 5, 6", null, null, null, null, null);
        assertArrayEquals(IntStream.of(2, 5).toArray(), documentProperties.getPagesRange(5).toArray());
    }

    @Test
    void return_selected_range_from_pages_with_range() {
        DocumentProperties documentProperties = new DocumentProperties(null, null, null, "2, 4-6", null, null, null, null, null);
        assertArrayEquals(IntStream.of(2, 4, 5).toArray(), documentProperties.getPagesRange(5).toArray());
    }

    @Test
    void return_selected_range_from_pages_with_range_and_comma() {
        DocumentProperties documentProperties = new DocumentProperties(null, null, null, "1-2, 4-6", null, null, null, null, null);
        assertArrayEquals(IntStream.of(1, 2, 4, 5).toArray(), documentProperties.getPagesRange(5).toArray());
    }

    @Test
    void return_selected_range_from_complex_pages() {
        DocumentProperties documentProperties = new DocumentProperties(null, null, null, "0-1, 3, 5, 7-8, 11", null, null, null, null, null);
        assertArrayEquals(IntStream.of(1, 3, 5, 7, 8).toArray(), documentProperties.getPagesRange(10).toArray());
    }

}