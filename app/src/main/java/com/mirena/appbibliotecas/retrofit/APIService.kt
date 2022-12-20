package com.mirena.appbibliotecas.retrofit

import com.mirena.appbibliotecas.objects.Generos
import com.mirena.appbibliotecas.objects.LibroPre
import retrofit2.Response
import retrofit2.http.GET

interface APIService {

    @GET("/getgeneros")
    suspend fun getGeneros(): Response<List<Generos>>

    @GET("/getrandombooks")
    suspend fun getRandomLibros(): Response<List<LibroPre>>


}