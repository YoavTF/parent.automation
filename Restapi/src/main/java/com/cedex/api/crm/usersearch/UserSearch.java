package com.cedex.api.crm.usersearch;

public class UserSearch {
    String success;
    Result[] result;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public Result[] getResult() {
        return result;
    }

    public void setResult(Result[] result) {
        this.result = result;
    }
}
