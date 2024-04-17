package HelperClasses.HomeAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.usbbog.cbs_app.R;
import com.usbbog.cbs_app.modelHelper.CasosHolder;

import java.util.List;

public class ListCasosAdapter extends ArrayAdapter<CasosHolder> {

    private Context context;
    private List<CasosHolder> casos;
    public ListCasosAdapter(@NonNull Context context, int resource, @NonNull List<CasosHolder> casos) {
        super(context, resource, casos);
        this.context = context;
        this.casos = casos;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.mis_casos_design, parent, false);

        TextView nombreCasoTextView = row.findViewById(R.id.txtCardCaso);
        TextView descTextView = row.findViewById(R.id.txtCardDesc);

        nombreCasoTextView.setText(casos.get(position).getNombreCaso());
        descTextView.setText(casos.get(position).getDesc());

        return row;
    }
}
