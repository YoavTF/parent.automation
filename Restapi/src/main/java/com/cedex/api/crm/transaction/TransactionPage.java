package com.cedex.api.crm.transaction;

import com.google.gson.annotations.SerializedName;

public class TransactionPage {
    private String success;

    @SerializedName("result")
    private Transaction[] transactions;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public Transaction[] getTransactions() {
        return transactions;
    }

    public void setTransactions(Transaction[] transactions) {
        this.transactions = transactions;
    }
}
