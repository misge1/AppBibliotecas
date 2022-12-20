package com.mirena.appbibliotecas.mainActivity

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.mirena.appbibliotecas.objects.Generos
import com.mirena.appbibliotecas.retrofit.RetrofitInstance
import kotlinx.coroutines.launch

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    suspend fun getGeneros(): List<Generos>{

        var list_generos = listOf<Generos>()

            val calls = RetrofitInstance.api.getGeneros()

            if (calls.isSuccessful) {
                calls.body().let {
                    if (it != null) {
                        list_generos = it
                    }
                }
            }

        return list_generos
    }
}