package com.mirena.appbibliotecas.ui.Account

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.mirena.appbibliotecas.SessionManager

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