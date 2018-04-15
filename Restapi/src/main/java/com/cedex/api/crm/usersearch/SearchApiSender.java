package com.cedex.api.crm.usersearch;


import com.cedex.api.ResponseSender;
import com.cedex.api.crm.CRMBeanBase;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jsystem.framework.report.Reporter;


public class SearchApiSender extends CRMBeanBase {

    private final String sortQweryParameter = "sort";

    public SearchApiSender(String rootUrl, String userName, String userPassword, String relativeUrl, String pageQueryKey, int pageNumber, boolean useAuth) {
        super(rootUrl, userName, userPassword, relativeUrl, pageQueryKey, pageNumber, useAuth);
    }


//    /**
//     * The init() method will be called by JSystem after the instantiation of
//     * the system object. <br>
//     * This can be a good place to assert that all the members that we need were
//     * defined in the SUT file.
//     */
//    public void init() throws Exception {
//        super.init();
//        Assert.assertNotNull("Please define the SearchResultSender URL in the SUT file", getSearchRelativeUrl());
//
//    }

    public static enum CRMSortBy {
        ID, REGISTRATION_TIME, EMAIL, STATUS, MARKETER;

        public String toString() {
            return name().toLowerCase();
        }
    }

    public static enum CRMSortOrder {
        ASC, DES;

        public String toString() {
            return ordinal() == ASC.ordinal() ? "" : "-";
        }
    }

    public static enum CRMSearchUserBy {
        ID, LAST_NAME, FIRST_NAME, EMAIL, STATUS, REFERRED_BY,
        //TODO: COUNTRY NOT WORKING YET
        COUNTRY_ISO;

        public String toString() {
            return name().toLowerCase();
        }
    }

    public static enum CRMUserStatus {
        NOT_CONFIRMED("1"), CONFIRMED("10"), ETH_WALlET_CONFIRMED("20"),
        CONTRIBUTER("30"), KYC_REQUIRED("40"), CRYPTO_WALLET_REQUIRED("45"), VERIFIED_ACCOUNT("50"),
        FUND_RETURNED("60"), BLACK_LIST("70"), NO_RESPONSE_NO_WALLET("80");


        private CRMUserStatus(String cRMUserStatus) {
            this.cRMUserStatus = cRMUserStatus;
        }

        private String cRMUserStatus;

        /**
         * @return the CRMUserStatus
         */
        public int getCRMUserStatus() {
            return Integer.parseInt(cRMUserStatus);
        }

    }


    /**
     * press on previous page whet users found and sorted
     *
     * @param searchCriteria
     * @param searchCriteriaValue
     * @param sortByCriteria
     * @param sortOrderCriteria
     * @return
     * @throws Exception
     */
    public Result[] requestSortPreviousPage(CRMSearchUserBy searchCriteria, String searchCriteriaValue, CRMSortBy sortByCriteria, CRMSortOrder sortOrderCriteria) throws Exception {
        super.setPageNumber(super.getPageNumber() - 1);
        return requestSort(searchCriteria, searchCriteriaValue, sortByCriteria, sortOrderCriteria);
    }

    /**
     * press on next page whet users found and sorted
     *
     * @param searchCriteria
     * @param searchCriteriaValue
     * @param sortByCriteria
     * @param sortOrderCriteria
     * @return
     * @throws Exception
     */
    public Result[] requestSortNextPage(CRMSearchUserBy searchCriteria, String searchCriteriaValue, CRMSortBy sortByCriteria, CRMSortOrder sortOrderCriteria) throws Exception {
        super.setPageNumber(super.getPageNumber() + 1);
        return requestSort(searchCriteria, searchCriteriaValue, sortByCriteria, sortOrderCriteria);
    }

    /**
     * Sending user search request and sorting received users by any criteria
     *
     * @param searchCriteria
     * @param searchCriteriaValue
     * @param sortByCriteria
     * @param sortOrderCriteria
     * @return
     * @throws Exception
     */
    public Result[] requestSort(CRMSearchUserBy searchCriteria, String searchCriteriaValue, CRMSortBy sortByCriteria, CRMSortOrder sortOrderCriteria) throws Exception {
        String sortBy = sortOrderCriteria.toString() + sortByCriteria.toString();
        Result[] receivedResultArray = requestSearch(getRelativeUrl(), searchCriteria.toString(), searchCriteriaValue, sortQweryParameter, sortBy, getPageQueryKey(), getPageNumber() + "");
        return receivedResultArray;
    }

    /**
     * Sending user search request
     *
     * @param searchCriteria
     * @param searchCriteriaValue
     * @return list of replied users as Array of Results
     * @throws Exception
     */
    public Result[] requestSearch(CRMSearchUserBy searchCriteria, String searchCriteriaValue) throws Exception {
        Result[] receivedResultArray = requestSearch(getRelativeUrl(), searchCriteria.toString(), searchCriteriaValue, getPageQueryKey(), getPageNumber() + "");
        return receivedResultArray;
    }

    /**
     * sending any search or sort request and converting the received json file to java classes
     *
     * @param relativeUrl
     * @param reqParams
     * @return
     * @throws Exception
     */
    public Result[] requestSearch(String relativeUrl, String... reqParams) throws Exception {
        ResponseSender rs = super.sendWithParameters(relativeUrl, reqParams);
        String jsonAsStr = rs != null ? rs.getResponseJson() : null;

        Gson gson = new GsonBuilder().create();
        UserSearch userSearch = gson.fromJson(jsonAsStr, UserSearch.class);
        Result[] receivedResult = userSearch.getResult();
        if (receivedResult.length == 50) {
            report.report("There are more user pages (to get more use pageNumber parameter)", Reporter.WARNING);

        }
        return userSearch.getResult();
    }

}
