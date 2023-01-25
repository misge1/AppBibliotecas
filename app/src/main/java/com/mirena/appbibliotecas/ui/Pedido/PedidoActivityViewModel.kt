package com.mirena.appbibliotecas.ui.Pedido

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import com.mirena.appbibliotecas.objects.Prestamo
import com.mirena.appbibliotecas.retrofit.RetrofitInstance
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response

class PedidoActivityViewModel(application: Application): AndroidViewModel(application) {

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