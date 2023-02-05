package com.mirena.appbibliotecas.ui.Prestamos.ARecoger

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.mirena.appbibliotecas.SessionManager
import com.mirena.appbibliotecas.objects.PrestamoUsuario
import com.mirena.appbibliotecas.retrofit.RetrofitRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ARecogerViewModel(application: Application): AndroidViewModel(application) {
    lateinit var sessionManager: SessionManager
    private lateinit var retrofitRepository: RetrofitRepository
    private lateinit var prestamosLiveData: LiveData<List<PrestamoUsuario>>

    init {
        sessionManager = SessionManager(application.applicationContext)
        retrofitRepository = RetrofitRepository(application.applicationContext)
        prestamosLiveData = retrofitRepository.getPRecogerLivedata()
    }


    fun getARecoger(){
        viewModelScope.launch {
            retrofitRepository.getPrestamosRecoger()
        }
    }

    fun getPRecogerLD(): Flow<List<PrestamoUsuario>> {
        return prestamosLiveData.asFlow()

    }
}