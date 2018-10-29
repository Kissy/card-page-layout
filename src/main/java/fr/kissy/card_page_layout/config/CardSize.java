package fr.kissy.card_page_layout.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CardSize {
    private final Integer width;
    private final Integer height;

    @JsonCreator
    public CardSize(
            @JsonProperty("width") Integer width,
            @JsonProperty("height") Integer height
    ) {
        this.width = width;
        this.height = height;
    }

    public Integer getWidth() {
        return width;
    }

    public Integer getHeight() {
        return height;
    }
}
