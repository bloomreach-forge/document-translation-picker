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

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.resource.ResourceReference;
import org.hippoecm.frontend.plugins.standards.image.CachingImage;
import org.hippoecm.frontend.service.IconSize;
import org.hippoecm.frontend.translation.ILocaleProvider;
import org.hippoecm.frontend.translation.ILocaleProvider.HippoLocale;
import org.hippoecm.frontend.translation.ILocaleProvider.LocaleState;

/**
 * Listview to display a List of TranslationOption
 * @author Ivor Boers @ Finalist / "Open IT"
 *
 */
final class TranslationOptionsListView extends ListView<TranslationOption> {
    private static final long serialVersionUID = 1L;
    private final ILocaleProvider iLocaleProvider;
    
    /**
     * @param id Wicket id of the container
     * @param iLocaleProvider Provider for content locale information
     */
    TranslationOptionsListView(String id, ILocaleProvider iLocaleProvider) {
        super(id);
        this.iLocaleProvider = iLocaleProvider;
        setReuseItems(true); // mandatory in a form
    }

    @Override
    protected void populateItem(ListItem<TranslationOption> item) {
        TranslationOption modelObject = item.getModel().getObject();

        item.add(getSelect(item, modelObject));
        item.add(getLocaleIcon(modelObject));
        item.add(getFolderLabel(modelObject));
    }

    private Label getFolderLabel(TranslationOption modelObject) {
        String name = new NodeNameBuilder(modelObject.getFolderNode()).build();
        return new Label("translationLabel", name);
    }

    private CachingImage getLocaleIcon(TranslationOption modelObject) {
        HippoLocale hippoLocale = iLocaleProvider.getLocale(modelObject.getLocale());
        ResourceReference icon = hippoLocale.getIcon(IconSize.S, LocaleState.DOCUMENT);
        return new CachingImage("img", icon);
    }

    private DropDownChoice<Node> getSelect(ListItem<TranslationOption> item, TranslationOption modelObject) {
        DropDownChoice<Node> result = new DropDownChoice<>(
                "translationDropdown", 
                new PropertyModel<Node>(item.getDefaultModelObject(), "currentTranslation"),
                new LoadableDetachableModel<List<Node>>() {
                    private static final long serialVersionUID = 1L;

                    @Override
                    protected List<Node> load() { 
                        return modelObject.getDocumentsInFolder();
                    }
                }, new NodeChoiceRenderer(getParent())
        );
        result.setNullValid(true);
        
        return result;
    }
}