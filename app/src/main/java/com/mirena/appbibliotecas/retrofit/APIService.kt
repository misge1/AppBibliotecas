package com.mirena.appbibliotecas.retrofit

import com.mirena.appbibliotecas.objects.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface APIService {

    @GET("/login")
    fun login(@Query("email") email: String, @Query("password") pass: String): Call<Usuario>

    @GET("/getUser/{id}")
    suspend fun getUser(@Path("id") id: Int): Response<Usuario>

    @GET("/getgeneros")
    suspend fun getGeneros(): Response<List<Generos>>

    @GET("/getsubgeneros")
    suspend fun getSubgeneros(@Body genero:Int ): Response<List<Subgeneros>>

    @GET("/getlibrossubgeneros/{id}")
    suspend fun getLibrosSubgenero(@Path("id") id: Int):  Response<List<LibroPre>>

    @GET("/getrandombooks")
    suspend fun getRandomLibros(): Response<List<LibroPre>>

    @GET("/getdisponibilidad/{id}")
    suspend fun getDisponibilidad(@Path("id") id: Int): Response<List<Biblioteca>>

    @POST("/addfavorito")
    fun addFavorito(@Body favoritos: Favoritos): Call<ResponseBody>

    @DELETE("/deletefavoritos")
    fun deleteFavorito(@Query("id_libro") id_libro: Int, @Query("id_usuario") id_usuario: Int): Call<ResponseBody>


}