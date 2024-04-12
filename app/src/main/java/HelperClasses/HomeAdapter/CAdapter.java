package HelperClasses.HomeAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.usbbog.cbs_app.R;
import com.usbbog.cbs_app.modelHelper.CasosHolder;

import java.util.List;

public class CAdapter extends ArrayAdapter<CasosHolder> {

    private LayoutInflater inflater;

    public CAdapter(Context context, List<CasosHolder> casosList) {
        super(context, 0, casosList);
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.mis_casos_design, parent, false);
        }

        CasosHolder casosHolder = getItem(position);

        // Aquí configuras las vistas dentro de cada elemento del ListView
        TextView nombreCasoTextView = view.findViewById(R.id.txtCardCaso);
        TextView descTextView = view.findViewById(R.id.txtCardDesc);
        TextView evidenciasTextView = view.findViewById(R.id.txtCardEvidencia);

        if (casosHolder != null) {
            nombreCasoTextView.setText(casosHolder.getNombreCaso());
            descTextView.setText(casosHolder.getDesc());
            evidenciasTextView.setText(casosHolder.getEvidencias());
        }

        return view;
    }
}
