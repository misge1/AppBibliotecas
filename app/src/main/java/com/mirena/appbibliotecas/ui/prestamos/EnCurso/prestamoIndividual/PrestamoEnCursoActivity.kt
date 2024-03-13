package com.mirena.appbibliotecas.ui.prestamos.EnCurso.prestamoIndividual

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.mirena.appbibliotecas.R
import com.mirena.appbibliotecas.SessionManager
import com.mirena.appbibliotecas.ui.Account.AccountActivity
import com.mirena.appbibliotecas.ui.ListaLibrosFiltrada.ListaFiltradaActivity
import com.mirena.appbibliotecas.ui.Login.LoginActivity
import com.mirena.appbibliotecas.ui.MainActivity.ScrollingActivity
import com.mirena.appbibliotecas.ui.prestamos.EnCurso.EnCursoViewModel
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class PrestamoEnCursoActivity : AppCompatActivity() {

    private lateinit var renovarButton: Button
    private lateinit var materialDialog: MaterialAlertDialogBuilder
    private lateinit var tituloTextView: TextView
    private lateinit var autorTextView: TextView
    private lateinit var fechaDevolucionTextView: TextView
    private lateinit var fechaInicioTextView: TextView
    private lateinit var bibliotecaTextView: TextView
    private lateinit var isbnTextView: TextView
    private lateinit var editorialTextView: TextView
    private lateinit var sessionManager: SessionManager
    private lateinit var imageView: ImageView
    private lateinit var backbutton: ImageView
    private lateinit var encursoViewModel: EnCursoViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prestamo_en_curso)

        renovarButton = findViewById(R.id.renovarButton)
        tituloTextView = findViewById(R.id.textview_titulo2)
        autorTextView = findViewById(R.id.textview_autor2)
        fechaDevolucionTextView = findViewById(R.id.fechaPrestamoFin)
        fechaInicioTextView = findViewById(R.id.fechaPrestamoIni)
        bibliotecaTextView = findViewById(R.id.nombreBiblioteca)
        editorialTextView = findViewById(R.id.textview_editorial)
        isbnTextView = findViewById(R.id.textview_isbn)
        sessionManager = SessionManager(this)
        imageView = findViewById(R.id.imageview_libro)
        backbutton = findViewById(R.id.back_button_encurso)
        encursoViewModel = ViewModelProvider(this)[EnCursoViewModel::class.java]

        var id = intent.getIntExtra("id", -1)
        var titulo = intent.getStringExtra("titulo")
        var autor = intent.getStringExtra("autor")
        var editorial = intent.getStringExtra("editorial")
        var isbn = intent.getStringExtra("isbn")
        var fechaFin = intent.getStringExtra("fechaFin")
        var fechaInicio = intent.getStringExtra("fechaInicio")
        var biblioteca = intent.getStringExtra("biblioteca")
        var imagen = intent.getStringExtra("imagen")

        tituloTextView.text = titulo
        autorTextView.text = autor
        bibliotecaTextView.text = biblioteca
        fechaDevolucionTextView.text = fechaFin
        fechaInicioTextView.text = fechaInicio
        editorialTextView.text = editorial
        isbnTextView.text = isbn

        Picasso.get()
            .load(imagen)
            .into(imageView)

        materialDialog = MaterialAlertDialogBuilder(this)
            .setTitle(resources.getString(R.string.renovar))
            .setMessage("Al renovar tu préstamos, dispondrás de 15 días más para disfrutar de tu libro. \n" +
                    "\n" +
                    "Recuerda que puedes renovar X veces más.")

            .setNeutralButton("Cancelar") { dialog, which ->
                dialog.cancel()
            }
            .setPositiveButton("Aceptar") { dialog, which ->
                var fechaRenovacion = calcularFechaRenovacion(fechaFin!!)
                encursoViewModel.renovarPrestamp(id,fechaRenovacion, applicationContext, this)
                finish()
                intent.putExtra("fechaFin", fechaRenovacion)
                startActivity(intent)
            }

        renovarButton.setOnClickListener {
            materialDialog.show()
        }

        backbutton.setOnClickListener {
            this.finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_favoritos, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.usuario -> {
                if (sessionManager.fetchAuthToken() == 0){
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                }else{
                    val intent = Intent(this, AccountActivity::class.java)
                    startActivity(intent)
                }
                true
            }
            R.id.home_menu -> {
                val intent = Intent(this, ScrollingActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }

    fun calcularFechaRenovacion(fechaDevolucion: String): String{
        var devolucionFecha = ""
        val calendarinst = Calendar.getInstance()
        val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.ITALY)
        val date = formatter.parse(fechaDevolucion)

        if (date != null) {
            calendarinst.time = date
            calendarinst.add(Calendar.DATE, 15)
            do {
                calendarinst.add(Calendar.DATE, 1)
            } while (calendarinst.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || calendarinst.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY); //excluding end date

            val devolucionTime: Date = calendarinst.time
            devolucionFecha = formatter.format(devolucionTime)
        }

        return devolucionFecha

    }

}