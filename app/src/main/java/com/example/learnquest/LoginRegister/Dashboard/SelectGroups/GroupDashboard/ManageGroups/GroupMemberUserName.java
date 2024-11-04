package com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.ManageGroups;

import java.io.Serializable;

public class GroupMemberUserName implements Serializable {

    private Integer userID;

    public GroupMemberUserName(Integer userID, String username) {
        this.userID = userID;
        this.username = username;
    }

    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }
}
