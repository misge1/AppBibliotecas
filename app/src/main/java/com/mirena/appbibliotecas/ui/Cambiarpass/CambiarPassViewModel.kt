package com.mirena.appbibliotecas.ui.Cambiarpass

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.mirena.appbibliotecas.SessionManager
import com.mirena.appbibliotecas.databinding.ActivityCambiarPassBinding
import com.mirena.appbibliotecas.retrofit.RetrofitInstance
import com.mirena.appbibliotecas.retrofit.RetrofitRepository
import com.mirena.appbibliotecas.ui.Ajustes.AjustesActivity
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response

class CambiarPassViewModel(application: Application): AndroidViewModel(application){
    lateinit var sessionManager: SessionManager
    private lateinit var retrofitRepository: RetrofitRepository

   init {
       sessionManager = SessionManager(application.applicationContext)
   }

    fun changePass(newpass: String, context: Context, contextActivity: Context){
            val call = RetrofitInstance.api.cambiarPass(sessionManager.fetchAuthToken(), newpass)

            call.enqueue(
                object : retrofit2.Callback<ResponseBody>{
                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        if (response.isSuccessful){
                            Toast.makeText(context, "Contraseña actualizada", Toast.LENGTH_LONG)
                                .show()
                            (contextActivity as Activity).finish()
                            val intent = Intent(contextActivity, AjustesActivity::class.java)
                            (contextActivity as Activity).startActivity(intent)
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