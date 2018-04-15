package com.cedex.api.crm;

import com.cedex.api.RequestSender;
import com.cedex.api.crm.transaction.TransactionPerUserPageSender;
import com.cedex.api.crm.userpage.UserPageSender;
import com.cedex.api.crm.usersearch.SearchApiSender;
import jsystem.framework.system.SystemObjectImpl;
import org.junit.Assert;

public class CRMApi extends SystemObjectImpl
{
    private String rootUrl;
    private String searchRelativeUrl = "/accounts";
    private String usersRelativeUrl="/accounts";
    private String anyRelativeUrl;
    private String transactionRelativeUrl;
    private boolean useAuth;
    private String pageQueryKey = "page";
    private int pageNumber=0;
    private SearchApiSender searchApiSender;
    private UserPageSender userPageSender;
    private TransactionPerUserPageSender transactionPerUserPageSender;
    /**
     * use it for send any temp reqiest
     */
    private RequestSender requestSender;
    private String userName;
    private String userPassword;

    /**
     * The init() method will be called by JSystem after the instantiation of
     * the system object. <br>
     * This can be a good place to assert that all the members that we need were
     * defined in the SUT file.
     */
    public void init() throws Exception {
        super.init();
        Assert.assertNotNull("Please define the CRMBase search relative URL in the SUT file", searchRelativeUrl);
        Assert.assertNotNull("Please define the CRMBase users relative URL in the SUT file", usersRelativeUrl);
        Assert.assertNotNull("Please define the CRMBase transactions relative URL in the SUT file", transactionRelativeUrl);
        searchApiSender=new SearchApiSender(rootUrl,userName,userPassword,searchRelativeUrl,pageQueryKey,pageNumber,useAuth);
        userPageSender=new UserPageSender(rootUrl,userName,userPassword,usersRelativeUrl,pageQueryKey,pageNumber,useAuth);
        transactionPerUserPageSender =new TransactionPerUserPageSender(rootUrl,userName,userPassword,transactionRelativeUrl,pageQueryKey,pageNumber,useAuth);
        requestSender=new RequestSender(rootUrl,useAuth);
    }


    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRootUrl() {
        return rootUrl;
    }

    public void setRootUrl(String rootUrl) {
        this.rootUrl = rootUrl;
    }

    public String getUsersRelativeUrl() {
        return usersRelativeUrl;
    }

    public void setUsersRelativeUrl(String usersRelativeUrl) {
        this.usersRelativeUrl = usersRelativeUrl;
    }

    public String getSearchRelativeUrl() {
        return searchRelativeUrl;
    }

    public void setSearchRelativeUrl(String searchRelativeUrl) {
        this.searchRelativeUrl = searchRelativeUrl;
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

    public void setPageNumber(int pageNumber) throws Exception {
        if (pageNumber<0) {
            throw new Exception("Page number cant be less than 0");
        }
        this.pageNumber = pageNumber;
    }

    public SearchApiSender getSearchApiSender() {
        return searchApiSender;
    }

    public void setSearchApiSender(SearchApiSender searchApiSender) {
        this.searchApiSender = searchApiSender;
    }

    public UserPageSender getUserPageSender() {
        return userPageSender;
    }

    public void setUserPageSender(UserPageSender userPageSender) {
        this.userPageSender = userPageSender;
    }

    public String getTransactionRelativeUrl() {
        return transactionRelativeUrl;
    }

    public void setTransactionRelativeUrl(String transactionRelativeUrl) {
        this.transactionRelativeUrl = transactionRelativeUrl;
    }

    public TransactionPerUserPageSender getTransactionPerUserPageSender() {
        return transactionPerUserPageSender;
    }

    public void setTransactionPerUserPageSender(TransactionPerUserPageSender transactionPerUserPageSender) {
        this.transactionPerUserPageSender = transactionPerUserPageSender;
    }

    public RequestSender getRequestSender() {
        return requestSender;
    }

    public void setRequestSender(RequestSender requestSender) {
        this.requestSender = requestSender;
    }

    public boolean isUseAuth() {
        return useAuth;
    }

    public void setUseAuth(boolean useAuth) {
        this.useAuth = useAuth;
    }

    public String getAnyRelativeUrl() {
        return anyRelativeUrl;
    }

    public void setAnyRelativeUrl(String anyRelativeUrl) {
        this.anyRelativeUrl = anyRelativeUrl;
    }


}
