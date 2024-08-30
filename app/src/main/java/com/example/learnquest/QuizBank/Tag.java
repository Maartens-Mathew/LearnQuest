package com.example.learnquest.QuizBank;

public class Tag {
    private int tagID;
    private int groupID;
    private String tagName;

    // Constructor
    public Tag(int tagID, int groupID, String tagName) {
        this.tagID = tagID;
        this.groupID = groupID;
        this.tagName = tagName;
    }

    // Getters
    public int getTagID() {
        return tagID;
    }

    public int getGroupID() {
        return groupID;
    }

    public String getTagName() {
        return tagName;
    }
}
