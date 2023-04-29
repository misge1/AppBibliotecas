package com.mirena.appbibliotecas.ui.prestamos.EnCurso

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

class EnCursoViewModel(application: Application): AndroidViewModel(application) {
    lateinit var sessionManager: SessionManager
    private lateinit var retrofitRepository: RetrofitRepository
    private lateinit var prestamosLiveData: LiveData<List<PrestamoUsuario>>

    init {
        sessionManager = SessionManager(application.applicationContext)
        retrofitRepository = RetrofitRepository(application.applicationContext)
        prestamosLiveData = retrofitRepository.getPCursoLivedata()
    }


    fun getCurso(){
        viewModelScope.launch {
            retrofitRepository.getPrestamosCurso()
        }
    }

    fun getPCursoLD(): Flow<List<PrestamoUsuario>> {
        return prestamosLiveData.asFlow()

    }
}