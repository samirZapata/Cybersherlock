package com.usbbog.cbs_app.modelHelper;


public class CasosHolder {

    private String nombreCaso;
    private String acosador;
    private String telAcosador;
    private String desc;
    private String Evidencias;


    public CasosHolder(String nombreCaso, String acosador, String telAcosador, String desc, String evidencias) {
        this.nombreCaso = nombreCaso;
        this.acosador = acosador;
        this.telAcosador = telAcosador;
        this.desc = desc;
        Evidencias = evidencias;
    }


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

    public String getEvidencias() {
        return Evidencias;
    }

    public void setEvidencias(String evidencias) {
        Evidencias = evidencias;
    }

    @Override
    public String toString() {
        return "CasosHolder{" +
                "nombreCaso='" + nombreCaso + '\'' +
                ", acosador='" + acosador + '\'' +
                ", telAcosador='" + telAcosador + '\'' +
                ", desc='" + desc + '\'' +
                ", Evidencias='" + Evidencias + '\'' +
                '}';
    }

}
