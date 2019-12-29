package com.iot.smartpump.utils;

/**
 * Created by abc on 7/9/2018.
 */

public interface Constants {
    String URL_BASE = "http://209.58.164.151:60/Service1.svc/";
    //    String URL_BASE="http://192.99.45.76:81/Service1.svc/";
    String WEB_ACTION_OTP_GENERATE = "SendOTPMobile/8dUEbbmtZUVfwHmZSa0doBQQYpaRN2WC7gKIllDbJbY=/";
    String WEB_ACTION_VERIFY_OTP = "VerifyRequestOTP/8dUEbbmtZUVfwHmZSa0doBQQYpaRN2WC7gKIllDbJbY=/";
    String WEB_ACTION_UPDATE_USER = "updateUser/";
    String WEB_ACTION_INSET_USER = "/insertUser/8dUEbbmtZUVfwHmZSa0doBQQYpaRN2WC7gKIllDbJbY=";
    String WEB_ACTION_FORGOT_PWD = "forgotPassword/69DHI5vxBxmF2fc7L2s5jZmZF2xSpHusPpZjgazUgrY=/";
    String WEB_ACTION_ADD_REVIEW = "insertReview/8dUEbbmtZUVfwHmZSa0doBQQYpaRN2WC7gKIllDbJbY=";
    String WEB_ACTION_ADD_RATTING = "insertRating/8dUEbbmtZUVfwHmZSa0doBQQYpaRN2WC7gKIllDbJbY=";
    String WEB_ACTION_GET_DEVICE_LIST = "GetDeviceListByUserId/";
    String WEB_ACTION_DELETE_DEVICE = "removeDevice/";
    String WEB_ACTION_UPDATE_DEVICE_NAME = "updateDeviceName/";
    String WEB_ACTION_INSERT_DEVICE = "insertDevice/";
    String WEB_ACTION_GET_USER_DETAILS = "getUserDetailsByUserId/";
    String WEB_ACTION_UPDATE_IMAGE = "updateImageByDno/";
    long WAIT_TIME_FOR_RESPONSE=1000*5;

    //    String WEB_ACTION_TEMP_POST_DEVICE_SETTING="http://192.99.45.76:80/Service1.svc/Settings";//"http://192.99.45.76:80/Service1.svc/set?val=1";//http://192.99.45.76:80/Service1.svc/Settings";
    String BASE_URL_TEMP = "http://192.168.1.1/";//"http://192.99.45.76:80/Service1.svc/set?val=1";//http://192.99.45.76:80/Service1.svc/Settings";
    String WEB_ACTION_TEMP_POST_DEVICE_SETTING = "settings";//"http://192.99.45.76:80/Service1.svc/set?val=1";//http://192.99.45.76:80/Service1.svc/Settings";
    String WEB_ACTION_TEMP_SET_ON_OFF = "set?val=";//1 or 2//http://192.99.45.76:80/Service1.svc/set?val=1";
    String WEB_ACTION_TEMP_PP = "pp?data=";//1 or 2//http://192.99.45.76:80/Service1.svc/set?val=1";
    String WEB_ACTION_TEMP_GC = "clone?data=";//1 or 2//http://192.99.45.76:80/Service1.svc/set?val=1";
    String WEB_ACTION_TEMP_SPEED = "speed?data=";


    //MQTT
//    String MQTT_BROKER_URL = "ws://iot.eclipse.org:80/ws";
    String MQTT_BROKER_URL = "tcp://broker.mqtt-dashboard.com";
//    String MQTT_BROKER_URL = "test.mosquitto.org:1883";

    String PUBLISH_TOPIC = "/SNS/pump_";

    String CLIENT_ID = "5c4b6741-9d04-4de9-9836-e29b06212976";

    String KEY_METHOD = "Method";
    String MQTT_METHOD_STATUS = "state";


}
