package com.usbbog.cbs_app.networkTesting;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.usbbog.cbs_app.modelTesting.model.LoginResponse;
import com.usbbog.cbs_app.modelTesting.model.User;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class LoginClient {

    public LoginResponse login(String email, String password) {
        RequestQueue queue = Volley.newRequestQueue(null);
        String url = "";

        Map<String, String> params = new HashMap<>();
        params.put("email", email);
        params.put("password", password);

        Gson gson = new Gson();
        String jsonRequest = gson.toJson(params);

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                response -> {
                    Type listType = new TypeToken<LoginResponse>() {}.getType();
                    LoginResponse loginResponse = gson.fromJson(response, listType);
                },
                error -> {
                    // Handle errors here
                }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json; utf-8");
                return headers;
            }

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return jsonRequest.getBytes("utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        };

        queue.add(stringRequest);
        return null;
    }
}