package com.mirena.appbibliotecas.ui.Libro

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.mirena.appbibliotecas.*
import com.mirena.appbibliotecas.adapters.AdapterBibliosLibro
import com.mirena.appbibliotecas.adapters.AdapterComentarios
import com.mirena.appbibliotecas.adapters.AdapterSubgenerosLibro
import com.mirena.appbibliotecas.databinding.ActivityLibro2Binding
import com.mirena.appbibliotecas.objects.Biblioteca
import com.mirena.appbibliotecas.objects.Comentario
import com.mirena.appbibliotecas.objects.ComentarioSave
import com.mirena.appbibliotecas.objects.Favoritos
import com.mirena.appbibliotecas.objects.Subgeneros
import com.mirena.appbibliotecas.ui.Account.AccountActivity
import com.mirena.appbibliotecas.ui.Login.LoginActivity
import com.mirena.appbibliotecas.ui.MainActivity.ScrollingActivity
import com.mirena.appbibliotecas.ui.Pedido.PedidoActivity
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import java.io.File
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager


class LibroActivity2 : AppCompatActivity() {

    private lateinit var binding: ActivityLibro2Binding
    private lateinit var textview_titulo: TextView
    private lateinit var textview_autor: TextView
    private lateinit var textview_descripcion: TextView
    private lateinit var textview_isbn: TextView
    private lateinit var textview_idioma: TextView
    private lateinit var textview_editorial: TextView
    private lateinit var imageView: ImageView
    private lateinit var butonejemplares: Button
    private lateinit var context: Context
    private lateinit var sessionManager: SessionManager
    private lateinit var buttonFavoritos: ImageButton
    private lateinit var libroActivity2ViewModel: LibroActivity2ViewModel
    private lateinit var materialDialog: MaterialAlertDialogBuilder
    private lateinit var textviewEstadoEjemplar: TextView
    private lateinit var subgenerosLibroAdapter: AdapterSubgenerosLibro
    private lateinit var bibliotecasLibroAdapter: AdapterBibliosLibro
    private lateinit var comentariosLibroAdapter: AdapterComentarios
    private lateinit var backbutton: ImageView
    private lateinit var textFieldComentario: TextInputEditText
    private lateinit var buttonAddComentario: MaterialButton

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

        setSupportActionBar(findViewById(R.id.toolbar))

        var id = intent.getIntExtra("id", 0)
        var titulo = intent.getStringExtra("titulo")
        var autor = intent.getStringExtra("autor")
        var descripcion = intent.getStringExtra("descripcion")
        var idioma = intent.getStringExtra("idioma")
        var isbn = intent.getStringExtra("isbn")
        var editorial = intent.getStringExtra("editorial")
        var image = intent.getStringExtra("image")


        textview_autor = binding.layoutInclude.textviewAutor
        textview_titulo = binding.layoutInclude.textviewTitulo
        textview_descripcion = binding.layoutInclude.textviewDescripcion
        textview_isbn = binding.layoutInclude.textviewIsbn
        textview_idioma = binding.layoutInclude.textviewIdioma
        textview_editorial = binding.layoutInclude.textviewEditorial
        butonejemplares = binding.layoutInclude.buttonEjemplares
        buttonFavoritos = binding.layoutInclude.buttonFavoritos
        textviewEstadoEjemplar = binding.layoutInclude.textviewEstadoEjemplar
        imageView = binding.layoutInclude.imagePlacer
        backbutton = binding.backButtonLibro
        textFieldComentario = binding.layoutInclude.fragmentComentario.textInputEditComentario
        buttonAddComentario = binding.layoutInclude.fragmentComentario.buttonAddComment

        textview_titulo.text = titulo
        textview_autor.text = autor
        textview_isbn.text = isbn
        textview_descripcion.text = descripcion
        textview_idioma.text = idioma
        textview_editorial.text = editorial


        if(image!=null ){
            Picasso.get().load(image )
                .into(imageView)
        }


        if (sessionManager.fetchAuthToken()!=0){
            libroActivity2ViewModel.comprobarlibrofav(sessionManager.fetchAuthToken(), id)

            CoroutineScope(Dispatchers.IO).launch {
                libroActivity2ViewModel.getComprobacion().collectLatest {

                    runOnUiThread {
                        buttonFavoritos.isSelected = it != "0"

                        if (buttonFavoritos.isSelected){
                            buttonFavoritos.setImageResource(R.drawable.favorito_icono_filled)
                        }else{
                            buttonFavoritos.setImageResource(R.drawable.favorito_icono)
                        }
                    }
                }
            }
        }

        val intent = Intent(context, PedidoActivity::class.java)
        intent.putExtra("id_libro", id)

        materialDialog = MaterialAlertDialogBuilder(context, R.style.ThemeOverlay_App_MaterialAlertDialog)
            .setTitle(resources.getString(R.string.bibliotecas))

            .setNeutralButton("cerrar") { dialog, which ->
                dialog.cancel()
            }


        libroActivity2ViewModel.getDisponibilidad(id)
        libroActivity2ViewModel.getSubgenerosLibro(id)
        libroActivity2ViewModel.getComentarios(id)

        CoroutineScope(Dispatchers.IO).launch {
            var listalibros: ArrayList<String> = ArrayList<String>()
            var listaBiblios: List<Biblioteca> = listOf<Biblioteca>()

            libroActivity2ViewModel.getDisponibilidadLivedata().collectLatest { it ->
                listaBiblios = it

                listaBiblios.forEach {
                    listalibros.add(it.nombre)
                }
                runOnUiThread {
                    textviewEstadoEjemplar.text = "Disponiblee"
                    if (listaBiblios.isNotEmpty()){
                        Log.i("DEBUG","entra en listaBiblios.isNotEmpty()")

                        var checkedItem = -1
                        val arrayLibros = listalibros.toTypedArray()

                        materialDialog.setSingleChoiceItems(arrayLibros, checkedItem) { dialog, which ->
                            var choice = arrayLibros[which]
                            var id_biblio =
                                listaBiblios.find { it.nombre == choice }!!.id
                            intent.putExtra("id_biblioteca", id_biblio)
                        }
                        materialDialog.setPositiveButton("Pedir") { dialog, which ->
                            startActivity(intent)
                        }
                    }else{
                        textviewEstadoEjemplar.text = "No disponible"
                        textviewEstadoEjemplar.setTextColor(Color.RED)
                    }
                }
            }
        }

        CoroutineScope(Dispatchers.IO).launch{
            libroActivity2ViewModel.getSubgenerosLibroLd().collectLatest{
                runOnUiThread {
                    var listaLibros = listOf<Subgeneros>()
                    listaLibros = it
                    val recyclerviewSubgeneros = binding.layoutInclude.generosLibroRView
                    recyclerviewSubgeneros.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    subgenerosLibroAdapter = AdapterSubgenerosLibro(context, listaLibros)
                    recyclerviewSubgeneros.adapter = subgenerosLibroAdapter
                }

            }
        }

       CoroutineScope(Dispatchers.IO).launch {
            libroActivity2ViewModel.getDisponibilidadLivedata().collectLatest {
                runOnUiThread {
                    var listaBiblios = listOf<Biblioteca>()
                    listaBiblios = it
                    val recyclerViewBibliotecas = binding.layoutInclude.bibliotecasLibroRView
                    recyclerViewBibliotecas.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
                    bibliotecasLibroAdapter = AdapterBibliosLibro(context, listaBiblios)
                    recyclerViewBibliotecas.adapter = bibliotecasLibroAdapter
                }
            }
        }

        CoroutineScope(Dispatchers.IO).launch {
            libroActivity2ViewModel.getComentariosFlow().collectLatest {
                runOnUiThread {
                    var listaComentarios = listOf<Comentario>()
                    listaComentarios = it
                    val recyclerViewComentarios = binding.layoutInclude.comentariosLibroRView
                    recyclerViewComentarios.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    comentariosLibroAdapter = AdapterComentarios(context, listaComentarios)
                    recyclerViewComentarios.adapter = comentariosLibroAdapter

                }
            }
        }

        backbutton.setOnClickListener {
            this.finish()
        }


        /**
         * comportamiento boton click para ver los ejemplares y pedir uno
         */
        butonejemplares.setOnClickListener {
            if(sessionManager.fetchAuthToken()==0){
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }else{
                materialDialog.show()
            }
        }

        /**
         * Añadir un comentario
         */

        buttonAddComentario.setOnClickListener {
            if (sessionManager.fetchAuthToken()==0){
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }else{
                val comentario = ComentarioSave(null,
                    textFieldComentario.text.toString(),
                    sessionManager.fetchAuthToken(),
                    id,
                    getFechaHoy()
                    )

                libroActivity2ViewModel.addComentario(comentario)
                startActivity(this.intent)
            }
        }

        /**
         * Comportamiento click boton favoritos
         */
        buttonFavoritos.setOnClickListener {

            if (sessionManager.fetchAuthToken() != 0) {

                val nuevoFavrito = Favoritos(null, id, sessionManager.fetchAuthToken())

                buttonFavoritos.isSelected = !buttonFavoritos.isSelected

                if (buttonFavoritos.isSelected) {
                    buttonFavoritos.setImageResource(R.drawable.favorito_icono_filled)
                    //myDrawable!!.colorFilter = PorterDuffColorFilter(Color.RED, PorterDuff.Mode.SRC_IN)
                    libroActivity2ViewModel.addFavoritos(nuevoFavrito, applicationContext, this)
                } else {
                    //myDrawable!!.clearColorFilter()
                    buttonFavoritos.setImageResource(R.drawable.favorito_icono)
                    libroActivity2ViewModel.deletefavoritos(
                        id,
                        sessionManager.fetchAuthToken(),
                        applicationContext,
                        this
                    )
                }
            }else{
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
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

    fun getFechaHoy(): String{
        val time = Calendar.getInstance().time
        val formatter = SimpleDateFormat("dd/MM/yyyy")
        val current = formatter.format(time)

        return current;
    }

}