package com.usbbog.cbs_app.networking;


/*
                    __/\\\\\\\\\\\\\____/\\\\____________/\\\\____/\\\\\\\\\_____
                     _\/\\\/////////\\\_\/\\\\\\________/\\\\\\__/\\\///////\\\___
                      _\/\\\_______\/\\\_\/\\\//\\\____/\\\//\\\_\///______\//\\\__
                       _\/\\\\\\\\\\\\\/__\/\\\\///\\\/\\\/_\/\\\___________/\\\/___
                        _\/\\\/////////____\/\\\__\///\\\/___\/\\\________/\\\//_____
                         _\/\\\_____________\/\\\____\///_____\/\\\_____/\\\//________
                          _\/\\\_____________\/\\\_____________\/\\\___/\\\/___________
                           _\/\\\_____________\/\\\_____________\/\\\__/\\\\\\\\\\\\\\\_
                            _\///______________\///______________\///__\///////////////__
*
* */


public class Network {
    private final String apiSingUp = "http://192.168.1.14:9000/api/auth/singup";
    private final String apiSinIn = "http://192.168.1.14:9000/api/auth/singin";
    private final String apiUpdatePassword = "http://192.168.1.14:9000/api/auth/updatePass";
    private final String getUrlForCreateCase = "http://192.168.1.14:9000/";
    //private final String apiGetCasos = "http://192.168.1.4:9000/api/cases/" + mail.getCorreo();

    public String getApiSingUp() {return apiSingUp;}

    public String getApiSinIn() {return apiSinIn;}
    public String getApiResetPassword(String email){
        return "http://192.168.1.14:9000/api/auth/resetPassword/" + email;}

    public String getApiOtp(int code){
        return  "http://192.168.1.14:9000/api/auth/verificarCodigo/" + code;}

    public String getApiUpdatePassword(){
        return apiUpdatePassword;
    }

    public String getGetUrlForCreateCase(){
        return getUrlForCreateCase;
    }
    public String getApiGetCasos(String nombreCaso){
        return "http://192.168.1.14:9000/api/cases/" + nombreCaso;
    }

}
