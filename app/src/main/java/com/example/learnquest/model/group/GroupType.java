package com.example.learnquest.model.group;

public class GroupType {

    Integer typeID;
    String groupType;

    public GroupType(Integer typeID, String groupType) {
        this.typeID = typeID;
        this.groupType = groupType;
    }

    public GroupType(){

    }

    public Integer getTypeID() {
        return typeID;
    }

    public void setTypeID(Integer typeID) {
        this.typeID = typeID;
    }

    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }
}
