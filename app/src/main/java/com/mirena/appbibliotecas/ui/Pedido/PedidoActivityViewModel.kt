package com.mirena.appbibliotecas.ui.Pedido

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.mirena.appbibliotecas.objects.Ejemplar
import com.mirena.appbibliotecas.objects.Prestamo
import com.mirena.appbibliotecas.retrofit.RetrofitInstance
import com.mirena.appbibliotecas.retrofit.RetrofitRepository
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import kotlinx.coroutines.flow.Flow
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class PedidoActivityViewModel(application: Application): AndroidViewModel(application) {

    private lateinit var retrofitRepository: RetrofitRepository
    private lateinit var ejemplarLiveData: LiveData<Ejemplar>

    init {
        retrofitRepository = RetrofitRepository(application.applicationContext)
        ejemplarLiveData = retrofitRepository.getEjemplaresLivedata()
    }

    fun getEjemplar(id_libro: Int, id_biblioteca:Int) {
        viewModelScope.launch {
            retrofitRepository.getEjemplares(id_libro, id_biblioteca)
        }
    }

    fun getEjemplarFlow():Flow<Ejemplar> {
        return ejemplarLiveData.asFlow()

    }

    fun crearNuevoPrestamo(prestamo: Prestamo, context: Context, contextactivity: Context){
        val call = RetrofitInstance.api.crearNuevoPrestamo(prestamo)

        call.enqueue(

            object : retrofit2.Callback<ResponseBody>{
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if (response.isSuccessful){
                        Toast.makeText(context, "Prestamo creado", Toast.LENGTH_LONG).show()
                    }
                    else{
                        Toast.makeText(context, "Error onResponse: ${response.errorBody().toString()}", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Toast.makeText(context, "Error onFailure: ${t.message}", Toast.LENGTH_LONG).show()
                }

            }
        )
    }


}