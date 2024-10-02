package com.example.learnquest.model.group;

public class Group {
    //change these to private later when have all necessary attributes

    Integer groupID;
    public String topic;
    public String description;
    public GroupType groupType;

    String groupColour;

    public Group(){
    }

    public Integer getGroupID() {
        return groupID;
    }

    public void setGroupID(Integer groupID) {
        this.groupID = groupID;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public GroupType getGroupType() {
        return groupType;
    }

    public void setGroupType(GroupType groupType) {
        this.groupType = groupType;
    }

    public String getGroupColour() {
        return groupColour;
    }

    public void setGroupColour(String groupColour) {
        this.groupColour = groupColour;
    }
}
