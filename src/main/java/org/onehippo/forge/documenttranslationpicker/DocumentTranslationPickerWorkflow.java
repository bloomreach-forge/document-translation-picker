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

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;
import org.hippoecm.addon.workflow.StdWorkflow;
import org.hippoecm.addon.workflow.WorkflowDescriptorModel;
import org.hippoecm.frontend.dialog.IDialogService.Dialog;
import org.hippoecm.frontend.plugin.IPluginContext;
import org.hippoecm.frontend.plugins.standards.icon.HippoIcon;
import org.hippoecm.frontend.skin.Icon;
import org.hippoecm.frontend.translation.ILocaleProvider;
import org.onehippo.repository.documentworkflow.DocumentWorkflow;

/**
 * @author Ivor Boers @ Finalist / Open IT
 * @since 14 apr. 2016
 */
public class DocumentTranslationPickerWorkflow extends StdWorkflow<DocumentWorkflow> {
    private static final long serialVersionUID = 1L;
    private static final String SUBMENU_NAME = "document";
    private ILocaleProvider localeProvider;

    public DocumentTranslationPickerWorkflow(String id, IModel<String> stringIModel, IPluginContext pluginContext,
            WorkflowDescriptorModel model, ILocaleProvider localeProvider) {
        super(id, stringIModel, pluginContext, model);
        this.setLocaleProvider(localeProvider);
    }

    @Override
    protected Component getIcon(String id) {
        return HippoIcon.fromSprite(id, Icon.GLOBE);
    }

    @Override
    public String getSubMenu() {
        return SUBMENU_NAME;
    }

    @Override
    protected Dialog createRequestDialog() {
        return new DocumentTranslationPickerDialog(this);
    }

    public ILocaleProvider getLocaleProvider() {
        return localeProvider;
    }

    public void setLocaleProvider(ILocaleProvider iLocaleProvider) {
        this.localeProvider = iLocaleProvider;
    }

}
