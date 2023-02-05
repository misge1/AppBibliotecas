package com.mirena.appbibliotecas.ui.SplashScreen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.mirena.appbibliotecas.SessionManager
import com.mirena.appbibliotecas.objects.Generos
import com.mirena.appbibliotecas.objects.LibroPre
import com.mirena.appbibliotecas.retrofit.RetrofitRepository
import kotlinx.coroutines.launch

class SplashScreenViewModel(application: Application): AndroidViewModel(application) {
    private lateinit var sessionManager: SessionManager
    private lateinit var retrofitrepository: RetrofitRepository
    private lateinit var generoslivedata: LiveData<List<Generos>>
    private lateinit var libroslivedata: LiveData<List<LibroPre>>

    init {
        sessionManager = SessionManager(application.applicationContext)
        retrofitrepository = RetrofitRepository(application.applicationContext)
        generoslivedata = retrofitrepository.getGenerosLivedata()
        libroslivedata = retrofitrepository.getLibrosLivedata()


    }

    fun getGeneros(){
        viewModelScope.launch {
            retrofitrepository.getGeneros()
        }
    }

    fun getLibrosRandom(){
        viewModelScope.launch {
            retrofitrepository.getRandomLibros()
        }
    }



    fun getFavoritos(){
        viewModelScope.launch {
            retrofitrepository.getFavoritos()
        }
    }



}