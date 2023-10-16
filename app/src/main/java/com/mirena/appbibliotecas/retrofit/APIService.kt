package com.mirena.appbibliotecas.retrofit

import com.mirena.appbibliotecas.objects.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface APIService {

    @GET("/login")
    fun login(@Query("email") email: String, @Query("password") pass: String): Call<Usuario>

    @PUT("/editUserPass/{id}")
    fun cambiarPass(@Path("id") id: Int, @Query("pass") pass: String): Call<ResponseBody>

    @PUT("/editUserInfo/{id}")
    fun updateUserInfo(@Path("id") id: Int, @Query("nombre") nombre: String,
                       @Query("telefono") telefono: String,
                       @Query("domicilio") domicilio: String,
                       @Query("codigo_postal") codigo_postal: String,
                       @Query("localidad") localidad: String): Call<ResponseBody>

    @GET("/filtroBiblioSubg")
    suspend fun filtroBibloSubg(@Query("subgenero") subgenero: String, @Query("biblioteca") biblioteca: String):
            Response<List<LibroObject>>

    @GET("/getUser/{id}")
    suspend fun getUser(@Path("id") id: Int): Response<Usuario>

    @GET("/getgeneros")
    suspend fun getGeneros(): Response<List<Generos>>

    @GET("/getsubgeneros")
    suspend fun getSubgeneros(@Body genero:Int ): Response<List<Subgeneros>>

    @GET("/getsubgenerospornombre/{genero}")
    //@HTTP(method = "GET", path = "/getsubgenerospornombre", hasBody = true)
    suspend fun getSubgenerosPorNombre(@Path("genero") genero:String ): Response<List<Subgeneros>>

    @GET("/getlibrossubgeneros/{id}")
    suspend fun getLibrosSubgenero(@Path("id") id: Int):  Response<List<LibroPre>>

    @GET("/getrandombooks")
    suspend fun getRandomLibros(): Response<List<LibroPre>>

    @GET("/getAllLibros")
    suspend fun getAllLibros(): Response<List<LibroPre>>

    @GET("/getdisponibilidad/{id}")
    suspend fun getDisponibilidad(@Path("id") id: Int): Response<List<Biblioteca>>

    @GET("/getFavoritos/{id}")
    suspend fun getFavoritos(@Path("id") id: Int): Response<List<LibroPre>>

    @GET("/getBibliotecas")
    suspend fun getBibliotecas(): Response<List<Biblioteca>>

    @POST("/addfavorito")
    fun addFavorito(@Body favoritos: Favoritos): Call<ResponseBody>

    @DELETE("/deletefavoritos")
    fun deleteFavorito(@Query("id_libro") id_libro: Int, @Query("id_usuario") id_usuario: Int): Call<ResponseBody>

    @GET("/getEjemplar")
    suspend fun getEjemplar(@Query("id_libro") id_libro: Int, @Query("id_biblioteca") id_biblioteca: Int): Response<Ejemplar>

    @POST("/crearPrestamo")
    fun crearNuevoPrestamo(@Body prestamo: Prestamo): Call<ResponseBody>

    @GET("/getPrestamoARecoger/{id}")
    suspend fun getPrestamoARecoger(@Path("id") id: Int): Response<List<PrestamoUsuario>>

    @GET("/getPrestamosCurso/{id}")
    suspend fun getPrestamosCurso(@Path("id") id: Int): Response<List<PrestamoUsuario>>

    @GET("/getPrestamosDevolver/{id}")
    suspend fun getPrestamosDevolver(@Path("id") id: Int): Response<List<PrestamoUsuario>>

    @GET("/comprobarfavoritos")
    suspend fun comprobarfavoritos(@Query("id_libro") id_libro: Int, @Query("id_usuario") id_usuario: Int): Response<String>

}