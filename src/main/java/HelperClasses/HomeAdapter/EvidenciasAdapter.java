package HelperClasses.HomeAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.usbbog.cbs_app.R;

import java.util.ArrayList;

public class EvidenciasAdapter extends RecyclerView.Adapter<EvidenciasAdapter.EvidenciasViewHolder> {

    ArrayList<EvidenciasHelperClass> evidencias;


    public EvidenciasAdapter(ArrayList<EvidenciasHelperClass> evidencias) {
        this.evidencias = evidencias;
    }

    @NonNull
    @Override
    public EvidenciasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mis_evidencias_card_design, parent, false);
        EvidenciasViewHolder evidenciasViewHolder = new EvidenciasViewHolder(view);

        return evidenciasViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull EvidenciasViewHolder holder, int position) {

        EvidenciasHelperClass evidenciasHelperClass = evidencias.get(position);

        holder.ic_evidencias.setImageResource(evidenciasHelperClass.geteImg());
        holder.fecha.setText(evidenciasHelperClass.getFecha());
        holder.evidencia.setText(evidenciasHelperClass.getNomEvidencia());

    }

    @Override
    public int getItemCount() {
        return evidencias.size();
    }

    public static class EvidenciasViewHolder extends RecyclerView.ViewHolder{

        ImageView ic_evidencias;
        TextView fecha, evidencia;

        public EvidenciasViewHolder(@NonNull View itemView) {
            super(itemView);

            ic_evidencias = itemView.findViewById(R.id.eImg);
            fecha = itemView.findViewById(R.id.txtCardFechaEvidencia);
            evidencia = itemView.findViewById(R.id.txtCardEvidencia);

        }
    }





}
