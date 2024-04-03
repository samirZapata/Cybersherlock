package HelperClasses.HomeAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.usbbog.cbs_app.R;
import com.usbbog.cbs_app.modelHelper.CasosHolder;

import java.util.List;

public class CAdapter extends RecyclerView.Adapter<CAdapter.viewHolder> {

    private List<CasosHolder> casos;
    Context contextV;

    public CAdapter(List<CasosHolder> casos, Context contextV) {
        this.casos = casos;
        this.contextV = contextV;
    }

    @NonNull
    @Override
    public CAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mis_casos_design, parent, false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CAdapter.viewHolder holder, int position) {
        holder.idCasos.setText(casos.get(position).getNombreCaso());
        holder.desCasos.setText(casos.get(position).getDesc());
    }

    @Override
    public int getItemCount() {
        return casos.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        TextView idCasos, desCasos;
        ImageView btnVerMas;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            idCasos = itemView.findViewById(R.id.txtCardCaso);
            desCasos = itemView.findViewById(R.id.txtCardDesc);
            btnVerMas = itemView.findViewById(R.id.btnMasDetalle);
        }
    }
}
