package com.mirena.appbibliotecas

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.mirena.appbibliotecas.Account.AccountActivity
import com.mirena.appbibliotecas.adapters.AdapterLibros
import com.mirena.appbibliotecas.databinding.ActivityLibro2Binding
import com.mirena.appbibliotecas.objects.Biblioteca
import com.mirena.appbibliotecas.objects.Favoritos
import com.mirena.appbibliotecas.objects.LibroPre
import com.mirena.appbibliotecas.objects.Prestamo
import com.mirena.appbibliotecas.retrofit.RetrofitInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Month
import java.util.*
import kotlin.collections.ArrayList

class LibroActivity2 : AppCompatActivity() {

    private lateinit var binding: ActivityLibro2Binding
    private lateinit var textview_titulo: TextView
    private lateinit var textview_autor: TextView
    private lateinit var textview_descripcion: TextView
    private lateinit var textview_isbn: TextView
    private lateinit var textview_idioma: TextView
    private lateinit var textview_editorial: TextView
    private lateinit var butonejemplares: Button
    private lateinit var context: Context
    private lateinit var sessionManager: SessionManager
    private lateinit var buttonFavoritos: ImageButton
    private lateinit var libroActivity2ViewModel: LibroActivity2ViewModel
    private lateinit var materialDialog: MaterialAlertDialogBuilder
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLibro2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        context = this
        sessionManager = SessionManager(this)

        libroActivity2ViewModel = ViewModelProvider(this).get(
            LibroActivity2ViewModel::class.java
        )

        val myDrawable = ContextCompat.getDrawable(context, R.drawable.favorito_icono)
        val colorFavoritos = PorterDuffColorFilter(Color.RED, PorterDuff.Mode.SRC_IN)


        setSupportActionBar(findViewById(R.id.toolbar))

        var id = intent.getIntExtra("id", 0)
        var titulo = intent.getStringExtra("titulo")
        var autor = intent.getStringExtra("autor")
        var descripcion = intent.getStringExtra("descripcion")
        var idioma = intent.getStringExtra("idioma")
        var isbn = intent.getStringExtra("isbn")
        var editorial = intent.getStringExtra("editorial")

        textview_autor = binding.layoutInclude.textviewAutor
        textview_titulo = binding.layoutInclude.textviewTitulo
        textview_descripcion = binding.layoutInclude.textviewDescripcion
        textview_isbn = binding.layoutInclude.textviewIsbn
        textview_idioma = binding.layoutInclude.textviewIdioma
        textview_editorial = binding.layoutInclude.textviewEditorial
        butonejemplares = binding.buttonEjemplares
        buttonFavoritos = binding.buttonFavoritos

        
        textview_titulo.text = titulo
        textview_autor.text = autor
        textview_isbn.text = isbn
        textview_descripcion.text = descripcion
        textview_idioma.text = idioma
        textview_editorial.text = editorial

        CoroutineScope(Dispatchers.IO).launch {
            val calldisponibilidad = RetrofitInstance.api.getDisponibilidad(id)
            var listaBiblios = listOf<Biblioteca>()

            runOnUiThread{

                var listalibros = ArrayList<String>()

                if (calldisponibilidad.isSuccessful){
                    calldisponibilidad.body().let {
                        if (it != null){
                            listaBiblios = it
                            listaBiblios.forEach{
                                listalibros.add(it.biblioteca)

                            }
                            val arrayLibros = listalibros.toTypedArray()
                            val checkedItem = 0
                            var id_biblio: Int = 0

                            materialDialog = MaterialAlertDialogBuilder(context)
                                .setTitle(resources.getString(R.string.bibliotecas))
                                .setSingleChoiceItems(arrayLibros, checkedItem){ dialog, which ->
                                    val choice = arrayLibros[checkedItem]
                                    id_biblio = listaBiblios.find { it.biblioteca==choice }!!.id_bilioteca
                                }
                                .setNeutralButton("cerrar") { dialog, which ->
                                    dialog.cancel()
                                }
                                .setPositiveButton("Pedir"){ dialog, which ->
                                    val intent = Intent(context, PedidoActivity::class.java)
                                    intent.putExtra("id_biblioteca", id_biblio)
                                    startActivity(intent)


                                }
                        }
                    }
                }
            }
        }

        butonejemplares.setOnClickListener {
           materialDialog.show()
        }

        val savedStateButton = sessionManager.fetchButtonState()
        buttonFavoritos.isSelected = savedStateButton != 0


        buttonFavoritos.setOnClickListener {

            val nuevoFavrito = Favoritos(null, id, sessionManager.fetchAuthToken())

            buttonFavoritos.isSelected = !buttonFavoritos.isSelected

            if (buttonFavoritos.isSelected){
                sessionManager.saveButtonState(1)
                libroActivity2ViewModel.addFavoritos(nuevoFavrito, applicationContext, this)

            }else{
                sessionManager.saveButtonState(0)
                libroActivity2ViewModel.deletefavoritos(id,sessionManager.fetchAuthToken(), applicationContext, this)
            }




        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.upper_menu, menu)
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
            else -> super.onOptionsItemSelected(item)
        }

    }

}