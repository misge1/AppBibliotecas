package com.mirena.appbibliotecas.retrofit

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mirena.appbibliotecas.SessionManager
import com.mirena.appbibliotecas.objects.*

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


    /**
     * LISTA DE LIBROS FILTRADA
     */

    suspend fun getListaFiltrada(subgenero: String, biblioteca: String){
        val call = RetrofitInstance.api.filtroBibloSubg(subgenero, biblioteca)
        val body = call.body()

        if (call.isSuccessful){
            body.let {
                listaFiltradaSubBiblio.postValue(it)
            }
        }
    }

    fun getListaFiltradaLiveData(): LiveData<List<LibroObject>>{
        return listaFiltradaSubBiblio
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

}
