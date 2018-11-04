package fr.kissy.card_page_layout.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GridSize {
    private final Integer cols;
    private final boolean reverseCols;
    private final Integer rows;
    private final boolean reverseRows;

    @JsonCreator
    public GridSize(
            @JsonProperty("cols") Integer cols,
            @JsonProperty("reverseCols") Boolean reverseCols,
            @JsonProperty("rows") Integer rows,
            @JsonProperty("reverseRows") Boolean reverseRows) {
        this.cols = cols;
        this.reverseCols = reverseCols == null ? false : reverseCols;
        this.rows = rows;
        this.reverseRows = reverseRows == null ? false : reverseRows;
    }

    public Integer getCols() {
        return cols;
    }

    public boolean getReverseCols() {
        return reverseCols;
    }

    public Integer getRows() {
        return rows;
    }

    public boolean getReverseRows() {
        return reverseRows;
    }
}
