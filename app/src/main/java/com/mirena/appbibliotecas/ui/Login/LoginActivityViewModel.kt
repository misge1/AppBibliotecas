package com.mirena.appbibliotecas.ui.Login

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.mirena.appbibliotecas.SessionManager
import com.mirena.appbibliotecas.objects.DispositivosUsuarios
import com.mirena.appbibliotecas.ui.Account.AccountActivity
import com.mirena.appbibliotecas.objects.Usuario
import com.mirena.appbibliotecas.retrofit.RetrofitInstance
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response

class LoginActivityViewModel(application: Application): AndroidViewModel(application){

    lateinit var sessionManager: SessionManager
    init {
         sessionManager = SessionManager(application.applicationContext)
    }

    fun saveTokenDispoUsuario(id_usuario: Int ) {
        val token = sessionManager.fetchDispoToken()
        val dispositivosUsuarios = DispositivosUsuarios(token, id_usuario)
        val call = RetrofitInstance.api.saveDispositivoToken(dispositivosUsuarios)

        call.enqueue(
            object: retrofit2.Callback<ResponseBody>{
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if(response.isSuccessful){
                        response.body().let {
                            Log.d("TAG SAVE TOKEN", "SUCCESFUL")
                        }
                    }else{
                        Log.d("TAG SAVE TOKEN", "NOT SUCCESFUL")
                    }

                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.d("TAG SAVE TOKEN", t.message!!)
                }
            }
        )
    }


    fun login(email: String, pass: String, context: Context, contextactivity: Context){

        val call = RetrofitInstance.api.login(email, pass)

           call.enqueue(
               object : retrofit2.Callback<Usuario> {

                   override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {

                       if (response.isSuccessful) {

                           response.body().let {
                                sessionManager.saveAuthToken(it!!.id)
                               saveTokenDispoUsuario(it!!.id)


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