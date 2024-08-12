package com.example.learnquest.GroupStuff;

import androidx.annotation.Nullable;

public class Group {
    //change these to private later when have all necessary attributes
    public String topic;
    public String description;
    public String groupType;

    public Group(){
    }

    public Group(String topic, String description, String groupType) {
        this.topic = topic;
        this.description = description;
        this.groupType = groupType;
    }
}
