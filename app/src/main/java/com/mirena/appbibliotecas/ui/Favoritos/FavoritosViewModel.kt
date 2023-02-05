package com.mirena.appbibliotecas.ui.Favoritos

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.mirena.appbibliotecas.SessionManager
import com.mirena.appbibliotecas.objects.LibroPre
import com.mirena.appbibliotecas.retrofit.RetrofitInstance
import com.mirena.appbibliotecas.retrofit.RetrofitRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.Flow
import androidx.lifecycle.asFlow

class FavoritosViewModel(application: Application): AndroidViewModel(application) {

    private lateinit var sessionManager: SessionManager
    private lateinit var retrofitRepository: RetrofitRepository
    private lateinit var favortiosLiveData: LiveData<List<LibroPre>>

    init {
        sessionManager = SessionManager(application.applicationContext)
        retrofitRepository = RetrofitRepository(application.applicationContext)
        favortiosLiveData = retrofitRepository.getFavoritosLiveData()

    }

    fun getFavoritos(){
        viewModelScope.launch {
          retrofitRepository.getFavoritos()
        }
    }

    fun getFavoritosFlow(): Flow<List<LibroPre>>{
        return favortiosLiveData.asFlow()
    }
}