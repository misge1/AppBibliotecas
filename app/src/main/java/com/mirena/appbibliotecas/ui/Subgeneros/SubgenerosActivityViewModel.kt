package com.mirena.appbibliotecas.ui.Subgeneros

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.mirena.appbibliotecas.SessionManager
import com.mirena.appbibliotecas.objects.LibroPre
import com.mirena.appbibliotecas.objects.Subgeneros
import com.mirena.appbibliotecas.retrofit.RetrofitRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.Flow

class SubgenerosActivityViewModel(application: Application): AndroidViewModel(application) {
    private var retrofitRepository: RetrofitRepository
    private var librosLiveData: LiveData<List<LibroPre>>
    lateinit var sessionManager: SessionManager

    init {
        retrofitRepository = RetrofitRepository(application.applicationContext)
        librosLiveData = retrofitRepository.getLibrosSubgeneroLivedata()
    }

    fun getLibrosSubgeneros(id: Int){
        viewModelScope.launch {
            retrofitRepository.getLibrosSubgeneros(id)
        }
    }

    fun getLibrosSubgeneroFlow(): Flow<List<LibroPre>>{
        return librosLiveData.asFlow()
    }
}