package com.mirena.appbibliotecas

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.mirena.appbibliotecas.adapters.AdapterEjemplares
import com.mirena.appbibliotecas.adapters.AdapterGeneros
import com.mirena.appbibliotecas.objects.Biblioteca
import com.mirena.appbibliotecas.objects.Prestamo
import com.mirena.appbibliotecas.retrofit.RetrofitInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class PedidoActivity : AppCompatActivity() {
    private lateinit var materialDialog: MaterialAlertDialogBuilder
    private lateinit var adapterEjemplares: AdapterEjemplares
    private lateinit var context: Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pedido)

        context = this
        var id_libro = intent.getIntExtra("id_libro", 0)

        CoroutineScope(Dispatchers.IO).launch {
            val calldisponibilidad = RetrofitInstance.api.getDisponibilidad(id_libro)
            var listaBiblios = listOf<Biblioteca>()

            runOnUiThread{

                var listalibros = ArrayList<String>()

                if (calldisponibilidad.isSuccessful){
                    calldisponibilidad.body().let {
                        if (it != null){
                            listaBiblios = it
                            val mrecyclerview =
                                findViewById<RecyclerView>(R.id.recyclerview_prestamos)
                            mrecyclerview.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                            adapterEjemplares = AdapterEjemplares(context, listaBiblios)
                            mrecyclerview.adapter = adapterEjemplares
                        }
                    }
                }
            }
        }

        /*materialDialog = MaterialAlertDialogBuilder(context)
            .setTitle(resources.getString(R.string.bibliotecas))
            .setSingleChoiceItems(arrayLibros, checkedItem){ dialog, which ->

            }
            .setNeutralButton("cerrar") { dialog, which ->
                dialog.cancel()
            }
            .setPositiveButton("Pedir"){ dialog, which ->

            }*/
    }



    //@SuppressLint("SimpleDateFormat")
    /*fun crearPrestamo(){

        val calendarinst = Calendar.getInstance()
        val calendarinstDev = Calendar.getInstance()
        val time = calendarinst.time
        val formatter = SimpleDateFormat("dd-mm-yy")
        val current = formatter.format(time)

        val devolucion = calendarinstDev.add(Calendar.MONTH, 1)
        val devolucionTime: Date = calendarinstDev.time
        val devolucionFecha = formatter.format(devolucionTime)

        val prestamo = Prestamo(null,sessionManager.fetchAuthToken(), current, devolucionFecha,  )

    }*/




}