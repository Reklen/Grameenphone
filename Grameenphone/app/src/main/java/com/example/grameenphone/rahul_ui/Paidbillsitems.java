package com.example.grameenphone.rahul_ui;

/**
 * Created by rahul on 11/09/15.
 */
public class Paidbillsitems {
    private String accountnumberString;
    private String amountString;
    private String accountnumber;
    private String amount;
    private String inr;
    private boolean iconVal;

    public Paidbillsitems(String accountnumber, String amount, boolean iconVal) {
        this.accountnumber = accountnumber;
        this.amount = amount;
        this.iconVal = iconVal;
    }

    public String getAccountnumber() {
        return accountnumber;
    }

    public void setAccountnumber(String accountnumber) {
        this.accountnumber = accountnumber;
    }

    public String getAccountnumberString() {
        return accountnumberString;
    }

    public void setAccountnumberString(String accountnumberString) {
        this.accountnumberString = accountnumberString;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAmountString() {
        return amountString;
    }

    public void setAmountString(String amountString) {
        this.amountString = amountString;
    }

    public boolean isIconVal() {
        return iconVal;
    }

    public void setIconVal(boolean iconVal) {
        this.iconVal = iconVal;
    }

    public String getInr() {
        return inr;
    }

    public void setInr(String inr) {
        this.inr = inr;
    }
}
