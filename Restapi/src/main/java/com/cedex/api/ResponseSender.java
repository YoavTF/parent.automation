package com.cedex.api;

import com.google.gson.JsonObject;

import java.io.Serializable;

public class ResponseSender implements Serializable {
    private static final long serialVersionUID = 1L;

    String responseJson;
    int responseCode;

    public ResponseSender(JsonObject responseJsonObject, int responseCode) {
        this.responseJson = responseJsonObject.toString();
        this.responseCode = responseCode;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseJson() {
        return responseJson;
    }

    public void setResponseJson(String responseJson) {
        this.responseJson = responseJson;
    }
}
