package com.usbbog.cbs_app.networking;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiServices {


    @GET("api/cases/{nombreCaso}")
    Call<ResponseBody> getCasoByNombreCaso(
            @Path("nombreCaso") String nombreCaso
    );

    @GET("api/cases/uploads/{casoId}/{nombreArchivo}")
    Call<ResponseBody> descargarArchivo(
            @Path("casoId") String casoId,
            @Path("nombreArchivo") String nombreArchivo
    );

    @GET("api/cases/aux/{createdBy}")
    Call<ResponseBody> getByMail(
            @Path("createdBy") String correo
    );

    @GET("api/auth/{correo}")
    Call<ResponseBody> getUserByCorreo(
            @Path("correo") String correo
    );

}
