package org.onehippo.forge.documenttranslationpicker;

import java.util.List;

import javax.jcr.Node;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * @author Ivor Boers @ Finalist / "Open IT"
 * @since 15 apr. 2016
 */
public class TranslationOption implements Comparable<TranslationOption> {

    private final String locale;
    private final Node folderNode;
    private final List<Node> documentHandlesInFolder;
    private Node currentTranslation;

    public TranslationOption(String locale, Node folderNode, List<Node> documentsInFolder, Node currentTranslation) {
        super();
        this.locale = locale;
        this.folderNode = folderNode;
        this.documentHandlesInFolder = documentsInFolder;
        this.setCurrentTranslation(currentTranslation);
    }
    
    public String getLocale() {
        return locale;
    }

    public Node getFolderNode() {
        return folderNode;
    }
    
    public List<Node> getDocumentsInFolder() {
        return documentHandlesInFolder;
    }

    @Override
    public int compareTo(TranslationOption o) {
        return locale.compareTo(o.getLocale());
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TranslationOption) {
            TranslationOption other = (TranslationOption)obj;
            return new EqualsBuilder().append(locale, other.getLocale()).append(folderNode, other.getFolderNode())
                    .append(documentHandlesInFolder, other.getDocumentsInFolder()).isEquals();
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(locale).append(folderNode).append(documentHandlesInFolder).toHashCode();
    }

    public Node getCurrentTranslation() {
        return currentTranslation;
    }

    public void setCurrentTranslation(Node currentTranslation) {
        this.currentTranslation = currentTranslation;
    }

    @Override
    public String toString() {
        return "TranslationOption [locale=" + locale + ", folderNode=" + folderNode + ", documentHandlesInFolder="
                + documentHandlesInFolder + ", currentTranslation=" + currentTranslation + "]";
    }
    
    
}
