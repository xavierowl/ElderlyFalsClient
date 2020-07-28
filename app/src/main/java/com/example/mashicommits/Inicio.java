package com.example.mashicommits;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mashicommits.adapters.AdapterCredito;
import com.example.mashicommits.models.Credito;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class Inicio extends AppCompatActivity implements AdapterCredito.OnCreditoListener {
    private RecyclerView rvCreditos;
    private String cuenta;
    private AdapterCredito adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        rvCreditos = findViewById(R.id.rVCreditos);
        cuenta = getIntent().getStringExtra("cuenta");

        //Adaptador que contendrá las cuotas del crédito
        adaptador = new AdapterCredito(this);

        setAdapterCreditos(rvCreditos);

        rvCreditos.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void setAdapterCreditos(RecyclerView rvCreditos){
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100,TimeUnit.SECONDS).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/Banco_Servidor/srv/cliente/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService apiService = retrofit.create(APIService.class);

        Call<List<Credito>> creditosRquest = apiService.getCreditos(Integer.valueOf(cuenta));

        creditosRquest.enqueue(new Callback<List<Credito>>() {
            @Override
            public void onResponse(Call<List<Credito>> call, Response<List<Credito>> response) {
                for(int x = 0; x < response.body().size(); x++){
                    Credito credito = new Credito();
                    credito.setId(response.body().get(x).getId());
                    credito.setFechaVencimiento(response.body().get(x).getFechaVencimiento());
                    credito.setMonto(response.body().get(x).getMonto());
                    credito.setSaldo(response.body().get(x).getSaldo());
                    credito.setTipo(response.body().get(x).getTipo());
                    adaptador.addCredito(credito);
                }
                rvCreditos.setAdapter(adaptador);
            }

            @Override
            public void onFailure(Call<List<Credito>> call, Throwable t) {
                System.out.println("Se ah producido el siguiente error: "+t.getMessage());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_change_password:
                Intent recuperaContra = new Intent(Inicio.this, RecuperaPassword.class);
                recuperaContra.putExtra("correo", getIntent().getStringExtra("correo"));
                startActivityForResult(recuperaContra,1);
                return true;
            case R.id.action_transacion:
                Intent transaccion = new Intent(Inicio.this, Transaccion.class);
                transaccion.putExtra("origen", cuenta);
                startActivityForResult(transaccion, 1);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreditoClick(int credito) {
        System.out.println("Ha seleccionado el crédito #"+credito);
    }
}
