package com.mirena.appbibliotecas.retrofit

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.loader.content.AsyncTaskLoader
import com.mirena.appbibliotecas.SessionManager
import com.mirena.appbibliotecas.objects.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RetrofitRepository(var context: Context) {

    private var sessionManager: SessionManager = SessionManager(context)
    private var favoritoslivedata: MutableLiveData<List<LibroPre>> = MutableLiveData<List<LibroPre>>()
    private var disponibilidadlivedata: MutableLiveData<List<Biblioteca>> = MutableLiveData<List<Biblioteca>>()
    private var usuariolivedata: MutableLiveData<Usuario> = MutableLiveData<Usuario>()
    private var ejemplarlivedata: MutableLiveData<Ejemplar> = MutableLiveData<Ejemplar>()
    private var listalibros: MutableLiveData<List<LibroPre>> = MutableLiveData<List<LibroPre>>()
    private var listaGeneros: MutableLiveData<List<Generos>> = MutableLiveData<List<Generos>>()
    private var listaPrestamosDevolver: MutableLiveData<List<PrestamoUsuario>> = MutableLiveData<List<PrestamoUsuario>>()
    private var listaPrestamosRecoger: MutableLiveData<List<PrestamoUsuario>> = MutableLiveData<List<PrestamoUsuario>>()
    private var listaPrestamosEnCurso: MutableLiveData<List<PrestamoUsuario>> = MutableLiveData<List<PrestamoUsuario>>()
    private var comprobacionfav: MutableLiveData<String> = MutableLiveData<String>()
    private var listalibrosAll: MutableLiveData<List<LibroPre>> = MutableLiveData<List<LibroPre>>()
    private var listaSubgeneros: MutableLiveData<List<Subgeneros>> = MutableLiveData<List<Subgeneros>>()
    private var listaSubgenerosNombre: MutableLiveData<List<Subgeneros>> = MutableLiveData<List<Subgeneros>>()
    private var listaBibliotecas: MutableLiveData<List<Biblioteca>> = MutableLiveData<List<Biblioteca>>()
    private var listaFiltradaSubBiblio: MutableLiveData<List<LibroObject>> = MutableLiveData<List<LibroObject>>()
    private var subgenerosLibro: MutableLiveData<List<Subgeneros>> = MutableLiveData<List<Subgeneros>>()
    private var listaAllSubGeneros: MutableLiveData<List<Subgeneros>> = MutableLiveData<List<Subgeneros>>()
    private var listaIdiomas: MutableLiveData<List<Idioma>> =MutableLiveData<List<Idioma>>()
    private var listaLibrosFiltrados: MutableLiveData<List<LibroPre>> = MutableLiveData<List<LibroPre>>()
    private var listaLibrosSubgeneros: MutableLiveData<List<LibroPre>> = MutableLiveData<List<LibroPre>>()
    private var listaLibrosBusqueda: MutableLiveData<List<LibroPre>> = MutableLiveData<List<LibroPre>>()
    private var listaFavoritosTabla: MutableLiveData<List<Favoritos>> = MutableLiveData<List<Favoritos>>()
    private var comentariosLibro: MutableLiveData<List<Comentario>> = MutableLiveData<List<Comentario>>()
    private var listaBibliotecasPersonales: MutableLiveData<List<BibliotecaPersonal>> = MutableLiveData<List<BibliotecaPersonal>>()


    /**
     * Lista de favoritos del usuario
     */

    suspend fun getListaFavoritosTabla(idUsuario: Int){
        val call = RetrofitInstance.api.getFavoritosTabla(idUsuario);
        val body = call.body()

        if(call.isSuccessful){
            body.let {
                listaFavoritosTabla.postValue(it)
            }
        }
    }

    fun getListaFavoritosTablaLD(): LiveData<List<Favoritos>> {
        return listaFavoritosTabla
    }

    /**
     * LISTA DE LIBROS FILTRADA
     */

    suspend fun getListaFiltrada(arrayIdiomas: ArrayList<Int>, arrayBibliotecas: ArrayList<Int>, arraySubgeneros: ArrayList<Int>, disponibles: Int){
        val call = RetrofitInstance.api.filtrar(arrayIdiomas, arrayBibliotecas, arraySubgeneros, disponibles);
        val body = call.body()

        if (call.isSuccessful){
            body.let {
                listaLibrosFiltrados.postValue(it)
            }
        }
    }

    fun getListaFiltradaLiveData(): LiveData<List<LibroPre>>{
        return listaLibrosFiltrados
    }

    /**
     * LISTA DE LIBROS BÚSQUEDA
     */

    suspend fun getListaBusqueda(arrayBusqueda: ArrayList<String>){
        val call = RetrofitInstance.api.buscar(arrayBusqueda);
        val body = call.body()

        if(call.isSuccessful){
            body.let {
                listaLibrosBusqueda.postValue(it)
            }
        }
    }

    fun getListaBusquedaLiveData(): LiveData<List<LibroPre>>{
        return listaLibrosBusqueda;
    }

    /**
     * LISTA DE BIBLIOTECAS PERSONALES
     */
    suspend fun getBibliotecasPersonales(id_usuario: Int){
        val call = RetrofitInstance.api.getBibliotecasPersonales(id_usuario)
        val body = call.body()

        if (call.isSuccessful){
            body.let {
                listaBibliotecasPersonales.postValue(it)
            }
        }
    }

    fun getBibliosPersonalesLiveData(): LiveData<List<BibliotecaPersonal>>{
        return listaBibliotecasPersonales
    }

    /**
     * PRESTAMOS A DEVOLVER
     */

    suspend fun getPrestamosDevolver(){
        val call = RetrofitInstance.api.getPrestamosDevolver(sessionManager.fetchAuthToken())
        val body = call.body()

        if(call.isSuccessful){
            body.let {
                listaPrestamosDevolver.postValue(it)
            }
        }
    }

    fun getPDevolverLivedata(): LiveData<List<PrestamoUsuario>>{
        return listaPrestamosDevolver
    }

    /**
     * PRESTAMOS A RECOGER
     */

    suspend fun getPrestamosRecoger(){
        val call = RetrofitInstance.api.getPrestamoARecoger(sessionManager.fetchAuthToken())
        val body = call.body()

        if(call.isSuccessful){
            body.let {
                listaPrestamosRecoger.postValue(it)
            }
        }
    }

    fun getPRecogerLivedata(): LiveData<List<PrestamoUsuario>>{
        return listaPrestamosRecoger
    }


    /**
     * PRESTAMOS EN CURSO
     */

    suspend fun getPrestamosCurso(){
        val call = RetrofitInstance.api.getPrestamosCurso(sessionManager.fetchAuthToken())
        val body = call.body()

        if(call.isSuccessful){
            body.let {
                listaPrestamosEnCurso.postValue(it)
            }
        }
    }

    fun getPCursoLivedata(): LiveData<List<PrestamoUsuario>>{
        return listaPrestamosEnCurso
    }


    /**
     * LIBROS MAIN
     */

    suspend fun getRandomLibros(){
        val call = RetrofitInstance.api.getRandomLibros()
       val body = call.body()

        if (call.isSuccessful){
            body.let {
                listalibros.postValue(it)

            }
        }
    }

    fun getLibrosLivedata(): LiveData<List<LibroPre>>{
        return listalibros
    }


    /**
     * LIBRO ALL
     */

    suspend fun getAllLibros(){
        val call = RetrofitInstance.api.getAllLibros()
        val body = call.body()

        if (call.isSuccessful){
            body.let {
                listalibrosAll.postValue(it)
            }
        }
    }

    fun getAllLibrosLiveData(): LiveData<List<LibroPre>>{
        return listalibrosAll
    }

    /**
     * Libros según subgenero
     */

    suspend fun getLibrosSubgeneros(idSubgenero: Int){
        val call = RetrofitInstance.api.getLibrosSubgenero(idSubgenero)
        val body = call.body()

        if (call.isSuccessful) {
            body.let {
                listaLibrosSubgeneros.postValue(it)
            }
        }
    }

    fun getLibrosSubgeneroLivedata(): LiveData<List<LibroPre>>{
        return listaLibrosSubgeneros
    }
    /**
     * GENEROS
     */

    suspend fun getGeneros(){
        val call = RetrofitInstance.api.getGeneros()
        val body = call.body()

        if (call.isSuccessful){
            body.let {
                listaGeneros.postValue(it)
            }
        }
    }


    fun getGenerosLivedata(): LiveData<List<Generos>>{
        return listaGeneros
    }

    /**
     * SUBGENEROS
     */

    suspend fun getSubGeneros(genero: Int){
        val call = RetrofitInstance.api.getSubgeneros(genero)
        val body = call.body()

        if (call.isSuccessful){
            body.let {
                listaSubgeneros.postValue(it)
            }
        }
    }


    fun getSubGenerosLivedata(): LiveData<List<Subgeneros>>{
        return listaSubgeneros
    }


    suspend fun getSubGenerosPorNombre(genero: String){
        val call = RetrofitInstance.api.getSubgenerosPorNombre(genero)
        val body = call.body()

        if (call.isSuccessful){
            body.let {
                listaSubgenerosNombre.postValue(it)
            }
        }
    }


    fun getSubGenerosNombreLivedata(): LiveData<List<Subgeneros>>{
        return listaSubgenerosNombre
    }

    suspend fun getAllSubgeneros(){
        val call = RetrofitInstance.api.getAllSubgeneros()
        val body = call.body()

        if(call.isSuccessful){
            body.let {
                listaAllSubGeneros.postValue(it)
            }
        }
    }

    fun getAllSubgenerosLivedata(): LiveData<List<Subgeneros>>{
        return listaAllSubGeneros
    }

    /**
     * Biblioteca
     */

    suspend fun getBibliotecas(){
        val call = RetrofitInstance.api.getBibliotecas()
        val body = call.body()

        if (call.isSuccessful){
            body.let {
                listaBibliotecas.postValue(it)
            }
        }
    }


    fun getBibliotecasLivedata(): LiveData<List<Biblioteca>>{
        return listaBibliotecas
    }



    /**
     * EJEMPLARES
     */

    suspend fun getEjemplares(id_libro: Int, id_biblioteca: Int){
        val call = RetrofitInstance.api.getEjemplar(id_libro, id_biblioteca)
        val body = call.body()

        if (call.isSuccessful){
            body.let {
                ejemplarlivedata.postValue(it)
            }
        }
    }

    fun getEjemplaresLivedata(): LiveData<Ejemplar>{
        return ejemplarlivedata
    }


    /**
     * USUARIO
     */

    suspend fun getUsuario(){
        val call = RetrofitInstance.api.getUser(sessionManager.fetchAuthToken())
        var body = call.body()

        if (call.isSuccessful){
            body.let {
                usuariolivedata.postValue(it)
            }
        }
    }

    fun getUsuarioLivedata(): LiveData<Usuario> {
        return usuariolivedata
    }

    /**
     * FAVORITOS
     */

    suspend fun getFavoritos(){
        val call = RetrofitInstance.api.getFavoritos(sessionManager.fetchAuthToken())
        var body = call.body()

        if(call.isSuccessful){
            body.let {
                if (it!!.isNotEmpty()){
                    favoritoslivedata.postValue(it)
                }
            }
        }
    }

    fun getFavoritosLiveData(): LiveData<List<LibroPre>>{
        return favoritoslivedata
    }

    /**
     * IDIOMAS
     */

    suspend fun getIdiomas(){
        val call = RetrofitInstance.api.getIdiomas()
        var body = call.body()

        if(call.isSuccessful){
            body.let {
                if(it!!.isNotEmpty()){
                    listaIdiomas.postValue(it)
                }
            }
        }
    }

    fun getIdiomasLiveData(): LiveData<List<Idioma>>{
        return listaIdiomas
    }

    /**
     * DISPONIBILIDAD
     */

    suspend fun getDisponibilidad(id: Int) {
        val call = RetrofitInstance.api.getDisponibilidad(id)
        var body = call.body()

        if (call.isSuccessful){
            body.let {
                if (it!!.isNotEmpty()){
                    disponibilidadlivedata.postValue(it)
                }
            }
        }


    }

    fun getDisponibilidadLiveData(): LiveData<List<Biblioteca>> {
        return disponibilidadlivedata
    }


    /**
     * comprobar un favorito
     */

    suspend fun comprobarFavs(id_libro: Int, id_usuario: Int){
        val call = RetrofitInstance.api.comprobarfavoritos(id_libro, id_usuario)
        val body = call.body()

        if(call.isSuccessful){
            body.let {
                comprobacionfav.postValue(it)
            }
        }
    }

    fun getComprobacionfavsLd(): LiveData<String>{
        return comprobacionfav
    }

    /**
     * Obtener los subgéneros de un libro
     */
    suspend fun getSubgenerosLibro(id: Int){
        val call = RetrofitInstance.api.getSubgenerosLibro(id)
        var body = call.body()

        if (call.isSuccessful){
            body.let {
                if (it!!.isNotEmpty()){
                    subgenerosLibro.postValue(it)
                }
            }
        }
    }

    fun getSubgenerosLibroLd(): LiveData<List<Subgeneros>>{
        return subgenerosLibro;
    }

    /**
     * Comentarios de un libro
     */
    suspend fun getComentarios(id_libro: Int){
        val call = RetrofitInstance.api.getComentarios(id_libro)
        val body = call.body()

        if (call.isSuccessful){
            body.let {
                if (it!!.isNotEmpty()){
                    comentariosLibro.postValue(it)
                }
            }
        }
    }

    fun getComentariosLd(): LiveData<List<Comentario>>{
        return comentariosLibro
    }

    /**
     * Borrar préstamo
     */

     /**fun borrarPrestamo(idPrestamo: Int){
        val call = RetrofitInstance.api.borrarPrestamo(idPrestamo);

        call.enqueue(
            object : retrofit2.Callback<Void>{
                override fun onResponse(
                    call: Call<Void>,
                    response: Response<Void>
                ) {
                    if(response.isSuccessful){
                        Log.i("BORRARPRESTAMO", "prestamos borrado")
                    }else{
                        Log.i("BORRARPRESTAMO", response.errorBody().toString())
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.i("BORRARPRESTAMO", "ONFAILURE: ${t.message}")
                }

            }
        )
    }*/

}
