package com.mirena.appbibliotecas.ui.Libro

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import com.mirena.appbibliotecas.ui.Login.LoginActivity
import com.mirena.appbibliotecas.SessionManager
import com.mirena.appbibliotecas.objects.Favoritos
import com.mirena.appbibliotecas.retrofit.RetrofitInstance
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response

class LibroActivity2ViewModel(application: Application): AndroidViewModel(application) {

    lateinit var sessionManager: SessionManager

    init {
        sessionManager = SessionManager(application.applicationContext)
    }

    fun addFavoritos(favoritos: Favoritos, context: Context, contextActivity: Context){

        if (sessionManager.fetchAuthToken()==0){
            val intent = Intent(contextActivity, LoginActivity::class.java)
            (contextActivity as Activity).startActivity(intent)
        }else{

            val call = RetrofitInstance.api.addFavorito(favoritos)

            call.enqueue(
                object : retrofit2.Callback<ResponseBody> {
                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        if (response.isSuccessful){
                            Toast.makeText(context,"Añadido a favoritos", Toast.LENGTH_LONG).show()

                        }else if (response.code() == 404){
                            Toast.makeText(context, "Ya está en favs", Toast.LENGTH_LONG).show()
                        }
                        else{
                            Toast.makeText(context,"onResponse ${response.errorBody().toString()}", Toast.LENGTH_LONG).show()
                        }


                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Toast.makeText(context,t.message.toString(), Toast.LENGTH_LONG).show()
                    }

                }
            )

        }

    }

    fun deletefavoritos(id_libro: Int, id_usuario: Int ,context: Context, contextActivity: Context ){
        if (sessionManager.fetchAuthToken()==0){
            val intent = Intent(contextActivity, LoginActivity::class.java)
            (contextActivity as Activity).startActivity(intent)
        }else{

            val call = RetrofitInstance.api.deleteFavorito(id_libro, id_usuario)

            call.enqueue(
                object : retrofit2.Callback<ResponseBody> {
                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        if (response.isSuccessful) {
                            Toast.makeText(context, "Borrado de favs", Toast.LENGTH_LONG).show()

                        }else{
                            Toast.makeText(context,"onResponse ${response.errorBody().toString()}", Toast.LENGTH_LONG).show()
                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Toast.makeText(context,t.message.toString(), Toast.LENGTH_LONG).show()
                    }

                }
            )

        }
    }
}