package fr.kissy.card_page_layout.engine.event;

import fr.kissy.card_page_layout.config.InputConfig;

import java.nio.file.Path;
import java.util.Objects;

public class ImportInputConfig {

    private final InputConfig inputConfig;

    public ImportInputConfig(InputConfig inputConfig) {
        this.inputConfig = inputConfig;
    }

    public InputConfig getInputConfig() {
        return inputConfig;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImportInputConfig that = (ImportInputConfig) o;
        return Objects.equals(inputConfig, that.inputConfig);
    }

    @Override
    public int hashCode() {
        return Objects.hash(inputConfig);
    }
}
