package com.mirena.appbibliotecas.ui.Filtros

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.mirena.appbibliotecas.SessionManager
import com.mirena.appbibliotecas.objects.Biblioteca
import com.mirena.appbibliotecas.objects.Subgeneros
import com.mirena.appbibliotecas.retrofit.RetrofitRepository

class FiltrosViewModel (application: Application): AndroidViewModel(application) {

    lateinit var sessionManager: SessionManager
    private lateinit var retrofitRepository: RetrofitRepository


    init {
        sessionManager = SessionManager(application.applicationContext)
        retrofitRepository = RetrofitRepository(application.applicationContext)

    }

    fun saveFiltroSubgenero(arraylist: ArrayList<Int>?){
        if (arraylist != null) {
            sessionManager.filtrarSubgeneros(arraylist)
        }
    }

    fun saveFiltroBibliotecas(arraylist: ArrayList<Int>){
        sessionManager.filtrarBibliotecas(arraylist)
    }

    fun saveFiltroIdiomas(arraylist: ArrayList<Int>){
        sessionManager.filtrarIdiomas(arraylist)
    }

    fun saveFiltroDisponibles(disponible: Int){
        sessionManager.filtrarDisponibles(disponible)
    }

    fun getFiltroDisponibles(): Int {
       return sessionManager.fetchDisponibles()
    }

    fun getAuthToken(): Int{
        return sessionManager.fetchAuthToken()
    }

}
