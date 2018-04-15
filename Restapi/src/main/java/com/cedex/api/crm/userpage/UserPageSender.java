package com.cedex.api.crm.userpage;

import com.cedex.api.ResponseSender;
import com.cedex.api.crm.CRMBeanBase;
import com.cedex.api.crm.usersearch.SearchApiSender.CRMUserStatus;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public class UserPageSender extends CRMBeanBase {


    public UserPageSender(String rootUrl, String userName, String userPassword, String relativeUrl, String pageQueryKey, int pageNumber, boolean useAuth) {
        super(rootUrl, userName, userPassword, relativeUrl, pageQueryKey, pageNumber, useAuth);
    }


    /**
     * sending any search or sort request and converting the received json file to java classes
     *
     * @return
     * @throws Exception
     */
    public User requestUser(int userId) throws Exception {
        String fullUrl = super.getRootUrl() + super.getRelativeUrl() + Integer.toString(userId);
        ResponseSender rs = super.sendWithoutParameters(fullUrl);
//        JsonObject receivedJson = rs != null ? rs.getResponseJsonObject() : null;
//        String jsonAsStr = receivedJson.toString();
        String jsonAsStr = rs != null ? rs.getResponseJson() : null;

        Gson gson = new GsonBuilder().create();
        UserPage userPage = gson.fromJson(jsonAsStr, UserPage.class);
        User receivedUser = userPage.getUser();

        return receivedUser;
    }

    /**
     * changing User's status ..
     *
     * @param userId
     * @param newUserStaus
     * @throws Exception
     */
    public void updateUserStatus(int userId, CRMUserStatus newUserStaus) throws Exception {
        User oldUser = requestUser(userId);
        String fullUrl = super.getRootUrl() + super.getRelativeUrl() + Integer.toString(userId);
        JsonObject parameters = new JsonObject();
        int newStatusNumValue = newUserStaus.getCRMUserStatus();
        parameters.addProperty("status", newStatusNumValue);
        super.sendWithoutParameters(fullUrl, RequestMethod.PUT, parameters.toString());
        User updatedUser = requestUser(userId);
        if (oldUser.getId() == updatedUser.getId()) {
            report.report("Failed to update user's status to: " + newUserStaus);
        } else {
            report.report("User: " + userId + " updated to status: " + newUserStaus + "!!!");
        }
    }
}
