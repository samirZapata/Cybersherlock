package HelperClasses.HomeAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.usbbog.cbs_app.R;
import com.usbbog.cbs_app.modelHelper.CasosHolder;

import java.util.List;

public class CasosAdapter extends RecyclerView.Adapter<CasosAdapter.CasosViewHolder> {

    private List<CasosHolder> casos;
    private final LayoutInflater inflater;
    private ItemClickListener clickListener;

    public CasosAdapter(List<CasosHolder> items, Context context) {
        this.inflater = LayoutInflater.from(context);
        this.casos = items;
    }

    @NonNull
    @Override
    public CasosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.mis_casos_design, parent, false);
        return new CasosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CasosViewHolder holder, int position) {
        holder.bindData(casos.get(position), clickListener);
    }

    @Override
    public int getItemCount() {
        return casos == null ? 0 : casos.size();
    }

    public void updateDataSet(List<CasosHolder> newItems) {
        casos = newItems;
        notifyDataSetChanged();
    }

    public interface ItemClickListener {
        void onItemClick(CasosHolder item, int position);
    }

    public void setOnItemClickListener(ItemClickListener listener) {
        this.clickListener = listener;
    }

    class CasosViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView idCasos, desCasos, btnVerMas;

        public CasosViewHolder(@NonNull View itemView) {
            super(itemView);

            idCasos = itemView.findViewById(R.id.txtCardEvidencia);
            desCasos = itemView.findViewById(R.id.txtCardDesc);
            btnVerMas = itemView.findViewById(R.id.btnCardVer);

            btnVerMas.setOnClickListener(this);
        }

        void bindData(final CasosHolder item, final ItemClickListener listener) {
            idCasos.setText(item.getNombreCaso());
            desCasos.setText(item.getDesc());

            if (listener != null) {
                itemView.setOnClickListener(v -> listener.onItemClick(item, getAdapterPosition()));
            }
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) {
                clickListener.onItemClick(casos.get(getAdapterPosition()), getAdapterPosition());
            }
        }
    }
}