package com.usbbog.cbs_app.networking;

public class Network {

    /*YONIER'S IP C: 192.168.3.158
    * YONIER'S IP H: 192.168.0.8*/
    final String apiSingUp = "http://172.20.10.6:9000/api/auth/singup";
    final String apiSinIn = "http://172.20.10.6:9000/api/auth/singin";

    public String getApiSingUp() {
        return apiSingUp;
    }

    public String getApiSinIn() {
        return apiSinIn;
    }
}
