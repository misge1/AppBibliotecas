package com.mirena.appbibliotecas.ui.MainActivity

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.mirena.appbibliotecas.objects.Generos
import com.mirena.appbibliotecas.objects.LibroPre
import com.mirena.appbibliotecas.retrofit.RetrofitRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.Flow
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.mirena.appbibliotecas.objects.Favoritos

class ScrollingActivityViewModel(application: Application): AndroidViewModel(application) {

    private lateinit var retrofitRepository: RetrofitRepository
    private lateinit var generoslivedata: LiveData<List<Generos>>
    private lateinit var libroslivedata: LiveData<List<LibroPre>>
    private lateinit var favoritosTablaLiveData: LiveData<List<Favoritos>>

    init {
        retrofitRepository = RetrofitRepository(application.applicationContext)
        generoslivedata = retrofitRepository.getGenerosLivedata()
        libroslivedata = retrofitRepository.getLibrosLivedata()
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
     * libros random
     */

    fun getLibrosRandom(){
        viewModelScope.launch {
            retrofitRepository.getRandomLibros()
        }
    }

    fun getLibrosFlow(): Flow<List<LibroPre>> {
        return libroslivedata.asFlow()
    }
}