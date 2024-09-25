package com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard;

public class PendingUsersResult {
    Integer userid;
    String firstName, lastName, roleName;

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
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

    public PendingUsersResult(Integer userid, String firstName, String lastName, String roleName) {
        this.userid = userid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.roleName = roleName;
    }
}
