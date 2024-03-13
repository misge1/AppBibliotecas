package com.mirena.appbibliotecas.ui.nuevaBiblioteca

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.mirena.appbibliotecas.objects.BibliotecaPersonal
import com.mirena.appbibliotecas.retrofit.RetrofitInstance
import com.mirena.appbibliotecas.retrofit.RetrofitRepository
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response

class AnyadirBibliotecaViewModel (application: Application): AndroidViewModel(application){
    private lateinit var retrofitRepository: RetrofitRepository

    init {
        retrofitRepository = RetrofitRepository(application.applicationContext)
    }


    fun addBibliotecaPersonal (bibliotecaPersonal: BibliotecaPersonal){
        val call = RetrofitInstance.api.addBiblioteca(bibliotecaPersonal)

        call.enqueue(
            object: retrofit2.Callback<ResponseBody>{
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if(response.isSuccessful){
                        response.body().let {
                            Log.d("TAG ADD BIBLIOTECA", "SUCCESFUL")
                        }
                    }else{
                        Log.d("TAG ADD BIBLIOTECA", "NOT SUCCESFUL")
                    }

                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.d("TAG ADD BIBLIOTECA", t.message!!)
                }
            }
        )
    }
}