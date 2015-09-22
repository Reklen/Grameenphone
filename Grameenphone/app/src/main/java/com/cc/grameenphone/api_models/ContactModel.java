package com.cc.grameenphone.api_models;


import com.orm.SugarRecord;

/**
 * Created by aditlal on 20/09/15.
 */
public class ContactModel extends SugarRecord<ContactModel> {

    String name;
    String number;

    String photoId;

    public ContactModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPhotoId() {
        return photoId;
    }

    public void setPhotoId(String photoId) {
        this.photoId = photoId;
    }


}
