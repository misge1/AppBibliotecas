package com.mirena.appbibliotecas.ui.Pedido

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.mirena.appbibliotecas.R
import com.mirena.appbibliotecas.SessionManager
import com.mirena.appbibliotecas.adapters.AdapterEjemplares
import com.mirena.appbibliotecas.databinding.ActivityPedidoBinding
import com.mirena.appbibliotecas.objects.Ejemplar
import com.mirena.appbibliotecas.objects.Prestamo
import com.mirena.appbibliotecas.retrofit.RetrofitInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class PedidoActivity : AppCompatActivity() {
    private lateinit var materialDialog: MaterialAlertDialogBuilder
    private lateinit var adapterEjemplares: AdapterEjemplares
    private lateinit var context: Context
    private lateinit var binding: ActivityPedidoBinding
    private lateinit var textviewTitulo: TextView
    private lateinit var textviewBiblioteca: TextView
    private lateinit var textViewFechaRecogida: TextView
    private lateinit var textViewFechaDevolucion: TextView
    private lateinit var botonFinalizar: Button
    private lateinit var sessionManager: SessionManager
    private lateinit var ejemplar: Ejemplar
    private lateinit var pedidoActivityViewModel: PedidoActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pedido)
        binding = ActivityPedidoBinding.inflate(layoutInflater)
        setContentView(binding.root)


        pedidoActivityViewModel = ViewModelProvider(this).get(
            PedidoActivityViewModel::class.java
        )
        binding.toolbar.title = "Resumen de pedido"
        sessionManager = SessionManager(this)
        botonFinalizar = binding.buttonFinalizarPedido


        textviewTitulo = binding.layoutPedido.textviewTituloPedido
        textviewBiblioteca = binding.layoutPedido.textviewBibliotecaPedido
        textViewFechaRecogida = binding.layoutPedido.textviewFechaRecogida
        textViewFechaDevolucion = binding.layoutPedido.textviewFechaDevolucion
        textViewFechaRecogida.text = sacarFechaRecogida()
        textViewFechaDevolucion.text = sacarFechaDevolucion()


        context = this
        var id_biblioteca = intent.getIntExtra("id_biblioteca", 0)
        var id_libro = intent.getIntExtra("id_libro", 0)

        CoroutineScope(Dispatchers.IO).launch {
            val calldisponibilidad = RetrofitInstance.api.getEjemplar(id_libro, id_biblioteca)

            runOnUiThread{
                if (calldisponibilidad.isSuccessful){
                    calldisponibilidad.body().let {
                        if (it != null){
                            ejemplar = it
                            textviewTitulo.text=ejemplar.libro
                            textviewBiblioteca.text = ejemplar.biblioteca
                        }
                    }
                }
            }
        }

        botonFinalizar.setOnClickListener {
            var prestamo = crearPrestamo(textViewFechaRecogida.text.toString(), textViewFechaDevolucion.text.toString(), ejemplar.id_ejemplar, id_libro)
            pedidoActivityViewModel.crearNuevoPrestamo(prestamo, applicationContext, this)
        }



    }



    @SuppressLint("SimpleDateFormat")
    fun crearPrestamo(fecharecogida: String, fechaDevolucion: String, id_ejemplar: Int, id_libro: Int): Prestamo{

        val calendarinst = Calendar.getInstance()
        val calendarinstDev = Calendar.getInstance()
        val time = calendarinst.time
        val formatter = SimpleDateFormat("dd-MM-yyyy")
        val current = formatter.format(time)

        return Prestamo(null,sessionManager.fetchAuthToken(), current, fecharecogida, fechaDevolucion, id_ejemplar, id_libro, 1  )
    }

    fun sacarFechaRecogida(): String{
        val calendarinst = Calendar.getInstance()
        val calendarinstDev = Calendar.getInstance()
        val time = calendarinst.time
        val formatter = SimpleDateFormat("dd-MM-yyyy")
        val current = formatter.format(time)

        val devolucion = calendarinstDev.add(Calendar.DAY_OF_MONTH, 3)
        val devolucionTime: Date = calendarinstDev.time
        val devolucionFecha = formatter.format(devolucionTime)

        return devolucionFecha

    }

    fun sacarFechaDevolucion(): String{
        val calendarinst = Calendar.getInstance()
        val calendarinstDev = Calendar.getInstance()
        val time = calendarinst.time
        val formatter = SimpleDateFormat("dd-MM-yyyy")
        val current = formatter.format(time)

        val devolucion = calendarinstDev.add(Calendar.MONTH, 1)
        val devolucionTime: Date = calendarinstDev.time
        val devolucionFecha = formatter.format(devolucionTime)

        return devolucionFecha

    }





}