package com.mirena.appbibliotecas.ui.Libro

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.mirena.appbibliotecas.*
import com.mirena.appbibliotecas.ui.Account.AccountActivity
import com.mirena.appbibliotecas.databinding.ActivityLibro2Binding
import com.mirena.appbibliotecas.objects.Biblioteca
import com.mirena.appbibliotecas.objects.Favoritos
import com.mirena.appbibliotecas.retrofit.RetrofitInstance
import com.mirena.appbibliotecas.ui.Login.LoginActivity
import com.mirena.appbibliotecas.ui.Pedido.PedidoActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.collections.ArrayList
import androidx.lifecycle.lifecycleScope

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

        setSupportActionBar(findViewById(R.id.toolbar_libro))

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

        val intent = Intent(context, PedidoActivity::class.java)
        intent.putExtra("id_libro", id)

        //libroActivity2ViewModel.getDisponibilidad(id)

        CoroutineScope(Dispatchers.IO).launch {
            var listalibros: ArrayList<String> = ArrayList<String>()
            var listaBiblios: List<Biblioteca> = listOf<Biblioteca>()

            val call = RetrofitInstance.api.getDisponibilidad(id)

            /*libroActivity2ViewModel.getDisponibilidadLivedata().collectLatest {
                listaBiblios = it

            }*/

            runOnUiThread {

                if (call.isSuccessful){
                    call.body().let {
                        if (it!=null){
                            listaBiblios = it
                        }
                    }

                    listaBiblios.forEach {
                        listalibros.add(it.biblioteca)
                    }

                    var checkedItem = -1
                    val arrayLibros = listalibros.toTypedArray()

                    materialDialog = MaterialAlertDialogBuilder(context)
                        .setTitle(resources.getString(R.string.bibliotecas))
                        .setSingleChoiceItems(arrayLibros, checkedItem) { dialog, which ->
                            var choice = arrayLibros[which]
                            var id_biblio =
                                listaBiblios.find { it.biblioteca == choice }!!.id_biblioteca
                            intent.putExtra("id_biblioteca", id_biblio)
                        }
                        .setNeutralButton("cerrar") { dialog, which ->
                            dialog.cancel()
                        }
                        .setPositiveButton("Pedir") { dialog, which ->
                            startActivity(intent)
                        }
                }

            }
        }


        butonejemplares.setOnClickListener {
            if(sessionManager.fetchAuthToken()==0){
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }else{
                materialDialog.show()
            }
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