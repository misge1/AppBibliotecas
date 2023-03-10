package com.mirena.appbibliotecas.ui.Search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.mirena.appbibliotecas.objects.Generos
import com.mirena.appbibliotecas.objects.LibroPre
import com.mirena.appbibliotecas.retrofit.RetrofitRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class SearchActivityViewModel(application: Application): AndroidViewModel(application) {
    private lateinit var retrofitRepository: RetrofitRepository
    private lateinit var libroslivedata: LiveData<List<LibroPre>>

    init {
        retrofitRepository = RetrofitRepository(application.applicationContext)
        libroslivedata = retrofitRepository.getAllLibrosLiveData()

    }

    fun getLibros(){
        viewModelScope.launch {
            retrofitRepository.getAllLibros()
        }
    }

    fun getLibrosFlow(): Flow<List<LibroPre>> {
        return libroslivedata.asFlow()
    }
}