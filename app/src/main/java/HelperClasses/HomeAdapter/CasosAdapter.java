package HelperClasses.HomeAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.usbbog.cbs_app.R;
import com.usbbog.cbs_app.view.DetalleCasos;

import java.util.ArrayList;

public class CasosAdapter extends RecyclerView.Adapter<CasosAdapter.CasosViewHolder>{

    ArrayList<CasosHelperClass> casos;
    private Context context;

    public CasosAdapter(Context context, ArrayList<CasosHelperClass> casos){
        this.context = context;
        this.casos = casos;
    }

    @NonNull
    @Override
    public CasosAdapter.CasosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mis_casos_design, parent, false);
        CasosViewHolder casosViewHolder = new CasosViewHolder(view);

        return casosViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CasosAdapter.CasosViewHolder holder, int position) {

        CasosHelperClass casosHelperClass = casos.get(position);

        holder.idCasos.setText(casosHelperClass.getIdCasos());
        holder.desCasos.setText(casosHelperClass.getDesCasos());
        holder.btnVerMas.setOnClickListener(v -> {
            handleBtnVerMasClick(casosHelperClass.getIdCasos());
        });

    }

    private void handleBtnVerMasClick(String idCasos) {
        Intent a = new Intent(context, DetalleCasos.class);
        context.startActivity(a);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class CasosViewHolder extends RecyclerView.ViewHolder{
        TextView idCasos, desCasos, btnVerMas;

        public CasosViewHolder(@NonNull View itemView){
            super(itemView);

            idCasos = itemView.findViewById(R.id.txtCardEvidencia);
            desCasos = itemView.findViewById(R.id.txtCardDesc);
            btnVerMas = itemView.findViewById(R.id.btnCardVer);
        }

    }

}
