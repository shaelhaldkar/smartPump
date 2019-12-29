package com.iot.smartpump.mqttrequest;

import android.os.Handler;
import android.util.Log;

import com.iot.smartpump.utils.Constants;
import com.iot.smartpump.utils.MQTTRequestListnerListner;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * Created by abc on 10/14/2018.
 */

public class MqttCallbackHandler implements MqttCallbackExtended {
    String TAG="TAG";
String message="";
Boolean isResponseRecived=false;
    MQTTRequestListnerListner mqttRequestListner=null;
    public MqttCallbackHandler(MQTTRequestListnerListner mqttRequestListner) {
        this.mqttRequestListner=mqttRequestListner;
    }

    @Override
    public void connectComplete(boolean b, String s) {
        Log.d(TAG, "connectComplete Successfully " + s);

    }

    @Override
    public void connectionLost(Throwable throwable) {
        Log.d(TAG, "connectComplete throw " + throwable);
    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        Log.d(TAG, "message Arived"  );
        Log.d(TAG, "String s"+ s  );
        Log.d(TAG, "MqttMessage mqttMessage"+ mqttMessage  );
        mqttRequestListner.onMessageArrived(message,mqttMessage);
        isResponseRecived=true;

//                setMessageNotification(s, new String(mqttMessage.getPayload()));
    }


    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        Log.d(TAG, "connectComplete delivered");
        message="";
        try {
            message=iMqttDeliveryToken.getMessage().toString();
             new Handler().postDelayed(new Runnable() {
                 @Override
                 public void run() {
                     if(!isResponseRecived) {
                         mqttRequestListner.onMessageArrived(message, null);
                     }
                 }
             }, Constants.WAIT_TIME_FOR_RESPONSE);
            Log.d(TAG, "messgae "+message);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

}
