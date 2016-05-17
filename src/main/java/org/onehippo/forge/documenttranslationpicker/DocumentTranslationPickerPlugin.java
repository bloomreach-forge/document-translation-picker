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
