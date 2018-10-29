package fr.kissy.card_page_layout.engine.event;

import fr.kissy.card_page_layout.config.DocumentProperties;

import java.util.Objects;

public class ImportInputConfig {

    private final DocumentProperties documentProperties;

    public ImportInputConfig(DocumentProperties documentProperties) {
        this.documentProperties = documentProperties;
    }

    public DocumentProperties getDocumentProperties() {
        return documentProperties;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImportInputConfig that = (ImportInputConfig) o;
        return Objects.equals(documentProperties, that.documentProperties);
    }

    @Override
    public int hashCode() {
        return Objects.hash(documentProperties);
    }
}
