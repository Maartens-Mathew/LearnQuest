package com.example.learnquest.QuizBank;

import com.example.learnquest.AppState.App;

public class Tag {
    private Integer tagID;
    private Integer groupID;
    private String tagName;
    private String tagColour;

    public String getTagColour() {
        return tagColour;
    }

    public void setTagColour(String tagColour) {
        this.tagColour = tagColour;
    }

    @Override
    public String toString() {
        return tagName;
    }

    // Constructor
    public Tag(Integer tagID, String tagName) {
        this.tagID = tagID;
        this.groupID = App.groupID;
        this.tagName = tagName;
    }

    public void setGroupID(Integer groupID) {
        this.groupID = groupID;
    }

    public void setTagID(Integer tagID) {
        this.tagID = tagID;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public Tag(){

    }

    // Getters
    public Integer getTagID() {
        return tagID;
    }

    public int getGroupID() {
        return groupID;
    }

    public String getTagName() {
        return tagName;
    }
}
