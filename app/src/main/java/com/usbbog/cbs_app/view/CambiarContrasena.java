package com.usbbog.cbs_app.view;

import static com.usbbog.cbs_app.R.id.txtLayoutPassConfirm;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;
import com.usbbog.cbs_app.R;
import com.usbbog.cbs_app.modelHelper.UsuariosHolder;
import com.usbbog.cbs_app.modelHelper.MailHolder;
import com.usbbog.cbs_app.networking.Network;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;


/*
*                                .-"``"-.
                                /______; \
                               {_______}\|
                               (/ a a \)(_)
                               (.-.).-.)
  _______________________ooo__(    ^    )___________________________
 /                             '-.___.-'                            \
|    RECOPILEMOS PRUEBAS, HAGAMOS JUSTICIA                           |
 \________________________________________ooo_______________________/
                               |_  |  _|
                               \___|___/
                               {___|___}
                                |_ | _|
                                /-'Y'-\
                               (__/ \__)
* */

public class CambiarContrasena extends AppCompatActivity {

    ImageView btnBack;
    Button btnFinish;
    EditText passOne, passConfirm;
    TextInputLayout layoutPass;
    private Network url = new Network();
    private String apiUrl = url.getApiUpdatePassword();
    UsuariosHolder getMail = new UsuariosHolder();


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_cambiar_contrasena);

        //HOOKS START------------------------------------
            btnBack = findViewById(R.id.btnBackThree);
            btnFinish = findViewById(R.id.btnLoginFP);
            passOne = findViewById(R.id.edtNuevaContrasena);
            passConfirm = findViewById(R.id.edtCNuevaContrasena);
            layoutPass = findViewById(txtLayoutPassConfirm);
        //HOOKS END--------------------------------------


        //System.out.println("CORREO RECIBIDO: " + getMail.getCorreo());


        btnBack.setOnClickListener((View view)->{
            Intent a = new Intent(CambiarContrasena.this, CambiarContrasena.class);
            startActivity(a);
        });

        btnFinish.setOnClickListener((View view)->{
            cambiarContrasena(apiUrl);
        });
    }

    boolean hasMatch = false;

    private void compararPassword(){

        String one = passOne.getText().toString();

        passConfirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String two = s.toString();

                if (!hasMatch){
                    if (!two.equals(one)){
                        layoutPass.setError("Las contraseñas no coinciden");
                    }else{
                        layoutPass.setError(null);
                    }
                    hasMatch = true;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

    }

    private void cambiarContrasena(String baseUrl){

        Log.i("URL ROUTE: ", baseUrl.substring(baseUrl.indexOf("/api")));
        getMail = MailHolder.getInstance().getHolder();
        System.out.println("CORREO QUE LLEGA: " + getMail.getCorreo());
        StringRequest request = new StringRequest(Request.Method.POST, baseUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                compararPassword();
                Toast.makeText(CambiarContrasena.this, "Cmbio exitoso", Toast.LENGTH_SHORT).show();
                Intent c = new Intent(CambiarContrasena.this, Login.class);
                startActivity(c);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error: ", error + "");
            }
        }){
            public String getBodyContentType(){

                return "application/json; charset=utf-8";
            }

            public byte[] getBody() throws AuthFailureError{
                try {
                    JSONObject jsonObject = new JSONObject();

                    jsonObject.put("correo", getMail.getCorreo());
                    jsonObject.put("newPassword", passOne.getText().toString());
                    return jsonObject.toString().getBytes("utf-8");

                } catch (JSONException | UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
            }

        };
        RequestQueue up = Volley.newRequestQueue(this);
        up.add(request);
    }

}