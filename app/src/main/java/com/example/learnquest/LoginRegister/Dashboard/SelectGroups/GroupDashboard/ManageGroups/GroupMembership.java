package com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.ManageGroups;

public class GroupMembership {
    private int userID;
    private int groupID;
    private int roleID;

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    public GroupMembership(int userID, int groupID, int roleID) {
        this.userID = userID;
        this.groupID = groupID;
        this.roleID = roleID;
    }
}
