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

import org.apache.wicket.model.StringResourceModel;
import org.hippoecm.addon.workflow.WorkflowDescriptorModel;
import org.hippoecm.frontend.plugin.IPluginContext;
import org.hippoecm.frontend.plugin.config.IPluginConfig;
import org.hippoecm.frontend.service.render.RenderPlugin;
import org.hippoecm.frontend.translation.ILocaleProvider;
import org.hippoecm.repository.api.WorkflowDescriptor;

/**
 * @author Ivor Boers @ Finalist / "Open IT"
 * @since 15 apr. 2016
 */
public class DocumentTranslationPickerPlugin extends RenderPlugin<WorkflowDescriptor> {
    private static final long serialVersionUID = 1L;

    public DocumentTranslationPickerPlugin(IPluginContext context, IPluginConfig config) {
        super(context, config);

        StringResourceModel title = new StringResourceModel("plugin.menuitem.title", this, null);
        add(new DocumentTranslationPickerWorkflow("documenttranslation", title, context,
                (WorkflowDescriptorModel) getModel(), getLocaleProvider()));
    }

    protected ILocaleProvider getLocaleProvider() {
        String localeProviderServiceName = getPluginConfig().getString(ILocaleProvider.SERVICE_ID,
                ILocaleProvider.class.getName());
        return getPluginContext().getService(localeProviderServiceName, ILocaleProvider.class);
    }

}
