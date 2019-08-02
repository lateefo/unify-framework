/*
 * Copyright 2018-2019 The Code Department.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.tcdng.unify.web.ui.data;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.tcdng.unify.core.UnifyCoreErrorConstants;
import com.tcdng.unify.core.UnifyException;
import com.tcdng.unify.core.data.MarkedTree;
import com.tcdng.unify.core.data.MarkedTree.MarkedTreeItemMatcher;
import com.tcdng.unify.core.data.MarkedTree.MarkedTreePolicy;
import com.tcdng.unify.core.data.MarkedTree.Node;
import com.tcdng.unify.web.ui.data.TreeTypeInfo.ExtendedTreeItemTypeInfo;

/**
 * Tree object.
 * 
 * @author Lateef Ojulari
 * @since 1.0
 */
public class Tree {

    private static final TreeItemExpander expandChildPolicy = new TreeItemExpander();

    private static final TreeItemCollapser collapseChildPolicy = new TreeItemCollapser();

    private TreeTypeInfo typeInfo;

    private MarkedTree<TreeItem> markedTree;

    private List<Long> selectedItemIdList;

    private Queue<TreeEvent> eventQueue;

    private Tree(TreeTypeInfo typeInfo, MarkedTree<TreeItem> markedTree) {
        this.typeInfo = typeInfo;
        this.markedTree = markedTree;
        selectedItemIdList = Collections.emptyList();
        eventQueue = new LinkedList<TreeEvent>();
    }

    public Long addTreeItem(Long parentItemId, String itemTypeCode, Object item) throws UnifyException {
        TreeItemTypeInfo treeItemTypeInfo = typeInfo.getTreeItemTypeInfo(itemTypeCode);
        if (treeItemTypeInfo == null) {
            throw new UnifyException(UnifyCoreErrorConstants.COMPONENT_OPERATION_ERROR,
                    "Unknown tree item type code [" + itemTypeCode + "].");
        }

        Long itemId = markedTree.addChild(parentItemId, new TreeItem(treeItemTypeInfo, item));
        markedTree.updateParentNodes(itemId, expandChildPolicy); // Expand parents
        return itemId;
    }

    public void setTreePolicy(MarkedTreePolicy<TreeItem> treePolicy) {
        markedTree.setTreePolicy(treePolicy);
    }

    public TreePolicy getTreePolicy() {
        return (TreePolicy) markedTree.getTreePolicy();
    }

    public boolean isTreePolicy() {
        return markedTree.isTreePolicy();
    }

    public Node<TreeItem> getRootNode() {
        return markedTree.getRoot();
    }

    public Node<TreeItem> getNode(Long itemId) {
        return markedTree.getNode(itemId);
    }

    public Node<TreeItem> getNode(TreeEvent treeEvent, int index) {
        return markedTree.getNode(treeEvent.getItemIds().get(index));
    }

    public Node<TreeItem> findFirstNode(Long firstItemId, MarkedTreeItemMatcher<TreeItem> matcher)
            throws UnifyException {
        return markedTree.findFirstNode(firstItemId, matcher);
    }

    public List<Node<TreeItem>> findNodes(Long firstItemId, MarkedTreeItemMatcher<TreeItem> matcher)
            throws UnifyException {
        return markedTree.findNodes(firstItemId, matcher);
    }

    public Node<TreeItem> findFirstChildNode(Long parentItemId, MarkedTreeItemMatcher<TreeItem> childMatcher)
            throws UnifyException {
        return markedTree.getFirstChildNode(parentItemId, childMatcher);
    }

    public List<Node<TreeItem>> getChildNodes(Long parentItemId) throws UnifyException {
        return markedTree.getChildNodes(parentItemId);
    }

    public List<Node<TreeItem>> getChildNodes(Long parentItemId, MarkedTreeItemMatcher<TreeItem> matcher)
            throws UnifyException {
        return markedTree.getChildNodes(parentItemId, matcher);
    }

    public Node<TreeItem> getParentNode(Long itemId) {
        return markedTree.getParentNode(itemId);
    }

    public List<TreeItem> getChildTreeItems(Long parentItemId) throws UnifyException {
        return markedTree.getChildItems(parentItemId);
    }

    public List<TreeItem> getChildTreeItems(Long parentItemId, MarkedTreeItemMatcher<TreeItem> matcher)
            throws UnifyException {
        return markedTree.getChildItems(parentItemId, matcher);
    }

    public TreeItem getTreeItem(Long itemId) {
        Node<TreeItem> node = markedTree.getNode(itemId);
        if (node != null) {
            return node.getItem();
        }

        return null;
    }

    public TreeItem getTreeItem(TreeEvent treeEvent, int index) {
        return getTreeItem(treeEvent.getItemIds().get(index));
    }

    public int itemCount() {
        return markedTree.size();
    }

    public TreeItemTypeInfo getTreeItemTypeInfo(String itemTypeCode) {
        return typeInfo.getTreeItemTypeInfo(itemTypeCode);
    }

    public Collection<ExtendedTreeItemTypeInfo> getExtendedTreeItemTypeInfos() {
        return typeInfo.getExtendedTreeItemTypeInfos();
    }

    public List<TreeMenuItemInfo> getMenuItemInfoList() {
        return typeInfo.getMenuItemInfoList();
    }

    public boolean isMenuItemList() {
        return typeInfo.isMenuItemList();
    }

    public void setSelectedItems(List<Long> selectedItemIdList) {
        this.selectedItemIdList = selectedItemIdList;
        for (Long itemId : selectedItemIdList) {
            try {
                markedTree.updateParentNodes(itemId, expandChildPolicy);
            } catch (UnifyException e) {
            }
        }
    }

    public List<Long> getSelectedItems() {
        return selectedItemIdList;
    }

    public void registerEvent(TreeEventType eventType, String menuCode) {
        eventQueue.offer(new TreeEvent(eventType, menuCode, selectedItemIdList));
    }

    public void registerEvent(TreeEventType eventType, Long dropTrgItemId, String srcLongName,
            List<Long> srcItemIdList) {
        eventQueue.offer(new TreeEvent(eventType, selectedItemIdList, dropTrgItemId, srcLongName, srcItemIdList));
    }

    public TreeEvent getEvent() {
        return eventQueue.poll();
    }

    public void collapseAll() throws UnifyException {
        markedTree.updateNodes(collapseChildPolicy);
    }

    public boolean collapse(Long itemId) {
        TreeItem treeItem = getTreeItem(itemId);
        if (treeItem != null) {
            treeItem.setExpanded(false);
            return true;
        }

        return false;
    }

    public void expandAll() throws UnifyException {
        markedTree.updateNodes(expandChildPolicy);
    }

    public boolean expand(Long itemId) {
        TreeItem treeItem = getTreeItem(itemId);
        if (treeItem != null) {
            treeItem.setExpanded(true);
            return true;
        }

        return false;
    }

    public static Builder newBuilder(TreeTypeInfo typeInfo) {
        return new Builder(typeInfo);
    }

    public static class Builder {

        private TreeTypeInfo typeInfo;

        private MarkedTree<TreeItem> markedTree;

        private Builder(TreeTypeInfo typeInfo) {
            this.typeInfo = typeInfo;
            this.markedTree = new MarkedTree<TreeItem>(new TreeItem());
        }

        public Builder addTreeItem(String type, Object item) throws UnifyException {
            if (!typeInfo.isTreeItemType(type)) {
                throw new UnifyException(UnifyCoreErrorConstants.COMPONENT_OPERATION_ERROR,
                        "Unknown category [" + type + "].");
            }

            markedTree.add(new TreeItem(typeInfo.getTreeItemTypeInfo(type), item));
            return this;
        }

        public Node<TreeItem> getLastNode() {
            return markedTree.getChainLast();
        }

        public boolean descend() throws UnifyException {
            return markedTree.descend();
        }

        public boolean ascend() throws UnifyException {
            return markedTree.ascend();
        }

        public Tree build() throws UnifyException {
            markedTree.setChain(false); // Enter unchained mode
            return new Tree(typeInfo, markedTree);
        }
    }

}
