package fr.kissy.card_page_layout.engine.event;

import java.nio.file.Path;
import java.util.Objects;

public class PdfFileOpened {

    private final Path path;

    public PdfFileOpened(Path path) {
        this.path = path;
    }

    public Path getPath() {
        return path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PdfFileOpened that = (PdfFileOpened) o;
        return Objects.equals(path, that.path);
    }

    @Override
    public int hashCode() {

        return Objects.hash(path);
    }
}
