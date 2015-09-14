package com.cc.grameenphone.viewmodels;

import com.cc.grameenphone.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rajkiran on 9/10/2015.
 */
public class CancelAssociationItem {
    private String accountText;
    private String accNumbText;
    private String compText;
    private String descText;
    private int cancelBtn;

    public CancelAssociationItem() {
        super();
    }

    public CancelAssociationItem(String accNumbText, String accountText, String compText, String descText, int cancelBtn) {
        this.accNumbText = accNumbText;
        this.accountText = accountText;
        this.compText = compText;
        this.descText = descText;
        this.cancelBtn = cancelBtn;
    }

    public String getAccountText() {
        return accountText;
    }

    public void setAccountText(String accountText) {
        this.accountText = accountText;
    }

    public String getAccNumbText() {
        return accNumbText;
    }

    public void setAccNumbText(String accNumbText) {
        this.accNumbText = accNumbText;
    }

    public String getCompText() {
        return compText;
    }

    public void setCompText(String compText) {
        this.compText = compText;
    }

    public String getDescText() {
        return descText;
    }

    public void setDescText(String descText) {
        this.descText = descText;
    }

    public int getCancelBtn() {
        return cancelBtn;
    }

    public void setCancelBtn(int cancelBtn) {
        this.cancelBtn = cancelBtn;
    }
    public static class ItemList {
        public static List<CancelAssociationItem> getItem() {
            List<CancelAssociationItem> list=new ArrayList<CancelAssociationItem>();
            list.add(new CancelAssociationItem("ACC. No:",
                    "123432","Company:","DESCO",R.id.cancel_btn));
            list.add(new CancelAssociationItem("ACC. No:",
                    "123433","Company:","TITAS",R.id.cancel_btn));
            list.add(new CancelAssociationItem("ACC. No:",
                    "123422","Company:","KGCL",R.id.cancel_btn));
            list.add(new CancelAssociationItem("ACC. No:",
                    "123132","Company:","DESCO",R.id.cancel_btn));
            list.add(new CancelAssociationItem("ACC. No:",
                    "123452","Company:","KGCL",R.id.cancel_btn));
            list.add(new CancelAssociationItem("ACC. No:",
                    "123467","Company:","TITAS",R.id.cancel_btn));

            return list;
        }

    }
}
