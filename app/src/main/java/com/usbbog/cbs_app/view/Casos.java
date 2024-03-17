package com.usbbog.cbs_app.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.usbbog.cbs_app.R;
import com.usbbog.cbs_app.modelHelper.AppData;
import com.usbbog.cbs_app.modelHelper.CasosHolder;
import com.usbbog.cbs_app.networking.Network;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import HelperClasses.HomeAdapter.CasosAdapter;
import HelperClasses.HomeAdapter.CasosHelperClass;


public class Casos extends AppCompatActivity {
    private static final String TAG = "Casos";


    RecyclerView rcEvidencias;
    TextView txtTitulo,txtDescripcion, txtFecha;
    Button btnVerMas, btnNuevo;
    private Network apiUrl = new Network();
    ArrayList<CasosHelperClass> casos = new ArrayList<>();
    private CasosAdapter adapter;
    int counter = 0;
    RequestQueue requestQueue;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_casos);


        rcEvidencias = findViewById(R.id.rcEvidencias);
        btnNuevo = findViewById(R.id.btnNuevoCaso);
        btnVerMas = findViewById(R.id.btnVerMasC);
        requestQueue = Volley.newRequestQueue(this);
        String currentMail = AppData.getCorreo();
        String baseUrl = apiUrl.getApiGetCasos(currentMail);


        List<CasosHolder> casosList;

        casosList(baseUrl);
        btnNuevo.setOnClickListener((View view)->{
            Intent a = new Intent(Casos.this, Nuevo_caso.class);
            startActivity(a);
        });

    }



   /* private void loadCasos(String baseUrl) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, baseUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    setupRecyclerView();
                    JSONArray jsonArray = new JSONArray(response);
                    casos = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); ++i) {
                        JSONObject jsonObj = jsonArray.getJSONObject(i);
                        casos.add(new CasosHelperClass(
                                jsonObj.getString("nombreCaso");
                                jsonObj.getString("desc");



                        ));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error: ", error + "");
                if (error instanceof AuthFailureError) {
                    Toast.makeText(Casos.this, "", Toast.LENGTH_SHORT).show();
                }
            }
        });
        RequestQueue RU = Volley.newRequestQueue(this);
        RU.add(stringRequest);
    }*/


   /* private void casosList(String baseUrl){
        Log.i(TAG, baseUrl);

        JsonObjectRequest casosRequest = new JsonObjectRequest(
                Request.Method.GET,
                baseUrl,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("data");
                            int size = jsonArray.length();
                            List<CasosHolder> items = new ArrayList<>();

                            for (int i = 0; i < size; i++) {
                                JSONObject jsonObject = new JSONObject(jsonArray.get(i).toString());

                                String casosNombre = jsonObject.getString("nombreCaso");
                                String casosDescripcion = jsonObject.getString("desc");
                                items.add(new CasosHolder(casosNombre, null, null, casosDescripcion, null));

                                CasosAdapter adapter = new CasosAdapter(items, Casos.this);
                                adapter.setItems(items);
                                adapter.notifyItemInserted(adapter.getItemCount() - 1);

                                rcEvidencias.setHasFixedSize(true);
                                rcEvidencias.setLayoutManager(new LinearLayoutManager(Casos.this));
                                rcEvidencias.setAdapter(adapter);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.i(TAG, volleyError.getMessage());
                    }
                });
        requestQueue.add(casosRequest);
    }*/

    private void casosList(String baseUrl) {
        Log.i(TAG, baseUrl);

        JsonObjectRequest casosRequest = new JsonObjectRequest(
                Request.Method.GET,
                baseUrl,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("data");
                            int size = jsonArray.length();
                            List<CasosHolder> items = new ArrayList<>();

                            for (int i = 0; i < size; i++) {
                                JSONObject jsonObject = new JSONObject(jsonArray.get(i).toString());

                                String casosNombre = jsonObject.getString("nombreCaso");
                                String casosDescripcion = jsonObject.getString("desc");
                                items.add(
                                        new CasosHolder(
                                                casosNombre,
                                                null,
                                                null,
                                                casosDescripcion,
                                                null
                                        ));
                            }

                            adapter = new CasosAdapter(items, Casos.this);
                            adapter.setOnItemClickListener(Casos.this::handleBtnVerMasClick);

                            LinearLayoutManager llm = new LinearLayoutManager(Casos.this);
                            llm.setOrientation(LinearLayoutManager.VERTICAL);
                            rcEvidencias.setLayoutManager(llm);
                            rcEvidencias.setAdapter(adapter);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.i(TAG, volleyError.getMessage());
                    }
                });
        requestQueue.add(casosRequest);
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