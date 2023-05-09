package com.mirena.appbibliotecas.ui.ListaLibrosFiltrada

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.mirena.appbibliotecas.objects.Biblioteca
import com.mirena.appbibliotecas.objects.Generos
import com.mirena.appbibliotecas.objects.LibroObject
import com.mirena.appbibliotecas.objects.LibroPre
import com.mirena.appbibliotecas.retrofit.RetrofitRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.Flow

class ListaFiltradaViewModel(application: Application): AndroidViewModel(application) {

    private lateinit var retrofitRepository: RetrofitRepository
    private lateinit var librosFiltradoslivedata: LiveData<List<LibroObject>>

    init {
        retrofitRepository = RetrofitRepository(application.applicationContext)
        librosFiltradoslivedata = retrofitRepository.getListaFiltradaLiveData()

    }

    fun getFiltroSubgBiblioteca(subgenero: String, biblioteca: String){
        viewModelScope.launch {
            retrofitRepository.getListaFiltrada(subgenero, biblioteca)
        }
    }

    fun getFiltroSubgBibliotecaFlow(): Flow<List<LibroObject>>{
        return librosFiltradoslivedata.asFlow()
    }
}