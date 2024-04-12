package com.usbbog.cbs_app.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.usbbog.cbs_app.R;
import com.usbbog.cbs_app.modelHelper.AppData;
import com.usbbog.cbs_app.modelHelper.CasosHolder;
import com.usbbog.cbs_app.networking.ApiServices;
import com.usbbog.cbs_app.networking.Network;
import com.usbbog.cbs_app.networking.RetrofitClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import HelperClasses.HomeAdapter.CAdapter;
import HelperClasses.HomeAdapter.CasosAdapter;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Casos extends AppCompatActivity {
    private static final String TAG = "Casos";

    private ApiServices apiService;
    private CAdapter adapter;
    private List<CasosHolder> casosList = new ArrayList<CasosHolder>();
    CasosHolder casosH;
    ListView listView;

    TextView txtTitulo,txtDescripcion, txtFecha;
    Button btnVerMas, btnNuevo;
    private Network apiUrl = new Network();
    int counter = 0;
    RequestQueue requestQueue;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_casos);

        //HOOKS ######################################################
        listView = findViewById(R.id.listCasos);
        btnNuevo = findViewById(R.id.btnNuevoCaso);
        btnVerMas = findViewById(R.id.btnVerMasC);
        requestQueue = Volley.newRequestQueue(this);
        //############################################################

        adapter = new CAdapter(this, casosList);
        listView.setAdapter(adapter);
        getCaseByEmail();


        listView.setOnItemClickListener((parent, view, position, id) -> {
            CasosHolder selectedCase = casosList.get(position);
            //Toast.makeText(Casos.this, "Caso seleccionado: " + selectedCase.getNombreCaso(), Toast.LENGTH_SHORT).show();
            // Aquí puedes realizar alguna acción al hacer clic en un elemento de la lista
        });

        btnNuevo.setOnClickListener((View view)->{
            Intent a = new Intent(Casos.this, Nuevo_caso.class);
            startActivity(a);
        });

    }


    private void getCaseByEmail(){
        apiService = RetrofitClient.getClient().create(ApiServices.class);
        String currentMail = AppData.getCorreo();

        Call<ResponseBody> call = apiService.getCaseByMail(currentMail);

        call.enqueue(new Callback<ResponseBody>() {


            @Override
            public void onResponse(retrofit2.Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.isSuccessful()){
                    try {

                        String responseBody = response.body().string();

                        Gson gson = new Gson();
                        JsonObject jsonObject = gson.fromJson(responseBody, JsonObject.class);


                        JsonArray casosArray = jsonObject.getAsJsonArray("cases");

                        for (JsonElement elemento : casosArray) {
                            JsonObject casos = elemento.getAsJsonObject();
                            String cName = casos.get("nombreCaso").getAsString();
                            String cDesc = casos.get("desc").getAsString();
                            String cEvidencia = casos.get("Evidencias").getAsString();
                            //casosList.add(new CasosHolder(casosH.setNombreCaso(cName)));
                        }
                        Log.i(TAG, "Número de casos en la lista: " + casosList.size());
                        adapter.notifyDataSetChanged();

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else {
                    Log.i(TAG, "Error en la respuesta: " + response.code());
                }

            }

            @Override
            public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, "Error de red: " + t.getMessage(), t);
            }
        });


    }


    private void handleBtnVerMasClick(CasosHolder item, int position) {
        Intent a = new Intent(Casos.this, DetalleCasos.class);
        startActivity(a);
    }

    @Override
    public void onResume() {
        super.onResume();

        // Si se requiere refrescar la lista cada vez que regresa a este activity, descomentar la siguiente línea
        //adapter.updateDataSet(null);
    }


}