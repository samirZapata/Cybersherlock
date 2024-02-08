package com.usbbog.cbs_app.model;

public class Cases {

    private String nombreCaso;
    private String acosador;
    private String telAcosador;
    private String desc;

    public String getNombreCaso() {
        return nombreCaso;
    }

    public void setNombreCaso(String nombreCaso) {
        this.nombreCaso = nombreCaso;
    }

    public String getAcosador() {
        return acosador;
    }

    public void setAcosador(String acosador) {
        this.acosador = acosador;
    }

    public String getTelAcosador() {
        return telAcosador;
    }

    public void setTelAcosador(String telAcosador) {
        this.telAcosador = telAcosador;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


    @Override
    public String toString() {
        return "Cases{" +
                "nombreCaso='" + nombreCaso + '\'' +
                ", acosador='" + acosador + '\'' +
                ", telAcosador='" + telAcosador + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}
