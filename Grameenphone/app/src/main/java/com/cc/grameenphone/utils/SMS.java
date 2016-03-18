package com.cc.grameenphone.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import com.cc.grameenphone.interfaces.OnSMSInterface;

/**
 * Created by aditlal on 06/01/16.
 */
public class SMS extends BroadcastReceiver {

    OnSMSInterface onSMSInterface;

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        //Logger.d("SMS", intent.getAction());

        if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
            Bundle bundle = intent.getExtras();           //---get the SMS message passed in---
            SmsMessage[] msgs = null;
            String msg_from;
            if (bundle != null) {
                //---retrieve the SMS message received---
                try {
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    msgs = new SmsMessage[pdus.length];
                    for (int i = 0; i < msgs.length; i++) {
                        msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                        msg_from = msgs[i].getOriginatingAddress();
                        String msgBody = msgs[i].getMessageBody();
                        //Logger.d("SMS", msgBody);
                        if (msgBody.contains("MobiCash"))
                            if (onSMSInterface != null)
                                onSMSInterface.onSMS(msgBody);
                    }
                } catch (Exception e) {
//                            Log.d("Exception caught",e.getMessage());
                }
            }
        }
    }

    public void setSMSListener(OnSMSInterface onSMSInterface) {
        this.onSMSInterface = onSMSInterface;
    }
}
