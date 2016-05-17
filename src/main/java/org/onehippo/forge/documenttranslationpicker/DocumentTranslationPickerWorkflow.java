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
