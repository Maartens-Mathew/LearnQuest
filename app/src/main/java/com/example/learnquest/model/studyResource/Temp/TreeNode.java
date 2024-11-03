package com.example.learnquest.model.studyResource.Temp;

import com.example.learnquest.model.studyResource.StudyResource;

import java.util.List;
import java.util.TreeMap;

/**
 * <h2>Tree Node</h2>
 * <p>Represents the abstract concept of a node of the tree. All leaves are either a {@link LeafNode} or an {@link InnerNode}</p>
 */

public abstract class TreeNode {
        private String name;
        private final NodeType nodeType;
        private final TreeNode parent;

        public TreeNode(String name, NodeType nodeType, TreeNode parent) {
            this.name = name;
            this.nodeType = nodeType;
            this.parent = parent;
        }


        public abstract boolean isLeaf();
        
        public abstract boolean isInner();

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



