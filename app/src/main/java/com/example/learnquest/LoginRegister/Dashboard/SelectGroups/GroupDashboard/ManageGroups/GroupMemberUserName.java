package com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.ManageGroups;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import retrofit2.http.Field;

public class GroupMemberUserName implements Serializable {

    @SerializedName("userid")
    private Integer userid;

    public GroupMemberUserName(Integer userid, String username) {
        this.userid = userid;
        this.username = username;
    }

    @SerializedName("username")
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
