package HelperClasses.HomeAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.usbbog.cbs_app.R;

import java.util.ArrayList;

public class ConsejosAdapter extends RecyclerView.Adapter<ConsejosAdapter.ConsejosViewHolder> {

    ArrayList<ConsejosHelperClass> consejos;

    public ConsejosAdapter(ArrayList<ConsejosHelperClass> consejos) {
        this.consejos = consejos;
    }

    @NonNull
    @Override
    public ConsejosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.consejos_card_design, parent, false);
        ConsejosViewHolder consejosViewHolder = new ConsejosViewHolder(view);

        return consejosViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ConsejosViewHolder holder, int position) {
        ConsejosHelperClass consejosHelperClass = consejos.get(position);

        holder.title.setText(consejosHelperClass.getTitle());
        holder.desc.setText(consejosHelperClass.getDesc());

    }

    @Override
    public int getItemCount() {
        return consejos.size();
    }

    public static class ConsejosViewHolder extends RecyclerView.ViewHolder{

        TextView title, desc;

        public ConsejosViewHolder(@NonNull View itemView) {
            super(itemView);


            //START HOOKS
            title = itemView.findViewById(R.id.txtTitleCard);
            desc  = itemView.findViewById(R.id.txtDesCard);
            //END HOOSKS

        }
    }

}
