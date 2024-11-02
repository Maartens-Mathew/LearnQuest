package com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard;

import java.io.Serializable;

public class GroupMemberUserName implements Serializable {

    private Integer userid;

    public GroupMemberUserName(Integer userid, String username) {
        this.userid = userid;
        this.username = username;
    }

    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }
}
