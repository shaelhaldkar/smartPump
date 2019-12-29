package com.iot.smartpump.utils;

import org.eclipse.paho.client.mqttv3.IMqttToken;

public interface MQTTSubscribeListner {
    public void onSubscribe(Boolean isScuess, IMqttToken iMqttToken);
}
