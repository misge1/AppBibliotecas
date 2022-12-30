package com.mirena.appbibliotecas.Account

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import com.mirena.appbibliotecas.SessionManager
import com.mirena.appbibliotecas.objects.Usuario
import com.mirena.appbibliotecas.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Response

class AccountViewModel(application: Application): AndroidViewModel(application) {

    lateinit var sessionManager: SessionManager

    init {
        sessionManager = SessionManager(application.applicationContext)

    }

    /*fun getUser(context: Context, contextActivity: Context): Usuario{

        val call = RetrofitInstance.api.getUser(sessionManager.fetchAuthToken())
        var usuario = Usuario()
        call.enqueue(
            object : retrofit2.Callback<Usuario> {
                override fun onFailure(call: Call<Usuario>, t: Throwable) {
                    Toast.makeText(contextActivity, "Ha habido problemas ${t.message}", Toast.LENGTH_LONG).show()
                }

                override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {

                    if (response.isSuccessful){
                        response.body().let {
                            if (it!=null){
                                usuario = it
                            }
                        }
                    }else {
                        Toast.makeText(context, "Ha habido problemas ${response.errorBody().toString()}o", Toast.LENGTH_LONG).show()
                    }
                }
            }
        )

        return usuario


    }*/




}