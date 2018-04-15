package com.cedex.api.crm.transaction;

import com.cedex.api.ResponseSender;
import com.cedex.api.crm.CRMBeanBase;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class TransactionPerUserPageSender extends CRMBeanBase {

    public TransactionPerUserPageSender(String baseUrl, String userName, String userPassword, String relativeUrl, String pageQueryKey, int pageNumber,boolean useAuth) {
        super(baseUrl, userName, userPassword, relativeUrl, pageQueryKey, pageNumber,useAuth);
    }

    /**
     * get all transactions for specific user
     *
     * @return
     * @throws Exception
     */
    public Transaction[] requestTransactionsPerUser(int userId) throws Exception {

        ResponseSender rs = super.sendWithParameters(getRelativeUrl(),"account_id",userId+"");
        String jsonAsStr = rs != null ? rs.getResponseJson() : null;
        Gson gson = new GsonBuilder().create();
        TransactionPage transactionPage = gson.fromJson(jsonAsStr, TransactionPage.class);
        Transaction[] receivedTransactionList = transactionPage.getTransactions();

        return receivedTransactionList;
    }
    /**
     * get all trasactions for all users
     *

     * @return
     * @throws Exception
     */
    public Transaction[] requestTransactionsForAllUsers() throws Exception {

        ResponseSender rs = super.sendWithParameters(getRelativeUrl(),super.getPageQueryKey(),super.getPageNumber()+"");
//        JsonObject receivedJson = rs != null ? rs.getResponseJsonObject() : null;
//        String jsonAsStr = receivedJson.toString();
        String jsonAsStr = rs != null ? rs.getResponseJson() : null;
        Gson gson = new GsonBuilder().create();
        TransactionPage transactionPage = gson.fromJson(jsonAsStr, TransactionPage.class);
        Transaction[] receivedTransactionList = transactionPage.getTransactions();

        return receivedTransactionList;
    }
    /**
     * get all trasactions for all users
     *

     * @return
     * @throws Exception
     */
    public Transaction requestTransaction(int transactionId) throws Exception {
        Transaction foundTransaction=null;
        ResponseSender rs = super.sendWithParameters(getRelativeUrl(),"id",transactionId+"");
//        JsonObject receivedJson = rs != null ? rs.getResponseJsonObject() : null;
//        String jsonAsStr = receivedJson.toString();
        String jsonAsStr = rs != null ? rs.getResponseJson() : null;

        Gson gson = new GsonBuilder().create();
        TransactionPage transactionPage = gson.fromJson(jsonAsStr, TransactionPage.class);
        Transaction[] receivedTransactionList = transactionPage.getTransactions();
        if (receivedTransactionList.length> 0)
            foundTransaction=receivedTransactionList[0];

        return foundTransaction;
    }
}
