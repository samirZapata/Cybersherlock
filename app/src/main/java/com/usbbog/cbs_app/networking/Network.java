package com.usbbog.cbs_app.networking;

public class Network {

    /*YONIER'S IP C: 192.168.3.158
    * YONIER'S IP H: 192.168.0.8*/
    private String ip = "172.20.10.6";
    final String apiSingUp = "http://"+ip+":9000/api/auth/singup";
    final String apiSinIn = "http://"+ip+":9000/api/auth/singin";
    final String apiUpdatePassword = "http://"+ip+":9000/api/auth/updatePass";

    public String getApiSingUp() {
        return apiSingUp;
    }

    public String getApiSinIn() {
        return apiSinIn;
    }
    public String getApiResetPassword(String email){
        return "http://"+ip+":9000/api/auth/resetPassword/" + email;
    }

    public String getApiOtp(int code){
        return  "http://"+ip+":9000/api/auth/verificarCodigo/" + code;
    }

    public String getApiUpdatePassword(){
        return apiUpdatePassword;
    }


}
