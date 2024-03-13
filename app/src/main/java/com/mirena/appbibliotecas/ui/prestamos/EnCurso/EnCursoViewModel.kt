package com.mirena.appbibliotecas.ui.prestamos.EnCurso

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.mirena.appbibliotecas.SessionManager
import com.mirena.appbibliotecas.objects.PrestamoUsuario
import com.mirena.appbibliotecas.retrofit.RetrofitInstance
import com.mirena.appbibliotecas.retrofit.RetrofitRepository
import com.mirena.appbibliotecas.ui.Ajustes.AjustesActivity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response

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

    fun renovarPrestamp(id: Int, fechaDevolucion: String, context: Context, contextActivity: Context){
        val call = RetrofitInstance.api.renovarPrestamo(id, fechaDevolucion)

        call.enqueue(
            object : retrofit2.Callback<ResponseBody>{
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful){
                        Toast.makeText(context, "Su prestamos ha sido renovado", Toast.LENGTH_LONG)
                            .show()
                    }else{
                        Toast.makeText(context,"onResponse ${response.errorBody().toString()}", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Toast.makeText(context,"onFailure $t", Toast.LENGTH_LONG).show()
                }

            }
        )
    }
}