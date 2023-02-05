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
}