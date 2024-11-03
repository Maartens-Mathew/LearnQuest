package com.example.learnquest.model.studyResource.Temp;

import com.example.learnquest.model.studyResource.StudyResource;

/**
 * <h2>Leaf Node</h2>
 * <p>Represents the Leaf of the tree. All leaf trees will contain a {@link StudyResource}</p>
 */

public class LeafNode extends TreeNode {

    private StudyResource object;

    public LeafNode(String name, NodeType nodeType, StudyResource object, TreeNode parent) {
        super(name, nodeType, parent);
        this.object = object;
    }

    public StudyResource getCargo() {
        return object;
    }

    @Override
    public boolean isLeaf(){
        return true;
    }

    @Override
    public boolean isInner() {
        return false;
    }

    public void setCargo(StudyResource object) {
            this.object = object;
        }
    }