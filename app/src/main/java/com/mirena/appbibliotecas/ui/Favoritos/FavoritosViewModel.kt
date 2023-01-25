package com.mirena.appbibliotecas.ui.Favoritos

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.mirena.appbibliotecas.SessionManager
import com.mirena.appbibliotecas.objects.LibroPre
import com.mirena.appbibliotecas.retrofit.RetrofitInstance
import kotlinx.coroutines.launch

class FavoritosViewModel(application: Application): AndroidViewModel(application) {

    private lateinit var sessionManager: SessionManager

    init {
        sessionManager = SessionManager(application.applicationContext)
    }

    fun getFavoritos(): List<LibroPre>{
        var listafavoritos = listOf<LibroPre>()
        viewModelScope.launch {
            val call = RetrofitInstance.api.getFavoritos(sessionManager.fetchAuthToken())
            var body = call.body()

            if(call.isSuccessful){
              body.let {
                  if (it!=null){
                      listafavoritos = it
                  }
              }
            }
        }

        return  listafavoritos
    }
}