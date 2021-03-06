package fr.kissy.card_page_layout.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Strings;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class DocumentProperties {
    private final String path;
    private final DocumentProperties back;
    private final String pages;
    private final PageSize page;
    private final GridSize grid;
    private final CardSize card;
    private final Margin margin;
    private final Offset offset;

    @JsonCreator
    public DocumentProperties(
            @JsonProperty("path") String path,
            @JsonProperty("back") DocumentProperties back,
            @JsonProperty("pages") String pages,
            @JsonProperty("page") PageSize page,
            @JsonProperty("grid") GridSize grid,
            @JsonProperty("card") CardSize card,
            @JsonProperty("margin") Margin margin,
            @JsonProperty("offset") Offset offset
    ) {
        this.path = path;
        this.back = back;
        this.pages = pages;
        this.page = page;
        this.grid = grid == null ? new GridSize(3, 3) : grid;
        this.card = card == null ? new CardSize(750, 1050) : card;
        this.margin = margin == null ? new Margin(0, 0) : margin;
        this.offset = offset == null ? new Offset(0, 0) : offset;
    }

    public String getPath() {
        return path;
    }

    public DocumentProperties getBack() {
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

    public Margin getMargin() {
        return margin;
    }

    public Offset getOffset() {
        return offset;
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
