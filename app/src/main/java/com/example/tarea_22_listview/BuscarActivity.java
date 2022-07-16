package com.example.tarea_22_listview;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
//import com.example.tarea_22_listview.Interfaces.ApiUsu;
//import com.example.tarea_22_listview.Usu.usuario;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BuscarActivity extends AppCompatActivity {

    ListView listapersonas;
    ArrayList<String> titulos = new ArrayList<>();
    ArrayAdapter arrayAdapter;
    EditText txtidusuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar);

        txtidusuario = (EditText) findViewById(R.id.txtidusuario);


        txtidusuario.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ObtenerUsuario(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, titulos);
        listapersonas = (ListView) findViewById(R.id.listusers);
        listapersonas.setAdapter(arrayAdapter);
    }
    // Lo primero
    private void obtenerListaPersonas()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiUsu usuariosApi = retrofit.create(ApiUsu.class);

        Call<List<usuario>> calllista = ApiUsu.getusuario();

        calllista.enqueue(new Callback<List<usuario>>() {
            @Override
            public void onResponse(Call<List<usuario>> call, Response<List<usuario>> response)
            {
                for(usuario usuarios : response.body())
                {
                    Log.i(usuarios.getTitle(), "onResponse");
                    titulos.add(usuarios.getTitle());

                    arrayAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<usuario>> call, Throwable t) {
                t.getMessage();
            }
        });
    }

    private void ObtenerUsuario(String texto)
    {


        if (titulos.size()>0)
        {
            titulos.remove(0);
        }
        arrayAdapter.notifyDataSetChanged();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiUsu  = retrofit.create(ApiUsu.class);

        Call<usuario> calllista = ApiUsu.getUsuario(texto);
        calllista.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<usuario> call, Response<usuario> response)
            {
                Log.i(response.body().getTitle(), "onResponse");
                titulos.add(response.body().getTitle());

                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<usuario> call, Throwable t) {

            }
        });
    }
}
