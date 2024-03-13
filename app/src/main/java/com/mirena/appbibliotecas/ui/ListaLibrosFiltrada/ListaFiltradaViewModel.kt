package com.mirena.appbibliotecas.ui.ListaLibrosFiltrada

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.mirena.appbibliotecas.SessionManager
import com.mirena.appbibliotecas.objects.Favoritos
import com.mirena.appbibliotecas.objects.LibroPre
import com.mirena.appbibliotecas.retrofit.RetrofitRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.StringTokenizer

class ListaFiltradaViewModel(application: Application): AndroidViewModel(application) {

    private lateinit var retrofitRepository: RetrofitRepository
    private lateinit var librosFiltradoslivedata: LiveData<List<LibroPre>>
    lateinit var sessionManager: SessionManager
    private lateinit var favoritosTablaLiveData: LiveData<List<Favoritos>>
    init {
        sessionManager = SessionManager(application.applicationContext)
        retrofitRepository = RetrofitRepository(application.applicationContext)
        librosFiltradoslivedata = retrofitRepository.getListaFiltradaLiveData()
        favoritosTablaLiveData = retrofitRepository.getListaFavoritosTablaLD()
    }

    fun getFavoritosTabla(idUsuario: Int){
        viewModelScope.launch {
            retrofitRepository.getListaFavoritosTabla(idUsuario)
        }
    }

    fun getFavoritosTablaFlow(): Flow<List<Favoritos>>{
        return favoritosTablaLiveData.asFlow()
    }

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

    fun getLibrosFiltrados(arrayIdiomas: ArrayList<Int>, arrayBibliotecas: ArrayList<Int>, arraySubgeneros: ArrayList<Int>, disponiblidad: Int){
        viewModelScope.launch {
            retrofitRepository.getListaFiltrada(arrayIdiomas, arrayBibliotecas, arraySubgeneros, disponiblidad )
        }
    }

    fun getLibrosFiltradosLivedata(): Flow<List<LibroPre>>{
        return librosFiltradoslivedata.asFlow()

    }

    fun deleteFiltros(){
        sessionManager.deleteFiltros()
    }

    fun getAuthToken(): Int{
        return sessionManager.fetchAuthToken()
    }





}