package com.example.mashicommits;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class Transaccion extends AppCompatActivity {
    private AppCompatButton btnTransferir;
    private EditText monto;
    private EditText destino;
    private int origen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaccion);

        btnTransferir = findViewById(R.id.btnTransferir);

        origen = Integer.valueOf(getIntent().getStringExtra("origen"));

        destino = findViewById(R.id.eCuentaDestino);

        monto = findViewById(R.id.eMonto);

        //Se establece el escenario para realizar las peticiones web
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60,TimeUnit.SECONDS).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/Banco_Servidor/srv/cliente/")
                .client(client)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();
        APIService apiService = retrofit.create(APIService.class);


        btnTransferir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int d = Integer.valueOf(destino.getText().toString());
                double m = Double.valueOf(monto.getText().toString());
                if(d != origen){
                    System.out.println("Datos: "+d+"  "+origen);
                    Call<ResponseBody> result = apiService.transferir(origen, d, m);
                    result.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            try {
                                String respuesta = response.body().string();
                                if(respuesta.equals("Exitoso")){
                                    finish();
                                }
                            } catch (Exception e) {
                                Snackbar.make(v, "Se ha producido un error. ("+e.getMessage()+")",
                                        Snackbar.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            System.out.println("*Servidor*: Se ha producido un error: "+t.getMessage());
                        }
                    });
                }
                else{
                    Snackbar.make(v, "No se puede transferir dinero a la misma cuenta.",
                            Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_back, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
