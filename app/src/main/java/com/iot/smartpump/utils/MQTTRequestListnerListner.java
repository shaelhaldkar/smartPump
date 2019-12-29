package com.iot.smartpump.utils;

import org.eclipse.paho.client.mqttv3.MqttMessage;

public interface MQTTRequestListnerListner {
    public void onMessageArrived(String s, MqttMessage mqttMessage);
}
