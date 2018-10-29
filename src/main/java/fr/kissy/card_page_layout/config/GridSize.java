package fr.kissy.card_page_layout.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GridSize {
    private final Integer cols;
    private final Integer rows;

    @JsonCreator
    public GridSize(
            @JsonProperty("cols") Integer cols,
            @JsonProperty("rows") Integer rows
    ) {
        this.cols = cols;
        this.rows = rows;
    }

    public Integer getCols() {
        return cols;
    }

    public Integer getRows() {
        return rows;
    }
}
