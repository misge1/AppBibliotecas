package com.mirena.appbibliotecas.ui.Login

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import com.mirena.appbibliotecas.SessionManager
import com.mirena.appbibliotecas.ui.Account.AccountActivity
import com.mirena.appbibliotecas.objects.Usuario
import com.mirena.appbibliotecas.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Response

class LoginActivityViewModel(application: Application): AndroidViewModel(application){

    lateinit var sessionManager: SessionManager
    init {
         sessionManager = SessionManager(application.applicationContext)
    }


    fun login(email: String, pass: String, context: Context, contextactivity: Context){

        val call = RetrofitInstance.api.login(email, pass)

           call.enqueue(
               object : retrofit2.Callback<Usuario> {

                   override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {

                       if (response.isSuccessful) {

                           response.body().let {
                                sessionManager.saveAuthToken(it!!.id)
                           }
                           Toast.makeText(context,"Sesión iniciada", Toast.LENGTH_LONG).show()
                           (contextactivity as Activity).finish()
                           val intent = Intent(contextactivity, AccountActivity::class.java)
                           (contextactivity as Activity).startActivity(intent)
                       } else {

                           Toast.makeText(context,"onResponse ${response.errorBody().toString()}", Toast.LENGTH_LONG).show()

                       }
                   }

                   override fun onFailure(call: Call<Usuario>, t: Throwable) {
                       Toast.makeText(context,"onFailure $t", Toast.LENGTH_LONG).show()
                   }
               }
            )
        }

}