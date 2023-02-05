package com.mirena.appbibliotecas.ui.Prestamos.Adevolver

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.mirena.appbibliotecas.SessionManager
import com.mirena.appbibliotecas.objects.PrestamoUsuario
import com.mirena.appbibliotecas.retrofit.RetrofitRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.Flow
import androidx.lifecycle.asFlow

class AdevolverViewModel(application: Application): AndroidViewModel(application) {

    lateinit var sessionManager: SessionManager
    private lateinit var retrofitRepository: RetrofitRepository
    private lateinit var prestamosLiveData: LiveData<List<PrestamoUsuario>>

    init {
        sessionManager = SessionManager(application.applicationContext)
        retrofitRepository = RetrofitRepository(application.applicationContext)
        prestamosLiveData = retrofitRepository.getPDevolverLivedata()
    }


    fun getPDevolver(){
        viewModelScope.launch {
            retrofitRepository.getPrestamosDevolver()
        }
    }

    fun getPDevolverLD(): Flow<List<PrestamoUsuario>>{
        return prestamosLiveData.asFlow()

    }

}