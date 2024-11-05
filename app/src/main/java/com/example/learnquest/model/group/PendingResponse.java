package com.example.learnquest.model.group;

import com.example.learnquest.AppState.App;
import com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.ManageGroups.Pending;

public class PendingResponse {
    Integer userID;
    Integer groupID;
    Integer roleID;

    public Integer getGroupID() {
        return groupID;
    }

    public void setGroupID(Integer groupID) {
        this.groupID = groupID;
    }

    public Integer getRoleID() {
        return roleID;
    }

    public void setRoleID(Integer roleID) {
        this.roleID = roleID;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public PendingResponse() {
    }

    public static PendingResponse accept(Pending pending){
        return new PendingResponse(App.group.getGroupID(), 1,pending.getUserID());
    }

    public PendingResponse(Integer groupID, Integer roleID, Integer userID) {
        this.groupID = groupID;
        this.roleID = roleID;
        this.userID = userID;
    }
}
