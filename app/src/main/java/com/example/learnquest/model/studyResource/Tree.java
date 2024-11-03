package com.example.learnquest.model.studyResource;

import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Tree<T> {

    private TreeNode currentRoot;
    private final TreeNode root;

    public Tree() {
        root = new InnerNode("File Resources", NodeType.INNER, null);
        currentRoot = root;  // Initialize currentRoot to root
    }

    public String getCurrentName() {
        return currentRoot.getName();
    }

    public void setCurrentName(String name) {
        this.currentRoot.setName(name);
    }

    public String setCurrentChild(String name) {
        if (currentRoot instanceof InnerNode) {
            TreeNode child = ((InnerNode) currentRoot).getChild(name);
            if (child != null) {
                currentRoot = child;
                return currentRoot.getName();
            } else {
                return "Child node not found!";
            }
        } else if (currentRoot instanceof LeafNode) {
            return "Current node is a leaf node and has no children!";
        }
        return null;
    }

    public String setCurrentToParent() {
        if (currentRoot.getParent() != null) {
            currentRoot = currentRoot.getParent();
            return currentRoot.getName();
        }
        return "Already at the root!";
    }

    // Method to return the children of the current node in a TreeMap with custom sorting
    public List<String> getCurrentChildren() {
        if (currentRoot instanceof InnerNode) {
            InnerNode innerNode = (InnerNode) currentRoot;
            List<TreeNode> children = innerNode.getChildren();

            return children.stream().sorted(getComparator()).map(TreeNode::getName).collect(Collectors.toList());

        } else {
            // If the current node is a leaf, return an empty TreeMap
            return null;
        }
    }

    public enum NodeType {
        INNER, LEAF
    }

    public static abstract class TreeNode {
        private String name;
        private final NodeType nodeType;
        private final TreeNode parent;

        public TreeNode(String name, NodeType nodeType, TreeNode parent) {
            this.name = name;
            this.nodeType = nodeType;
            this.parent = parent;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public NodeType getNodeType() {
            return nodeType;
        }

        public TreeNode getParent() {
            return parent;
        }

        @Override
        public String toString() {
            return "TreeNode{name='" + name + "', nodeType=" + nodeType + '}';
        }
    }

    public static class LeafNode<T> extends TreeNode {
        private T object;

        public LeafNode(String name, NodeType nodeType, T object, TreeNode parent) {
            super(name, nodeType, parent);
            this.object = object;
        }

        public T getCargo() {
            return object;
        }

        public void setCargo(T object) {
            this.object = object;
        }
    }

    public static class InnerNode extends TreeNode {
        private final TreeMap<String, TreeNode> children;

        public InnerNode(String name, NodeType nodeType, TreeNode parent) {
            super(name, nodeType, parent);
            children = new TreeMap<>();
        }

        public void addChild(String nodeName, TreeNode treeNode) {
            children.put(nodeName, treeNode);
        }

        public void removeChild(String nodeName) {
            children.remove(nodeName);
        }

        public List<TreeNode> getChildren() {
            return List.copyOf(children.values());
        }

        public TreeNode getChild(String childName) {
            return children.get(childName);
        }
    }


    public static Comparator<TreeNode> getComparator() {
        return Comparator.comparing(TreeNode::getNodeType).thenComparing(TreeNode::getName);
    }
}
