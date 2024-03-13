package com.mirena.appbibliotecas.ui.Libro

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.mirena.appbibliotecas.ui.Login.LoginActivity
import com.mirena.appbibliotecas.SessionManager
import com.mirena.appbibliotecas.objects.Biblioteca
import com.mirena.appbibliotecas.objects.Favoritos
import com.mirena.appbibliotecas.retrofit.RetrofitInstance
import com.mirena.appbibliotecas.retrofit.RetrofitRepository
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import kotlinx.coroutines.flow.Flow
import androidx.lifecycle.asFlow
import com.mirena.appbibliotecas.objects.Comentario
import com.mirena.appbibliotecas.objects.ComentarioSave
import com.mirena.appbibliotecas.objects.DispositivosUsuarios
import com.mirena.appbibliotecas.objects.Subgeneros

class LibroActivity2ViewModel(application: Application): AndroidViewModel(application) {

    var sessionManager: SessionManager
    private  var retrofitRepository: RetrofitRepository
    private  var disponibilidadlivedata: LiveData<List<Biblioteca>>
    private  var comprobarfavs: LiveData<String>
    private  var subgenerosLibro: LiveData<List<Subgeneros>>
    private  var comentariosLibro: LiveData<List<Comentario>>

    init {
        sessionManager = SessionManager(application.applicationContext)
        retrofitRepository = RetrofitRepository(application.applicationContext)
        disponibilidadlivedata = retrofitRepository.getDisponibilidadLiveData()
        comprobarfavs = retrofitRepository.getComprobacionfavsLd()
        subgenerosLibro = retrofitRepository.getSubgenerosLibroLd()
        comentariosLibro = retrofitRepository.getComentariosLd()
    }


    /**
     * get Comentarios
     */

    fun getComentarios(id_libro: Int){
        viewModelScope.launch {
            retrofitRepository.getComentarios(id_libro)
        }
    }

    fun getComentariosFlow(): Flow<List<Comentario>>{
        return comentariosLibro.asFlow()
    }
    /**
     * comprobacion de que un libro está en favoritos del usuario
     */

    fun comprobarlibrofav(id_usuario: Int, id_libro: Int){
        viewModelScope.launch {
            retrofitRepository.comprobarFavs(id_libro, id_usuario)
        }
    }

    fun getComprobacion(): Flow<String> {
        return comprobarfavs.asFlow()
    }

    fun addComentario(comentario: ComentarioSave) {
        val call = RetrofitInstance.api.addComentario(comentario)

        call.enqueue(
            object: retrofit2.Callback<ResponseBody>{
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if(response.isSuccessful){
                        response.body().let {
                            Log.d("TAG ADD COMMENT", "SUCCESFUL")
                        }
                    }else{
                        Log.d("TAG ADD COMMENT", "NOT SUCCESFUL")
                    }

                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.d("TAG ADD COMMENT", t.message!!)
                }
            }
        )
    }

    /**
     * disponibilidad en bibliotecas
     */

    fun getDisponibilidad(id: Int){
        viewModelScope.launch {
            retrofitRepository.getDisponibilidad(id)
        }
    }

    fun getDisponibilidadLivedata(): Flow<List<Biblioteca>>{
        return disponibilidadlivedata.asFlow()
    }

    /**
     * Subgéneros del libro
     */

    fun getSubgenerosLibro(id: Int){
        viewModelScope.launch {
            retrofitRepository.getSubgenerosLibro(id)
        }
    }

    fun getSubgenerosLibroLd(): Flow<List<Subgeneros>>{
        return subgenerosLibro.asFlow()
    }

    /**
     * Añadir un libro a favoritos
     */

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

    /**
     * borrar un libro de favoritos
     */

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