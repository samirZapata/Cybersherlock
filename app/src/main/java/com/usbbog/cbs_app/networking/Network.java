package com.usbbog.cbs_app.networking;

public class Network {
    private final String apiSingUp = "http://192.168.1.4:9000/api/auth/singup";
    private final String apiSinIn = "http://192.168.1.4:9000/api/auth/singin";
    private final String apiUpdatePassword = "http://192.168.1.4:9000/api/auth/updatePass";
    private final String getUrlForCreateCase = "http://192.168.1.4:9000/api/cases/";
    //private final String apiGetCasos = "http://192.168.1.4:9000/api/cases/" + mail.getCorreo();

    public String getApiSingUp() {return apiSingUp;}

    public String getApiSinIn() {return apiSinIn;}
    public String getApiResetPassword(String email){
        return "http://192.168.1.4:9000/api/auth/resetPassword/" + email;}

    public String getApiOtp(int code){
        return  "http://192.168.1.4:9000/api/auth/verificarCodigo/" + code;}

    public String getApiUpdatePassword(){
        return apiUpdatePassword;
    }

    public String getGetUrlForCreateCase(){
        return getUrlForCreateCase;
    }
    public String getApiGetCasos(String correo){
        return "http://192.168.1.4:9000/api/cases/" + correo;
    }
}
