package com.mirena.appbibliotecas.ui.Account

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.mirena.appbibliotecas.SessionManager
import com.mirena.appbibliotecas.objects.Usuario
import com.mirena.appbibliotecas.retrofit.RetrofitRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.Flow
import androidx.lifecycle.asFlow

class AccountViewModel(application: Application): AndroidViewModel(application) {

    lateinit var sessionManager: SessionManager
    private lateinit var retrofitRepository: RetrofitRepository
    private lateinit var usuariolivedata: LiveData<Usuario>

    init {
        sessionManager = SessionManager(application.applicationContext)
        retrofitRepository = RetrofitRepository(application.applicationContext)
        usuariolivedata = retrofitRepository.getUsuarioLivedata()

    }

    fun getUserInfo() {
        viewModelScope.launch {
            retrofitRepository.getUsuario()
        }
    }

    fun getUserInfoWork(): Flow<Usuario> {
        return usuariolivedata.asFlow()

    }



}