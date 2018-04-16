/**
 * Copyright 2014 Finalist B.V. (http://www.finalist.nl)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *         http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
