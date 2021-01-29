package org.bloomreach.forge.documenttranslationpicker.beans;
/*
 * Copyright 2014-2019 Hippo B.V. (http://www.onehippo.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import java.util.Calendar;

import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoDocument;
import org.hippoecm.hst.content.beans.standard.HippoGalleryImageSet;
import org.hippoecm.hst.content.beans.standard.HippoHtml;
import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;

@HippoEssentialsGenerated(internalName = "documenttranslationpickerdemo:newsdocument")
@Node(jcrType="documenttranslationpickerdemo:newsdocument")
public class NewsDocument extends HippoDocument {

    /**
     * The document type of the news document.
     */
    public static final String DOCUMENT_TYPE = "documenttranslationpickerdemo:newsdocument";

    private static final String TITLE = "documenttranslationpickerdemo:title";
    private static final String DATE = "documenttranslationpickerdemo:date";
    private static final String INTRODUCTION = "documenttranslationpickerdemo:introduction";
    private static final String IMAGE = "documenttranslationpickerdemo:image";
    private static final String CONTENT = "documenttranslationpickerdemo:content";
    private static final String LOCATION = "documenttranslationpickerdemo:location";
    private static final String AUTHOR = "documenttranslationpickerdemo:author";
    private static final String SOURCE = "documenttranslationpickerdemo:source";

    /**
     * Get the title of the document.
     *
     * @return the title
     */
    @HippoEssentialsGenerated(internalName = "documenttranslationpickerdemo:title")
    public String getTitle() {
        return getSingleProperty(TITLE);
    }

    /**
     * Get the date of the document.
     *
     * @return the date
     */
    @HippoEssentialsGenerated(internalName = "documenttranslationpickerdemo:date")
    public Calendar getDate() {
        return getSingleProperty(DATE);
    }

    /**
     * Get the introduction of the document.
     *
     * @return the introduction
     */
    @HippoEssentialsGenerated(internalName = "documenttranslationpickerdemo:introduction")
    public String getIntroduction() {
        return getSingleProperty(INTRODUCTION);
    }

    /**
     * Get the image of the document.
     *
     * @return the image
     */
    @HippoEssentialsGenerated(internalName = "documenttranslationpickerdemo:image")
    public HippoGalleryImageSet getImage() {
        return getLinkedBean(IMAGE, HippoGalleryImageSet.class);
    }

    /**
     * Get the main content of the document.
     *
     * @return the content
     */
    @HippoEssentialsGenerated(internalName = "documenttranslationpickerdemo:content")
    public HippoHtml getContent() {
        return getHippoHtml(CONTENT);
    }

    /**
     * Get the location of the document.
     *
     * @return the location
     */
    @HippoEssentialsGenerated(internalName = "documenttranslationpickerdemo:location")
    public String getLocation() {
        return getSingleProperty(LOCATION);
    }

    /**
     * Get the author of the document.
     *
     * @return the author
     */
    @HippoEssentialsGenerated(internalName = "documenttranslationpickerdemo:author")
    public String getAuthor() {
        return getSingleProperty(AUTHOR);
    }

    /**
     * Get the source of the document.
     *
     * @return the source
     */
    @HippoEssentialsGenerated(internalName = "documenttranslationpickerdemo:source")
    public String getSource() {
        return getSingleProperty(SOURCE);
    }

}

