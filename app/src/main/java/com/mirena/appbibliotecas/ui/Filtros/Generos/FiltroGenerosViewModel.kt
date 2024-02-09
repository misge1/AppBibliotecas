package com.mirena.appbibliotecas.ui.Filtros.Generos

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.mirena.appbibliotecas.SessionManager
import com.mirena.appbibliotecas.objects.Generos
import com.mirena.appbibliotecas.objects.LibroPre
import com.mirena.appbibliotecas.retrofit.RetrofitRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class FiltroGenerosViewModel(application: Application): AndroidViewModel(application)  {

    private lateinit var retrofitRepository: RetrofitRepository
    private lateinit var generoslivedata: LiveData<List<Generos>>
    private lateinit var libroslivedata: LiveData<List<LibroPre>>

    init {
        retrofitRepository = RetrofitRepository(application.applicationContext)
        generoslivedata = retrofitRepository.getGenerosLivedata()
        libroslivedata = retrofitRepository.getLibrosLivedata()

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
}