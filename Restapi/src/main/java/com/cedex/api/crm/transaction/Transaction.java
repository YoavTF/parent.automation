package com.cedex.api.crm.transaction;

public class Transaction {
    private int id;
    private String status;
    private String currency;
    private int account_id;
    private String from_address;
    private String to_address;
    private String amount;
    private long timestamp;
    private String accepted_timestamp;
    private String payment_type;
    private String blockchain_hash;
    private String accepted_amount;
    private String purchased_tokens;
    private String bonus_tokens;
    private String normalized_amount_eth;
    private String normalized_amount_usd;
    private String original_transaction_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public String getFrom_address() {
        return from_address;
    }

    public void setFrom_address(String from_address) {
        this.from_address = from_address;
    }

    public String getTo_address() {
        return to_address;
    }

    public void setTo_address(String to_address) {
        this.to_address = to_address;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getAccepted_timestamp() {
        return accepted_timestamp;
    }

    public void setAccepted_timestamp(String accepted_timestamp) {
        this.accepted_timestamp = accepted_timestamp;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public String getBlockchain_hash() {
        return blockchain_hash;
    }

    public void setBlockchain_hash(String blockchain_hash) {
        this.blockchain_hash = blockchain_hash;
    }

    public String getAccepted_amount() {
        return accepted_amount;
    }

    public void setAccepted_amount(String accepted_amount) {
        this.accepted_amount = accepted_amount;
    }

    public String getPurchased_tokens() {
        return purchased_tokens;
    }

    public void setPurchased_tokens(String purchased_tokens) {
        this.purchased_tokens = purchased_tokens;
    }

    public String getBonus_tokens() {
        return bonus_tokens;
    }

    public void setBonus_tokens(String bonus_tokens) {
        this.bonus_tokens = bonus_tokens;
    }

    public String getNormalized_amount_eth() {
        return normalized_amount_eth;
    }

    public void setNormalized_amount_eth(String normalized_amount_eth) {
        this.normalized_amount_eth = normalized_amount_eth;
    }

    public String getNormalized_amount_usd() {
        return normalized_amount_usd;
    }

    public void setNormalized_amount_usd(String normalized_amount_usd) {
        this.normalized_amount_usd = normalized_amount_usd;
    }

    public String getOriginal_transaction_id() {
        return original_transaction_id;
    }

    public void setOriginal_transaction_id(String original_transaction_id) {
        this.original_transaction_id = original_transaction_id;
    }
}
