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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;

import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.util.value.IValueMap;
import org.apache.wicket.util.value.ValueMap;
import org.hippoecm.addon.workflow.AbstractWorkflowDialog;
import org.hippoecm.frontend.model.JcrNodeModel;
import org.hippoecm.repository.api.WorkflowDescriptor;
import org.hippoecm.repository.translation.HippoTranslatedNode;
import org.hippoecm.repository.translation.HippoTranslationNodeType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Displays a Dialog to select documents that can be regarded as a translation of the document on which the Plugin was
 * invoked.
 * 
 * @author Ivor Boers @ Finalist / "Open IT"
 * @since 14 apr. 2016
 */
public class DocumentTranslationPickerDialog extends AbstractWorkflowDialog<WorkflowDescriptor> {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = LoggerFactory.getLogger(DocumentTranslationPickerDialog.class);
    private static final String HIPPO_HANDLE = "hippo:handle";
    private static final ResourceReference DIALOG_CSS = new CssResourceReference(DocumentTranslationPickerDialog.class,
            "DocumentTranslationPicker.css");;
    private Node documentHandle;
    private WebMarkupContainer holder = new WebMarkupContainer("container-holder");

    private Form<List<TranslationOption>> form = new Form<List<TranslationOption>>("container");
    private List<TranslationOption> folders = new ArrayList<>();

    public DocumentTranslationPickerDialog(DocumentTranslationPickerWorkflow invoker) {
        super(invoker.getModel(), invoker);
        try {
            this.documentHandle = invoker.getModel().getNode();
            try {
                folders = getTranslations(documentHandle);
            } catch (RepositoryException e) {
                LOG.error("Failed to getTranslations", e);
            }
            ListView<TranslationOption> listView = new TranslationOptionsListView("options", invoker.getLocaleProvider());
            listView.setList(folders);
            form.add(listView);
            
            String introductionResource;
            if (folders.isEmpty()) {
                form.setVisible(false);
                introductionResource = "introduction.withoutOptions";
            } else if (folders.size() == 1){
                introductionResource = "introduction.withOneOption";
            } else {
                introductionResource = "introduction.withOptions";
            }
            StringResourceModel introductionText = new StringResourceModel(introductionResource, this, null);
            holder.add(new Label("introduction", introductionText));
            holder.add(form);
            add(holder);
        } catch (RepositoryException e1) {
            LOG.error("Failed to create DocumentTranslationPickerDialog", e1);
            holder.setVisible(false);
        }

    }

    private List<TranslationOption> getTranslations(Node handleNode) throws RepositoryException {
        List<TranslationOption> result = new ArrayList<>();
        if (!isNodeOfType(handleNode, HIPPO_HANDLE)) {
            return result;
        }
        if (!handleNode.hasNode(handleNode.getName())) {
            LOG.warn("HandleNode is expected to have siblings");
            return result;
        }
        Node folderNode = handleNode.getParent();
        if (!isNodeOfType(folderNode, "hippostd:folder", HippoTranslationNodeType.NT_TRANSLATED)) {
            return result;
        }

        // create a HippoTranslatedNode for the first variantnode of the handle (all variants have same translationId)
        HippoTranslatedNode translatedDocNode = new HippoTranslatedNode(handleNode.getNode(handleNode.getName()));
        HippoTranslatedNode translatedFolderNode = new HippoTranslatedNode(folderNode);
        try {
            // iterate over available locales of folder
            for (String translation : translatedFolderNode.getTranslations()) {
                Node translationOfFolderNode = translatedFolderNode.getTranslation(translation);
                // skip current document/folder
                // check on ID, not on node object
                if (!translationOfFolderNode.getIdentifier().equals(handleNode.getParent().getIdentifier())) {
                    TranslationOption option = getTranslationOption(handleNode, translatedDocNode, translation,
                            translationOfFolderNode);
                    result.add(option);
                }
            }
            LOG.debug("Folders: " + result);
        } catch (RepositoryException e) {
            LOG.error("Failed to list translation folders");
        }
        return result;
    }

    private TranslationOption getTranslationOption(Node handleNode, HippoTranslatedNode translatedVariant,
            String translation, Node translationOfFolderNode) throws RepositoryException {

        String primaryTypeOfVariantNode = handleNode.getNodes(handleNode.getName()).nextNode().getPrimaryNodeType()
                .getName();
        List<Node> documentsInFolder = getDocumentsInFolder(translationOfFolderNode, primaryTypeOfVariantNode);

        Node currentTranslationHandle = null;
        if (translatedVariant.hasTranslation(translation)) {
            currentTranslationHandle = translatedVariant.getTranslation(translation);
            if (currentTranslationHandle != null) {
                // use the handleNode
                currentTranslationHandle = currentTranslationHandle.getParent();
            }
        }
        return new TranslationOption(translation, translationOfFolderNode, documentsInFolder, currentTranslationHandle);
    }

    private boolean isNodeOfType(Node node, String... requiredNodeTypes) throws RepositoryException {
        if (node == null) {
            LOG.warn("Expected a node, but was null");
            return false;
        }
        if (requiredNodeTypes != null && requiredNodeTypes.length > 0) {
            for (String requiredNodeType : requiredNodeTypes) {
                if (!node.isNodeType(requiredNodeType)) {
                    LOG.warn("Expected a node with type or mixins: " + Arrays.asList(requiredNodeTypes)
                            + ", but was mixinType " + requiredNodeType + " was not found. path=" + node.getPath());
                    return false;
                }
            }
        }
        return true;
    }

    private List<Node> getDocumentsInFolder(Node folderNode, String documentType) throws RepositoryException {
        NodeIterator handleNodeIterator = folderNode.getNodes();
        List<Node> nodes = new ArrayList<>();
        nodes.add(null); // add option to select no document
        // add handle nodes with documents of type documentType
        while (handleNodeIterator.hasNext()) {
            Node handleNode = handleNodeIterator.nextNode();
            if (isHandleNodeWithVariants(handleNode)) {
                NodeIterator documentNodes = handleNode.getNodes(handleNode.getName());
                if (documentNodes.hasNext()
                        && documentNodes.nextNode().getPrimaryNodeType().getName().equals(documentType)) {
                    nodes.add(handleNode);
                }
            }
        }
        return nodes;
    }

    private boolean isHandleNodeWithVariants(Node handleNode) throws RepositoryException {
        return handleNode.getPrimaryNodeType().getName().equals(HIPPO_HANDLE)
                && handleNode.hasNode(handleNode.getName());
    }

    @Override
    public IModel<String> getTitle() {
        return new StringResourceModel("dialog.title", this, null);
    }

    @Override
    protected void onOk() {
        try {
            if (documentHandle != null) {
                LOG.info("Clicked OK. node=" + documentHandle.getPath());
                String newTranslationId = UUID.randomUUID().toString();
                setTranslationId(documentHandle, newTranslationId);
    
                for (TranslationOption option : folders) {
                    setTranslationId(option.getCurrentTranslation(), newTranslationId);
                }
            }
        } catch (RepositoryException e) {
            LOG.error("Failed to perform OK action", e);
        }
    }

    private void setTranslationId(Node handleNode, String translationId) {
        if (handleNode != null) {
            try {
                NodeIterator docNodes = handleNode.getNodes(handleNode.getName());
                while (docNodes.hasNext()) {
                    Node docNode = docNodes.nextNode();
                    LOG.debug("Setting translationID of " + docNode.getPath() + " to " + translationId);
                    docNode.setProperty(HippoTranslationNodeType.ID, translationId);
                    docNode.getSession().save();
                    docNode.getSession().refresh(false);
                }
            } catch (RepositoryException e) {
                LOG.error("could not set property hippotranslation:id for document "
                        + new JcrNodeModel(handleNode).getItemModel().getPath(), e);
            }
        }
    }

    @Override
    public void renderHead(final IHeaderResponse response) {
        super.renderHead(response);
        response.render(CssHeaderItem.forReference(DIALOG_CSS));
    }

    @Override
    public IValueMap getProperties() {
        return new ValueMap("width=375,height=300").makeImmutable();
    }

}
