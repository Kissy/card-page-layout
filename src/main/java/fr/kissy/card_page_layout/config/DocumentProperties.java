package fr.kissy.card_page_layout.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Strings;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class DocumentProperties {
    private final String name;
    private final String path;
    private final Boolean back;
    private final String pages;
    private final PageSize page;
    private final GridSize grid;
    private final CardSize card;

    @JsonCreator
    public DocumentProperties(
            @JsonProperty("name") String name,
            @JsonProperty("path") String path,
            @JsonProperty("back") Boolean back,
            @JsonProperty("pages") String pages,
            @JsonProperty("page") PageSize page,
            @JsonProperty("grid") GridSize grid,
            @JsonProperty("card") CardSize card
    ) {
        this.name = name;
        this.path = path;
        this.back = back == null ? false : back;
        this.pages = pages;
        this.page = page;
        this.grid = grid;
        this.card = card;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public Boolean isBack() {
        return back;
    }

    public String getPages() {
        return pages;
    }

    public PageSize getPage() {
        return page;
    }

    public GridSize getGrid() {
        return grid;
    }

    public CardSize getCard() {
        return card;
    }

    public IntStream getPagesRange(int numberOfPages) {
        if (Strings.isNullOrEmpty(pages)) {
            return IntStream.rangeClosed(1, numberOfPages);
        }
        return Stream.of(pages.split(","))
                .map(String::trim)
                .flatMapToInt(s -> {
                    if (s.contains("-")) {
                        String[] range = s.split("-");
                        int start = Integer.parseInt(range[0], 10);
                        int end = Integer.parseInt(range[1], 10);
                        return IntStream.rangeClosed(start, end);
                    } else {
                        return IntStream.of(Integer.parseInt(s, 10));
                    }
                })
                .filter(i -> i > 0 && i <= numberOfPages);
    }
}
