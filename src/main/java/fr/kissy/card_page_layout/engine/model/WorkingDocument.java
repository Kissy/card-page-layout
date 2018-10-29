package fr.kissy.card_page_layout.engine.model;

import fr.kissy.card_page_layout.config.InputConfig;

import java.nio.file.Path;
import java.util.List;

public class WorkingDocument {
    private final InputConfig inputConfig;
    private final List<WorkingImage> images;
    public WorkingDocument(InputConfig inputConfig, List<WorkingImage> images) {
        this.inputConfig = inputConfig;
        this.images = images;
    }

    public InputConfig getInputConfig() {
        return inputConfig;
    }

    public List<WorkingImage> getImages() {
        return images;
    }
}
