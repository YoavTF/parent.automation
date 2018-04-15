package com.cedex.api.crm.userpage;

public class User {

    private String id;
    private String time_stamp;
    private String status;
    private String password;
    private String email;
    private String first_name;
    private String last_name;
    private String country_iso;
    private String language_iso;
    private String referrer_id;
    private String referred_by;
    private String confirmation_code;
    private String etherium_wallet_address;
    private String requested_tokens_amount;
    private String marketer;
    private String forget_password_id;
    private String update_time_stamp;
    private String kyc_link;
    private Campaigns[] campaigns;
    private String tracking;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime_stamp() {
        return time_stamp;
    }

    public void setTime_stamp(String time_stamp) {
        this.time_stamp = time_stamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getCountry_iso() {
        return country_iso;
    }

    public void setCountry_iso(String country_iso) {
        this.country_iso = country_iso;
    }

    public String getLanguage_iso() {
        return language_iso;
    }

    public void setLanguage_iso(String language_iso) {
        this.language_iso = language_iso;
    }

    public String getReferrer_id() {
        return referrer_id;
    }

    public void setReferrer_id(String referrer_id) {
        this.referrer_id = referrer_id;
    }

    public String getReferred_by() {
        return referred_by;
    }

    public void setReferred_by(String referred_by) {
        this.referred_by = referred_by;
    }

    public String getConfirmation_code() {
        return confirmation_code;
    }

    public void setConfirmation_code(String confirmation_code) {
        this.confirmation_code = confirmation_code;
    }

    public String getEtherium_wallet_address() {
        return etherium_wallet_address;
    }

    public void setEtherium_wallet_address(String etherium_wallet_address) {
        this.etherium_wallet_address = etherium_wallet_address;
    }

    public String getRequested_tokens_amount() {
        return requested_tokens_amount;
    }

    public void setRequested_tokens_amount(String requested_tokens_amount) {
        this.requested_tokens_amount = requested_tokens_amount;
    }

    public String getMarketer() {
        return marketer;
    }

    public void setMarketer(String marketer) {
        this.marketer = marketer;
    }

    public String getForget_password_id() {
        return forget_password_id;
    }

    public void setForget_password_id(String forget_password_id) {
        this.forget_password_id = forget_password_id;
    }

    public String getUpdate_time_stamp() {
        return update_time_stamp;
    }

    public void setUpdate_time_stamp(String update_time_stamp) {
        this.update_time_stamp = update_time_stamp;
    }

    public String getKyc_link() {
        return kyc_link;
    }

    public void setKyc_link(String kyc_link) {
        this.kyc_link = kyc_link;
    }

    public String getTracking() {
        return tracking;
    }

    public void setTracking(String tracking) {
        this.tracking = tracking;
    }

    public Campaigns[] getCampaigns() {
        return campaigns;
    }

    public void setCampaigns(Campaigns[] campaigns) {
        this.campaigns = campaigns;
    }
}
