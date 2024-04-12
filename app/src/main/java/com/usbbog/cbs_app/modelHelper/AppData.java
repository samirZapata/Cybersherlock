package com.usbbog.cbs_app.modelHelper;

import android.net.Uri;


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


public class AppData {
    private static String correo;
    private static String nombreCaso;
    private static String fecha;
    private static String personaje;
    private static String whatsapp;
    private static String descrip;
    private static String fileUri;

    private static String nombre;

    public static String getCorreo(){
        return correo;
    }

    public static void setCorreo(String value){
        correo = value;
    }

    public static String getNombreCaso(){ return nombreCaso; }

    public static void setNombreCaso(String value ){ nombreCaso = value; }

    public static String getFecha() {return fecha;}

    public static void setFecha(String fecha) {AppData.fecha = fecha;}

    public static String getPersonaje() {return personaje;}

    public static void setPersonaje(String personaje) {AppData.personaje = personaje;}

    public static String getWhatsapp() {return whatsapp;}

    public static void setWhatsapp(String whatsapp) {
        AppData.whatsapp = whatsapp;
    }

    public static String getDescrip() {
        return descrip;
    }

    public static void setDescrip(String descrip) {
        AppData.descrip = descrip;
    }

    public static void setFileUri(Uri uri) {
    }

    public static String getFileUri() {
        return fileUri;
    }

    public static String getNombre() {
        return nombre;
    }

    public static void setNombre(String nombre) {
        AppData.nombre = nombre;
    }
}
