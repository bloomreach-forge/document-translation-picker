package org.onehippo.forge.documenttranslationpicker;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Builds the name of a node.
 * @author Ivor Boers @ Finalist / Open IT
 * @since 14 apr. 2016
 */
public class NodeNameBuilder {
    private static final Logger LOG = LoggerFactory.getLogger(NodeNameBuilder.class);
    private static final String HIPPO_TRANSLATED = "hippo:translated";
    private static final String HIPPO_MESSAGE = "hippo:message";
    private static final String HIPPO_TRANSLATION = "hippo:translation";
    private final Node node;

    public NodeNameBuilder(Node node) {
        this.node = node;
    }

    /**
     * @return the translated message of node with mixin hippo:translated or else the node name
     */
    public String build() {
        if (node != null) {
            try {
                if (node.isNodeType(HIPPO_TRANSLATED) && node.hasNode(HIPPO_TRANSLATION)) {
                    return node.getNode(HIPPO_TRANSLATION).getProperty(HIPPO_MESSAGE).getString();
                }
                return node.getName();
            } catch (RepositoryException e) {
                LOG.error("Failed to build node name, returning an empty String", e);
            }
        }
        // TODO get resource for 'No translation'
        return "";
    }
}
