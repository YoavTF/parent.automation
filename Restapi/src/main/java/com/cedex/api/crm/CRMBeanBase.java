package com.cedex.api.crm;

import com.cedex.api.RequestSender;

public abstract class CRMBeanBase extends RequestSender {

    private String relativeUrl;
    private String pageQueryKey = "page";
    private int pageNumber;

    public CRMBeanBase(String baseUrl, String userName, String userPassword, String relativeUrl, String pageQueryKey, int pageNumber,boolean useAuth) {
        super(baseUrl, userName, userPassword, useAuth);
        this.relativeUrl = relativeUrl;
//        this.usersRelativeUrl = usersRelativeUrl;
        this.pageQueryKey = pageQueryKey;
        this.pageNumber = pageNumber;
    }

    public String getRelativeUrl() {
        return relativeUrl;
    }

    public void setRelativeUrl(String relativeUrl) {
        this.relativeUrl = relativeUrl;
    }

    protected String getFullUrl(String param) {
//        String fullUrl = "";
        String baseUrl = super.getRootUrl().endsWith("/") ? super.getRootUrl() : super.getRootUrl() + "/";
        String relativeUrl = getRelativeUrl().endsWith("/") ? this.relativeUrl : this.relativeUrl + "/";
        relativeUrl = relativeUrl.startsWith("/") ? relativeUrl.substring(1, relativeUrl.length()) : relativeUrl;

        return baseUrl + relativeUrl + param;

    }

    public String getPageQueryKey() {
        return pageQueryKey;
    }

    public void setPageQueryKey(String pageQueryKey) {
        this.pageQueryKey = pageQueryKey;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }
}
