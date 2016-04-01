package com.cc.grameenphone.viewmodels;

/**
 * Created by rahul on 11/09/15.
 */
public class PaidBillsItems {
    private String accountNumberString;
    private String amountString;
    private String accountNumber;
    private String amount;
    private String inr;
    private boolean iconVal;

    public PaidBillsItems(String accountNumber, String amount, boolean iconVal) {
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.iconVal = iconVal;
    }

    public String getAccountnumber() {
        return accountNumber;
    }

    public void setAccountnumber(String accountnumber) {
        this.accountNumber = accountnumber;
    }

    public String getAccountnumberString() {
        return accountNumberString;
    }

    public void setAccountnumberString(String accountnumberString) {
        this.accountNumberString = accountnumberString;
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
