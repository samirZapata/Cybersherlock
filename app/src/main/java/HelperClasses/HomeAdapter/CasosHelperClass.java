package HelperClasses.HomeAdapter;

public class CasosHelperClass {

    String idCasos, desCasos;
    int btnId;
private String nombreCasos,acosador,telAcosador,desc,Evidencias;
    public CasosHelperClass(String idCasos, String desCasos, int btnId){
        this.idCasos = idCasos;
        this.desCasos = desCasos;
        this.btnId = btnId;
    }

    public CasosHelperClass(String nombreCaso, String desc) {
        this.nombreCasos = nombreCaso;
        this.desc = desc;
    }

    public String getIdCasos() {return idCasos;}

    public String getDesCasos() {
        return desCasos;
    }

    public int getBtnId() {
        return btnId;
    }

    public String getNombreCasos() {
        return nombreCasos;
    }

    public void setNombreCasos(String nombreCasos) {
        this.nombreCasos = nombreCasos;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}

