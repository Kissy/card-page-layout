package fr.kissy.card_page_layout.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Offset {
    private final Integer width;
    private final Integer height;

    @JsonCreator
    public Offset(
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
