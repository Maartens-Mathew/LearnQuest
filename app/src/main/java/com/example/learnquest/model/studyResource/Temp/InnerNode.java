package com.example.learnquest.model.studyResource.Temp;

import com.example.learnquest.model.studyResource.StudyResource;

import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;


/**
 * <h2>Inner Node</h2>
 * <p>Represents the Inner node of the tree. All leaf trees will contain a {@link TreeMap} of {@link TreeNode}</p>
 */
public class InnerNode extends TreeNode {
    private final TreeMap<String, TreeNode> children;

    public InnerNode(String name, NodeType nodeType, TreeNode parent) {
        super(name, nodeType, parent);
        children = new TreeMap<>();
    }

    public void addChild(String nodeName, TreeNode treeNode) {
        children.put(nodeName, treeNode);
    }

    @Override
    public boolean isLeaf(){
        return false;
    }

    @Override
    public boolean isInner() {
        return true;
    }

    public void removeChild(String nodeName) {
            children.remove(nodeName);
        }

    public List<TreeNode> getChildren() {
        return List.copyOf(children.values()).stream().sorted(Tree.getComparator()).collect(Collectors.toList());
    }

    public TreeNode getChild(String childName) {
        return children.get(childName);
    }
    }

