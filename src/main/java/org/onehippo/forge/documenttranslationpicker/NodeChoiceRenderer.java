package org.onehippo.forge.documenttranslationpicker;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.Localizer;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Renders the node using the NodeNameBuilder
 * @author Ivor Boers @ Finalist / Open IT
 * @since 14 apr. 2016
 */
final class NodeChoiceRenderer implements IChoiceRenderer<Node> {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = LoggerFactory.getLogger(NodeChoiceRenderer.class);
    private Component component;
    
    public NodeChoiceRenderer(Component component) {
        this.component = component;
    }
    
    @Override
    public Object getDisplayValue(Node node) {
        if (node == null) {
            Localizer localizer = Application.get().getResourceSettings().getLocalizer();
            return localizer.getString("translation.none", component);
        }
        return new NodeNameBuilder(node).build();
    }

    @Override
    public String getIdValue(Node object, int index) {
        try {
            if (object != null) {
                return object.getIdentifier();
            }
        } catch (RepositoryException e) {
            LOG.error("Failes to get Node identifier", e);
            return "node_" + index;
        }
        return "";
    }
}