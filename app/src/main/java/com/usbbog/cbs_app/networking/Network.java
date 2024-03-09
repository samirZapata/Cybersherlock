package com.usbbog.cbs_app.networking;

public class Network {

    /*YONIER'S IP C: 192.168.3.158
    * YONIER'S IP H: 192.168.0.8*/
    final String apiSingUp = "http://192.168.20.82:9000/api/auth/singup";
    final String apiSinIn = "http://192.168.20.82:9000/api/auth/singin";
    final String apiUpdatePassword = "http://192.168.20.82:9000/api/auth/updatePass";

    final String apiNuevoCaso = "http://192.168.20.82:9000/api/auth/";

    public String getApiSingUp() {

        return apiSingUp;
    }

    public String getApiSinIn() {

        return apiSinIn;
    }
    public String getApiResetPassword(String email){
        return "http://192.168.20.82:9000/api/auth/resetPassword/" + email;
    }

    public String getApiOtp(int code){
        return  "http://192.168.20.82:9000/api/auth/verificarCodigo/" + code;
    }

    public String getApiUpdatePassword(){

        return apiUpdatePassword;
    }

    public String getUrlForCreateCase(){
        return apiNuevoCaso;
    }


}
