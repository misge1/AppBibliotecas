package com.mirena.appbibliotecas.ui.EleccionFiltros

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.mirena.appbibliotecas.SessionManager
import com.mirena.appbibliotecas.objects.*
import com.mirena.appbibliotecas.retrofit.RetrofitRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class EleccionFiltrosViewModel(application: Application): AndroidViewModel(application) {

    private var retrofitRepository: RetrofitRepository
    private var generoslivedata: LiveData<List<Generos>>
    private var subgeneroslivedata: LiveData<List<Subgeneros>>
    private var bibliotecaslivedata: LiveData<List<Biblioteca>>

    init {
        retrofitRepository = RetrofitRepository(application.applicationContext)
        generoslivedata = retrofitRepository.getGenerosLivedata()
        subgeneroslivedata = retrofitRepository.getSubGenerosLivedata()
        bibliotecaslivedata = retrofitRepository.getBibliotecasLivedata()

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
     * subgeneros
     */

    fun getSubGeneros(genero: Int){
        viewModelScope.launch {
            retrofitRepository.getSubGeneros(genero)
        }
    }

    fun getSubGenerosflow(): Flow<List<Subgeneros>> {
        return subgeneroslivedata.asFlow()
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

}