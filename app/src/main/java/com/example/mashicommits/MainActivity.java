package com.example.mashicommits;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class MainActivity extends AppCompatActivity {
    private TextInputEditText eUsuario;
    private TextInputEditText eClave;
    private AppCompatButton btnIniciar;
    private ProgressBar pgValidar;
    private TextView tvValidar;
    private TextView tvRecuperaContra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eUsuario = findViewById(R.id.eUsuario);

        eClave = findViewById(R.id.eClave);

        btnIniciar = (AppCompatButton) findViewById(R.id.btnIniciarSesion);

        pgValidar = findViewById(R.id.cargaImagen);

        tvValidar = findViewById(R.id.cargaTexto);

        tvRecuperaContra = findViewById(R.id.tvRecuperaContra);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        visualizarCarga(true);

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

        tvRecuperaContra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Acción al dar tap en el texto olvidé mi contraseña
                //Intent recuperaContra = new Intent(MainActivity.this, RecuperaPassword.class);
                //startActivityForResult(recuperaContra,1);
            }
        });

        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validarCampos()){
                    visualizarCarga(true);
                    Call<ResponseBody> result = apiService.loguear(eUsuario.getText().toString(),
                            eClave.getText().toString());
                    result.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            try {
                                String respuesta = response.body().string();
                                String logueado = respuesta.substring(0, 7);
                                if(logueado.equals("exitoso")){
                                    visualizarCarga(false);
                                    String cuenta = respuesta.substring(7,respuesta.length());
                                    Intent inicio = new Intent(MainActivity.this, Inicio.class);
                                    inicio.putExtra("cuenta", cuenta);
                                    inicio.putExtra("correo", eUsuario.getText().toString());
                                    startActivityForResult(inicio, 1);
                                }
                                else{
                                    Snackbar.make(v, "Las credenciales son incorrectas.",
                                            Snackbar.LENGTH_LONG).show();
                                    visualizarCarga(false);
                                }
                            } catch (Exception e) {
                                Snackbar.make(v, "Se ah producido un error. ("+e.getMessage()+")",
                                        Snackbar.LENGTH_LONG).show();
                                visualizarCarga(false);
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            System.out.println("Ha ocurrido un error: "+t.getMessage());
                        }
                    });
                }
            }
            private boolean validarCampos() {
                boolean validarCampos = true;
                if(eUsuario.getText().toString().trim().isEmpty()){
                    System.out.println(eUsuario.getText().toString().trim());
                    eUsuario.setError("Por favor ingrese su usuario");
                    eUsuario.requestFocus();
                    validarCampos = false;
                }
                if(eClave.getText().toString().trim().isEmpty()){
                    System.out.println(eClave.getText().toString().trim());
                    eClave.setError("Por favor ingrese su contraseña");
                    eClave.requestFocus();
                    validarCampos = false;
                }
                return validarCampos;
            }
        });
    }

    private void visualizarCarga(Boolean visualizar) {
        if(visualizar){
            pgValidar.setVisibility(View.VISIBLE);
            tvValidar.setVisibility(View.VISIBLE);
        }
        else{
            pgValidar.setVisibility(View.INVISIBLE);
            tvValidar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        visualizarCarga(false);
    }
}
