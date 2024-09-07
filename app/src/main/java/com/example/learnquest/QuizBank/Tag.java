package com.example.learnquest.QuizBank;

import com.example.learnquest.AppState.App;

public class Tag {
    private Short tagID;
    private Short groupID;
    private String tagName;

    @Override
    public String toString() {
        return "Tag{" +
                "groupID=" + groupID +
                ", tagID=" + tagID +
                ", tagName='" + tagName + '\'' +
                '}';
    }

    // Constructor
    public Tag(Short tagID, String tagName) {
        this.tagID = tagID;
        this.groupID = App.groupID;
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
