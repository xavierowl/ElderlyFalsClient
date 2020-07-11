package com.example.mashicommits;

import com.example.mashicommits.models.Credito;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIService {
    @GET("loguear")
    Call<ResponseBody> loguear(@Query("user") String user, @Query("clave") String clave);

    @GET("getCreditos")
    Call<List<Credito>> getCreditos(@Query("cue_id") int cue_id);

    @GET("getCredito")
    Call<Credito> getCredito();
}