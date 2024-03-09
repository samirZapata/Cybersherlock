package HelperClasses.HomeAdapter;

import android.widget.ImageView;

public class EvidenciasHelperClass {

    String fecha, nomEvidencia;

    int eImg;

    public EvidenciasHelperClass(String fecha, String nomEvidencia, int eImg) {
        this.fecha = fecha;
        this.nomEvidencia = nomEvidencia;
        this.eImg = eImg;
    }

    public String getFecha() {
        return fecha;
    }

    public String getNomEvidencia() {
        return nomEvidencia;
    }

    public int geteImg() {
        return eImg;
    }
}
