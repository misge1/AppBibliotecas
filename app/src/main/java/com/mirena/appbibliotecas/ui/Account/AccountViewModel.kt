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
import com.mirena.appbibliotecas.objects.BibliotecaPersonal
import com.mirena.appbibliotecas.objects.IdUsuario

class AccountViewModel(application: Application): AndroidViewModel(application) {

    lateinit var sessionManager: SessionManager
    private lateinit var retrofitRepository: RetrofitRepository
    private lateinit var usuariolivedata: LiveData<Usuario>
    private lateinit var bibliotecasPersonalesLd: LiveData<List<BibliotecaPersonal>>

    init {
        sessionManager = SessionManager(application.applicationContext)
        retrofitRepository = RetrofitRepository(application.applicationContext)
        usuariolivedata = retrofitRepository.getUsuarioLivedata()
        bibliotecasPersonalesLd = retrofitRepository.getBibliosPersonalesLiveData()

    }

    fun getUserInfo() {
        viewModelScope.launch {
            retrofitRepository.getUsuario()
        }
    }

    fun getUserInfoWork(): Flow<Usuario> {
        return usuariolivedata.asFlow()

    }

    fun getBibliosPersonales(idUsuario: Int){
        viewModelScope.launch {
            retrofitRepository.getBibliotecasPersonales(idUsuario)
        }
    }

    fun getBibliosPersonalesFlow(): Flow<List<BibliotecaPersonal>>{
        return bibliotecasPersonalesLd.asFlow()
    }



}