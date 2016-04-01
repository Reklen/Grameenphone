package com.cc.grameenphone.viewmodels;

/**
 * Created by aditlal on 08/10/15.
 */
public class MultiBillListModel {

    String accountNumber;
    String amount;
    int status;
    String companyName;
    String billNum;
    String bProvider;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getBillNum() {
        return billNum;
    }

    public String getbProvider() {
        return bProvider;
    }

    public void setbProvider(String bProvider) {
        this.bProvider = bProvider;
    }

    public void setBillNum(String billNum) {
        this.billNum = billNum;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
