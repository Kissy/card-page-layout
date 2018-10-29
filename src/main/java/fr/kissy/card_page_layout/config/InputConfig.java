package fr.kissy.card_page_layout.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class InputConfig {
    private final String name;
    private final String path;
    private final String pages;
    private final GridSize grid;
    private final CardSize card;

    @JsonCreator
    public InputConfig(
            @JsonProperty("name") String name,
            @JsonProperty("path") String path,
            @JsonProperty("pages") String pages,
            @JsonProperty("grid") GridSize grid,
            @JsonProperty("card") CardSize card
    ) {
        this.name = name;
        this.path = path;
        this.pages = pages;
        this.grid = grid;
        this.card = card;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public String getPages() {
        return pages;
    }

    public GridSize getGrid() {
        return grid;
    }

    public CardSize getCard() {
        return card;
    }
}
