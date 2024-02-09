package com.mirena.appbibliotecas.ui.prestamos.ARecoger.prestamoIndividual

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.mirena.appbibliotecas.R
import com.mirena.appbibliotecas.ui.prestamos.ARecoger.ARecogerViewModel
import com.mirena.appbibliotecas.ui.prestamos.ARecoger.PrestamosRecogerActivity
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Calendar
import java.util.Locale

class PrestamoRecogerActivity : AppCompatActivity() {
    private lateinit var cancelarButton: Button
    private lateinit var materialDialog: MaterialAlertDialogBuilder
    private lateinit var textTitutloRecoger: TextView
    private lateinit var textAutorRecoger: TextView
    private lateinit var textEditorialRecoger: TextView
    private lateinit var textIsbnRecoger: TextView
    private lateinit var textFechaRecogida: TextView
    private lateinit var imageViewLibro: ImageView
    private lateinit var nombreBiblioteca: TextView
    private lateinit var backButton: ImageView
    private lateinit var recogerViewModel: ARecogerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prestamo_recoger)

        cancelarButton = findViewById(R.id.cancelarButton)
        var id = intent.getIntExtra("id", -1)
        var titulo = intent.getStringExtra("titulo")
        var autor = intent.getStringExtra("autor")
        var editorial = intent.getStringExtra("editorial")
        var isbn = intent.getStringExtra("isbn")
        var fechaRecogida = intent.getStringExtra("fechaRecogida")
        var fechaInicio = intent.getStringExtra("fechaInicio")
        var biblioteca = intent.getStringExtra("biblioteca")
        var imagen = intent.getStringExtra("imagen")

        textAutorRecoger = findViewById(R.id.textAutorRecoger)
        textTitutloRecoger = findViewById(R.id.textTitutloRecoger)
        textEditorialRecoger = findViewById(R.id.textEditorialRecoger)
        textIsbnRecoger = findViewById(R.id.textIsbnRecoger)
        textFechaRecogida = findViewById(R.id.fechaRecogida)
        imageViewLibro = findViewById(R.id.imageview_libro)
        nombreBiblioteca = findViewById(R.id.nombreBiblioteca)
        backButton = findViewById(R.id.back_button_recoger)
        recogerViewModel = ViewModelProvider(this)[ARecogerViewModel::class.java]


        textTitutloRecoger.text = titulo
        textAutorRecoger.text = autor
        textEditorialRecoger.text = editorial
        textIsbnRecoger.text =isbn
        textFechaRecogida.text = fechaRecogida
        nombreBiblioteca.text = biblioteca

        Picasso.get()
            .load(imagen)
            .into(imageViewLibro)

        materialDialog = MaterialAlertDialogBuilder(this)
            .setTitle(resources.getString(R.string.cancelarPrestamo))
            .setMessage("Al cancelar el préstamo ya no podrás ir a recoger y disfrutar de tu libro. ¿Estás seguro?")

            .setNeutralButton("Cancelar") { dialog, which ->
                dialog.cancel()
            }
            .setPositiveButton("Aceptar") { dialog, which ->
                //eliminar préstamo
                recogerViewModel.borrarPrestamo(id)
                val intent = Intent(this, PrestamosRecogerActivity::class.java)
                startActivity(intent)
                this.finish()
            }

        cancelarButton.setOnClickListener {
            materialDialog.show()

        }

        backButton.setOnClickListener {
            this.finish()
        }



    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun diasRestantes(fechaRecogida: String): Int{
        var diasRestantes = -1;
        val calendarHoy = Calendar.getInstance()

        val formatter = SimpleDateFormat("dd-MMM-yyyy")
        val timeRecogida = formatter.parse(fechaRecogida)

        return diasRestantes

    }
}