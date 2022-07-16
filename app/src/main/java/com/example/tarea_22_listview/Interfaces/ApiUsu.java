package com.example.tarea_22_listview.Interfaces;

import com.example.tarea_22_listview.Usu.usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiUsu
{
    String Ruta0 = "/posts";
    String Ruta1= "/posts/{valor}";

    @GET(Ruta0)
    Call<List<usuario>> getUsuario();

    @GET(Ruta1)
    Call<usuario> getUsuario(@Path("valor") String valor);
}
