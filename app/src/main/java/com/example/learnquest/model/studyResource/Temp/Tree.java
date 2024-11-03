package com.example.learnquest.model.studyResource.Temp;


import com.example.learnquest.model.studyResource.StudyResource;

import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;

public class Tree {

    private TreeNode currentRoot;
    private final TreeNode root;

    public Tree() {
        root = new InnerNode("File Resources", NodeType.INNER, null);
        currentRoot = root;  // Initialize currentRoot to root
    }

    public StudyResource getResource(){
        if (currentRoot instanceof LeafNode)
            return ((LeafNode)currentRoot).getCargo();
        else
            return null;
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

    public boolean isRootLeaf(){
        return currentRoot instanceof LeafNode;
    }

    public boolean isRoot(){
        return currentRoot == root;
    }

    public void addResource(StudyResource studyResource) {
        String[] divisions = studyResource.getFilePath().split("/"); // Split the file path by "/"
        insertRecursively(currentRoot, divisions, 0, studyResource); // Start recursion from currentRoot
    }

    private void insertRecursively(TreeNode currentNode, String[] path, int index, StudyResource studyResource) {
        // If we reached the last part of the path, add a LeafNode
        if (index == path.length - 1) {
            LeafNode leafNode = new LeafNode(studyResource.getFileName(), NodeType.LEAF, studyResource, currentNode);
            if (currentNode instanceof InnerNode) {
                ((InnerNode) currentNode).addChild(studyResource.getFileName(), leafNode); // Add leaf node
            }
        } else {
            // Check if the current node is an InnerNode and has the child node
            InnerNode innerNode = (InnerNode) currentNode;
            TreeNode nextNode = innerNode.getChild(path[index]);

            // If the child node doesn't exist, create a new InnerNode
            if (nextNode == null) {
                nextNode = new InnerNode(path[index], NodeType.INNER, currentNode);
                innerNode.addChild(path[index], nextNode); // Add the inner node
            }

            // Recursively move to the next part of the path
            insertRecursively(nextNode, path, index + 1, studyResource);
        }
    }


    public String setParentToCurrent() {
        if (currentRoot.getParent() != null) {
            currentRoot = currentRoot.getParent();
            return currentRoot.getName();
        }
        return "Already at the root!";
    }

    // Method to return the children of the current node in a TreeMap with custom sorting
    public List<TreeNode> getCurrentChildren() {
        if (currentRoot instanceof InnerNode) {
            InnerNode innerNode = (InnerNode) currentRoot;
            List<TreeNode> children = innerNode.getChildren();


            return children;

        } else {
            // If the current node is a leaf, return an empty TreeMap
            return null;
        }
    }

    public static Comparator<TreeNode> getComparator() {
        return Comparator.comparing(TreeNode::getNodeType).thenComparing(TreeNode::getName);
    }







}
