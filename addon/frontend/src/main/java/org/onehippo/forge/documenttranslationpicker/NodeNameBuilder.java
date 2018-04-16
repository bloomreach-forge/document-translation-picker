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
