package com.example.learnquest.model.group;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;

import com.example.learnquest.AppState.App;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

public class Group implements Serializable {

    Integer groupID;
    String topic;
    String description;
    Integer typeID;
    String groupType;
    String groupColour;

    static Bitmap peerToPeer;
    static Bitmap studentTeacher;

    public Integer getTypeID() {
        return typeID;
    }

    public void setTypeID(Integer typeID) {
        this.typeID = typeID;
    }

    public Integer getGroupID() {
        return groupID;
    }

    public Group() {
        loadImages();
    }

    public static void loadImages(){
        AssetManager manager = App.applicationContext.getAssets();


        try {
            InputStream P2P_is = manager.open("Group/PeerToPeer.png");
            InputStream ST_is = manager.open("Group/Teacher.png");

            peerToPeer = BitmapFactory.decodeStream(P2P_is);
            studentTeacher = BitmapFactory.decodeStream(ST_is);
        }catch(IOException e){
            e.printStackTrace();
        }


    }

    public Bitmap getImage(){
        if (typeID == 2)
            return peerToPeer;

        if (typeID == 3)
            return studentTeacher;

        else
            return null;
    }




    public Group(Integer groupID, String topic, String description, String groupType, String groupColour) {
        this();
        this.groupID = groupID;
        this.topic = topic;
        this.description = description;
        this.groupType = groupType;
        this.groupColour = groupColour;
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

    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    public String getGroupColour() {
        return groupColour;
    }

    public void setGroupColour(String groupColour) {
        this.groupColour = groupColour;
    }
}
