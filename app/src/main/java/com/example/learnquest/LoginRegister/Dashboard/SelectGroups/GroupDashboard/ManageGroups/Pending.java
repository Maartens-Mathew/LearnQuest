package com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.ManageGroups;

public class Pending {
    Integer userID; Integer groupID;
    String firstName, lastName, roleName;

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getPendingUserFirstName() {
        return firstName;
    }

    public void setPendingUserFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPendingUserLastName() {
        return lastName;
    }

    public void setPendingUserLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Pending(Integer userID, String firstName, String lastName, String roleName) {
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.roleName = roleName;
    }
}
