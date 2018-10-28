package fr.kissy.card_page_layout.engine.model;

import java.nio.file.Path;
import java.util.List;

public class WorkingDocument {
    private final Path path;
    private final List<WorkingImage> images;
    public WorkingDocument(Path path, List<WorkingImage> images) {
        this.path = path;
        this.images = images;
    }

    public Path getPath() {
        return path;
    }

    public List<WorkingImage> getImages() {
        return images;
    }
}
