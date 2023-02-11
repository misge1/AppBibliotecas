package com.mirena.appbibliotecas.ui.EditarPerfil

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
import com.mirena.appbibliotecas.objects.Usuario
import com.mirena.appbibliotecas.retrofit.RetrofitInstance
import com.mirena.appbibliotecas.retrofit.RetrofitRepository
import com.mirena.appbibliotecas.ui.Account.AccountActivity
import com.mirena.appbibliotecas.ui.Ajustes.AjustesActivity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response

class EditarPerfilViewModel(application: Application): AndroidViewModel(application)  {

    lateinit var sessionManager: SessionManager
    private lateinit var retrofitRepository: RetrofitRepository
    private lateinit var usuariolivedata: LiveData<Usuario>

    init {
        sessionManager = SessionManager(application.applicationContext)
        retrofitRepository = RetrofitRepository(application.applicationContext)
        usuariolivedata = retrofitRepository.getUsuarioLivedata()

    }

    fun getUserInfo() {
        viewModelScope.launch {
            retrofitRepository.getUsuario()
        }
    }

    fun getUserInfoWork(): Flow<Usuario> {
        return usuariolivedata.asFlow()

    }


    fun updateUserInfo(nombre: String,
                       tel: String,
                       domi: String,
                       localidad: String,
                       cp: String,
                        contextActivity: Context,
                        context: Context){
        val call = RetrofitInstance.api.updateUserInfo(sessionManager.fetchAuthToken(),
            nombre,
            tel,
            domi,
            localidad,
            cp)

        call.enqueue(
            object : retrofit2.Callback<ResponseBody>{
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful){
                        Toast.makeText(context, "Información actualizada", Toast.LENGTH_LONG)
                            .show()
                        (contextActivity as Activity).finish()
                        val intent = Intent(contextActivity, AccountActivity::class.java)
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