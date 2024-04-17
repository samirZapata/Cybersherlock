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
import com.google.gson.JsonParseException;
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
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import HelperClasses.HomeAdapter.CAdapter;
import HelperClasses.HomeAdapter.CasosAdapter;
import HelperClasses.HomeAdapter.ListCasosAdapter;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Casos extends AppCompatActivity {
    private static final String TAG = "Casos";

    private ApiServices apiService;
    private ListCasosAdapter adapter;
    List<CasosHolder> casos = new ArrayList<>();

    private List<JSONObject> casosList = new ArrayList<>();
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

        adapter = new ListCasosAdapter(this, R.layout.mis_casos_design, casos);
        listView.setAdapter(adapter);
        getCasos();


        listView.setOnItemClickListener((parent, view, position, id) -> {
            //CasosHolder selectedCase = casosList.get(position);
            //Toast.makeText(Casos.this, "Caso seleccionado: " + selectedCase.getNombreCaso(), Toast.LENGTH_SHORT).show();
            // Aquí puedes realizar alguna acción al hacer clic en un elemento de la lista
        });

        btnNuevo.setOnClickListener((View view)->{
            Intent a = new Intent(Casos.this, Nuevo_caso.class);
            startActivity(a);
        });

    }


   /* private void getCaseByEmail(){
        apiService = RetrofitClient.getClient().create(ApiServices.class);
        String currentMail = AppData.getCorreo();

        Call<ResponseBody> call = apiService.getByMail(currentMail);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(retrofit2.Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {

                        String responseBody = response.body().string();
                        Gson gson = new Gson();
                        JsonElement jsonElement = gson.fromJson(responseBody, JsonElement.class);

                        if (jsonElement.isJsonArray()) {
                            // Convertir JsonArray a List<JSONObject>
                            Iterator<JsonElement> iterator = jsonElement.getAsJsonArray().iterator();
                            while (iterator.hasNext()) {
                                JsonObject jsonObject = iterator.next().getAsJsonObject();
                                casosList.add(new JSONObject(jsonObject.toString()));
                                Log.d(TAG, "Número de casos ( ARRAY ) recibidos: " + casosList.size());

                            }
                        } else if (jsonElement.isJsonObject()) {
                            // Convertir JsonObject a JSONObject
                            JsonObject jsonObject = jsonElement.getAsJsonObject();
                            casosList.add(new JSONObject(jsonObject.toString()));
                            Log.d(TAG, "Número de casos ( OBJECT ) recibidos: " + casosList.size());

                        }
                        adapter.notifyDataSetChanged();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    Log.i(TAG, "Error en la respuesta: " + response.code());
                }
            }
            @Override
            public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, "Error de red: " + t.getMessage(), t);
            }
        });
    }
*/


    private void getCasos(){
        apiService = RetrofitClient.getClient().create(ApiServices.class);
        String currentMail = AppData.getCorreo();

        Call<ResponseBody> call = apiService.getByMail(currentMail);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                List<CasosHolder> data = (List<CasosHolder>) response.body();
                casos.add((CasosHolder) data);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

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