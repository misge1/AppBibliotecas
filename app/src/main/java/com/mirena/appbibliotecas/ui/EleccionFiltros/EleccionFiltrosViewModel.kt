package com.mirena.appbibliotecas.ui.EleccionFiltros

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.mirena.appbibliotecas.SessionManager
import com.mirena.appbibliotecas.objects.*
import com.mirena.appbibliotecas.retrofit.RetrofitRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.StringTokenizer

class EleccionFiltrosViewModel(application: Application): AndroidViewModel(application) {

    private var retrofitRepository: RetrofitRepository
    private var generoslivedata: LiveData<List<Generos>>
    private var subgeneroslivedata: LiveData<List<Subgeneros>>
    private var bibliotecaslivedata: LiveData<List<Biblioteca>>
    private var idiomaslivedata: LiveData<List<Idioma>>
    private var subgenerosByGenerolivedata: LiveData<List<Subgeneros>>
    lateinit var sessionManager: SessionManager

    init {
        retrofitRepository = RetrofitRepository(application.applicationContext)
        generoslivedata = retrofitRepository.getGenerosLivedata()
        subgeneroslivedata = retrofitRepository.getAllSubgenerosLivedata()
        bibliotecaslivedata = retrofitRepository.getBibliotecasLivedata()
        idiomaslivedata = retrofitRepository.getIdiomasLiveData()
        subgenerosByGenerolivedata = retrofitRepository.getSubGenerosLivedata()
        sessionManager = SessionManager(application.applicationContext)

    }

    /**
     * generos
     */

    fun getGeneros(){
        viewModelScope.launch {
            retrofitRepository.getGeneros()
        }
    }

    fun getgenerosflow(): Flow<List<Generos>> {
        return generoslivedata.asFlow()
    }

    /**
     * idiomas
     */

    fun getIdiomas(){
        viewModelScope.launch {
            retrofitRepository.getIdiomas()
        }
    }

    fun getIdiomasFlow(): Flow<List<Idioma>>{
        return idiomaslivedata.asFlow()
    }

    /**
     * subgeneros
     */

    fun getSubGeneros(){
        viewModelScope.launch {
            retrofitRepository.getAllSubgeneros()
        }
    }

    fun getSubGenerosflow(): Flow<List<Subgeneros>> {
        return subgeneroslivedata.asFlow()
    }

    fun getSubGenerosByGenero(idGenero: Int){
        viewModelScope.launch {
            retrofitRepository.getSubGeneros(idGenero)
        }
    }

    fun getSubGenerosByGeneroflow(): Flow<List<Subgeneros>> {
        return subgenerosByGenerolivedata.asFlow()
    }







    /**
     * Bibliotecas
     */

    fun getBibliotecas(){
        viewModelScope.launch {
            retrofitRepository.getBibliotecas()
        }
    }

    fun getBibliotecasFlow(): Flow<List<Biblioteca>> {
        return bibliotecaslivedata.asFlow()
    }

    /**
     * INFORMACION GUARDADA EN SESIÓN
     */

    fun getFiltroSubgenerosSession(): ArrayList<Int>{
        val stringSubgeneros = sessionManager.fetchFiltroSubgeneros()
        val st = StringTokenizer(stringSubgeneros, ",")
        val savedList = arrayListOf<Int>()
        while(st.hasMoreTokens()){
            savedList.add(st.nextToken().toInt())
        }

        return savedList
    }

    fun getFiltroBibliotecasSession(): ArrayList<Int>{
        val stringBibliotecas = sessionManager.fetchFiltroBibliotecas()
        val st = StringTokenizer(stringBibliotecas, ",")
        val savedList = arrayListOf<Int>()
        while(st.hasMoreTokens()){
            savedList.add(st.nextToken().toInt())
        }

        return savedList
    }

    fun getFiltroIdiomasSession(): ArrayList<Int>{
        val stringIdiomas = sessionManager.fetchFiltroIdiomas()
        val st = StringTokenizer(stringIdiomas, ",")
        val savedList = arrayListOf<Int>()
        while(st.hasMoreTokens()){
            savedList.add(st.nextToken().toInt())
        }

        return savedList
    }

    fun getFiltroDisponibles(): Int{
        return sessionManager.fetchDisponibles()

    }

    /**
     * Eliminar sharedpreferences
     */

    fun deleteFiltroBiblio(){
        sessionManager.deleteBibliotecas()
    }

    fun deleteFiltroSubgeneros(arraylistSubgeneros: ArrayList<Int>){
        sessionManager.deleteSubgeneros(arraylistSubgeneros)
    }

    fun deleteFiltroIdioma(){
        sessionManager.deleteIdiomas()
    }

    /**
     * save filtros sharedPreferences
     */
    fun saveFiltroSubgenero(arraylist: ArrayList<Int>?){
        if (arraylist != null) {
            sessionManager.filtrarSubgeneros(arraylist)
        }
    }

    fun saveFiltroBibliotecas(arraylist: ArrayList<Int>){
        sessionManager.filtrarBibliotecas(arraylist)
    }

    fun saveFiltroIdiomas(arraylist: ArrayList<Int>){
        sessionManager.filtrarIdiomas(arraylist)
    }

    fun saveFiltroDisponibles(disponibles: Int){
        sessionManager.filtrarDisponibles(disponibles)
    }



}