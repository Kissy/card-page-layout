package fr.kissy.card_page_layout.engine.event;

import fr.kissy.card_page_layout.engine.model.WorkingDocument;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Objects;

public class WorkingDocumentImported {

    private final WorkingDocument workingDocument;

    public WorkingDocumentImported(WorkingDocument workingDocument) {
        this.workingDocument = workingDocument;
    }

    public WorkingDocument getWorkingDocument() {
        return workingDocument;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkingDocumentImported that = (WorkingDocumentImported) o;
        return Objects.equals(workingDocument, that.workingDocument);
    }

    @Override
    public int hashCode() {
        return Objects.hash(workingDocument);
    }
}
