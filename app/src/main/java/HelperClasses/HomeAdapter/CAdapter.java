package HelperClasses.HomeAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.usbbog.cbs_app.R;
import com.usbbog.cbs_app.modelHelper.CasosHolder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class CAdapter extends ArrayAdapter<JSONObject> {

    private LayoutInflater inflater;

    public CAdapter(Context context, List<JSONObject> casosList) {
        super(context, 0, casosList);
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.mis_casos_design, parent, false);
            holder = new ViewHolder();
            holder.nombreCasoTextView = convertView.findViewById(R.id.txtCardCaso);
            holder.descTextView = convertView.findViewById(R.id.txtCardDesc);
            holder.evidenciasTextView = convertView.findViewById(R.id.txtCardEvidenciaD);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        JSONObject caso = getItem(position);

        if (caso != null) {
            try {
                holder.nombreCasoTextView.setText(caso.getString("nombreCaso"));
                holder.descTextView.setText(caso.getString("desc"));
                // holder.evidenciasTextView.setText(caso.getJSONArray("evidencias").toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return convertView;
    }
    static class ViewHolder {
        TextView nombreCasoTextView;
        TextView descTextView;
        TextView evidenciasTextView;
    }
}
