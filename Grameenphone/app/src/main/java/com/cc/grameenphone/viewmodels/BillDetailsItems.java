package com.cc.grameenphone.viewmodels;

/**
 * Created by rahul on 09/09/15.
 */
public class BillDetailsItems {

    private final String accountNumber_String = "Acc. No: ";
    private final String billNumber_String = "Bill No: ";
    private final String company_String= "Company";
    private final String dueDate_String = "Due date : ";
    private final String inr= "INR ";
    private int accountNumber;
    private int billNumber;
    private String company;
    private String dueDate;
    private int value;
    private boolean selected=false;

    public BillDetailsItems(int accountNumber, int billNumber, String company, String dueDate, boolean selected, int value) {
        this.accountNumber = accountNumber;
        this.billNumber = billNumber;
        this.company = company;
        this.dueDate = dueDate;
        this.selected = selected;
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getInr() {
        return inr;
    }


    public String getBillNumber_String() {
        return billNumber_String;
    }


    public String getAccountNumber_String() {
        return accountNumber_String;
    }


    public String getCompany_String() {
        return company_String;
    }


    public String getDueDate_String() {
        return dueDate_String;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public int getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(int billNumber) {
        this.billNumber = billNumber;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isSelected() {

        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
