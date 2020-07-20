package com.example.mashicommits;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class RecuperaPassword extends AppCompatActivity {
    private AppCompatButton btnCambiar;
    private EditText txtCorreo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recupera_password);

        btnCambiar = (AppCompatButton)findViewById(R.id.btnCambiar);
        txtCorreo = findViewById(R.id.txtCorreo);

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

        btnCambiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String correo_actual = getIntent().getStringExtra("correo");
                String correo_ingresado = txtCorreo.getText().toString();
                if(correo_actual.equals(correo_ingresado)){
                    Call<ResponseBody> result = apiService.cambiarClave(txtCorreo.getText().toString());
                    result.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            try {
                                String respuesta = response.body().string();
                                if(respuesta.equals("Clave cambiada")){
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
                    Snackbar.make(v, "El correo ingresado no coincide con la cuenta actual.",
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}
