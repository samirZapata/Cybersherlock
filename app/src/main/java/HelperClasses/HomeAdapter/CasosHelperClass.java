package HelperClasses.HomeAdapter;

public class CasosHelperClass {

    String idCasos, desCasos;
    int btnId;

    public CasosHelperClass(String idCasos, String desCasos, int btnId){
        this.idCasos = idCasos;
        this.desCasos = desCasos;
        this.btnId = btnId;
    }

    public String getIdCasos() {
        return idCasos;
    }

    public String getDesCasos() {
        return desCasos;
    }

    public int getBtnId() {
        return btnId;
    }
}
