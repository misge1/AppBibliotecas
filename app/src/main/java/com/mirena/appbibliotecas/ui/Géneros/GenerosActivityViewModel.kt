package com.mirena.appbibliotecas.ui.Géneros

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.mirena.appbibliotecas.SessionManager
import com.mirena.appbibliotecas.objects.Favoritos
import com.mirena.appbibliotecas.objects.Subgeneros
import com.mirena.appbibliotecas.retrofit.RetrofitRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.Flow

class GenerosActivityViewModel(application: Application): AndroidViewModel(application) {
    private var retrofitRepository: RetrofitRepository
    private var subgeneroslivedata: LiveData<List<Subgeneros>>
    lateinit var sessionManager: SessionManager
    private var favoritosTablaLiveData: LiveData<List<Favoritos>>

    init {
        retrofitRepository = RetrofitRepository(application.applicationContext)
        subgeneroslivedata = retrofitRepository.getSubGenerosLivedata()
        favoritosTablaLiveData = retrofitRepository.getListaFavoritosTablaLD()
    }
    fun getFavoritosTabla(idUsuario: Int){
        viewModelScope.launch {
            retrofitRepository.getListaFavoritosTabla(idUsuario)
        }
    }

    fun getFavoritosTablaFlow(): Flow<List<Favoritos>>{
        return favoritosTablaLiveData.asFlow()
    }

    /**
     * subgeneros
     */

    fun getSubgeneros(genero: Int){
        viewModelScope.launch {
            retrofitRepository.getSubGeneros(genero)
        }
    }

    fun getSubgenerosFlow(): Flow<List<Subgeneros>>{
        return subgeneroslivedata.asFlow()

    }

}