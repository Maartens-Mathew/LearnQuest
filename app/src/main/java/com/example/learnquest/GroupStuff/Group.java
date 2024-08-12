package com.example.learnquest.GroupStuff;

public class Group {
    //change these to private later when have all necessary attributes
    public String topic;
    public String description;
    public GroupTypes groupType;

    public Group(){
    }

    public Group(String topic, String description, GroupTypes groupType) {
        this.topic = topic;
        this.description = description;
        this.groupType = groupType;
    }
}
