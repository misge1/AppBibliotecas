package com.mirena.appbibliotecas.ui.prestamos.ARecoger

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.mirena.appbibliotecas.SessionManager
import com.mirena.appbibliotecas.objects.PrestamoUsuario
import com.mirena.appbibliotecas.retrofit.RetrofitInstance
import com.mirena.appbibliotecas.retrofit.RetrofitRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response

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

    fun borrarPrestamo(idPrestamo: Int){

        val call = RetrofitInstance.api.borrarPrestamo(idPrestamo);

        call.enqueue(
            object : retrofit2.Callback<ResponseBody>{
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if(response.isSuccessful){
                        Log.i("BORRARPRESTAMO", "prestamos borrado")
                    }else{
                        Log.i("BORRARPRESTAMO", response.errorBody().toString())
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.i("BORRARPRESTAMO", "ONFAILURE: ${t.message}")
                }

            }
        )

    }
}