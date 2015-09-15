package com.cc.grameenphone.api_models;

/**
 * Created by aditlal on 15/09/15.
 */
public class MSISDNCommandModel {

    private String DEVICEID;

    private String MSISDN;

    private String TYPE;

    public String getDEVICEID ()
    {
        return DEVICEID;
    }

    public void setDEVICEID (String DEVICEID)
    {
        this.DEVICEID = DEVICEID;
    }

    public String getMSISDN ()
    {
        return MSISDN;
    }

    public void setMSISDN (String MSISDN)
    {
        this.MSISDN = MSISDN;
    }

    public String getTYPE ()
    {
        return TYPE;
    }

    public void setTYPE (String TYPE)
    {
        this.TYPE = TYPE;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [DEVICEID = "+DEVICEID+", MSISDN = "+MSISDN+", TYPE = "+TYPE+"]";
    }
}
