package fr.kissy.card_page_layout.config;

import com.beust.jcommander.Parameter;

import java.awt.Dimension;

public class OutputConfig {
    @Parameter(names = {"--output-card-size", "-ocd" }, description = "Output card dimension in weight x height, default to 750x1050")
    public Dimension cardSize = new Dimension(750, 1050);
    @Parameter(names = {"--output-grid-size", "-ogd" }, description = "Output grid dimension in cols x rows, default to 3x3")
    public Dimension gridSize = new Dimension(3, 3);

    @Override
    public String toString() {
        return "OutputConfig{" +
                "cardSize=" + cardSize +
                ", gridSize=" + gridSize +
                '}';
    }
}
