package com.cedex.api.crm.userpage;

import com.google.gson.annotations.SerializedName;

public class UserPage {
    private String success;

    @SerializedName("result")
    private User user;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
