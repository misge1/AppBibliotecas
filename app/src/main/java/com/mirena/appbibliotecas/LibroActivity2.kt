package com.mirena.appbibliotecas

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.mirena.appbibliotecas.adapters.AdapterLibros
import com.mirena.appbibliotecas.databinding.ActivityLibro2Binding
import com.mirena.appbibliotecas.objects.Biblioteca
import com.mirena.appbibliotecas.objects.LibroPre
import com.mirena.appbibliotecas.retrofit.RetrofitInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LibroActivity2 : AppCompatActivity() {

    private lateinit var binding: ActivityLibro2Binding
    private lateinit var textview_titulo: TextView
    private lateinit var textview_autor: TextView
    private lateinit var textview_descripcion: TextView
    private lateinit var textview_isbn: TextView
    private lateinit var textview_idioma: TextView
    private lateinit var butonejemplares: Button
    private lateinit var context: Context
    private lateinit var materialDialog: MaterialAlertDialogBuilder
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLibro2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        context = this



        setSupportActionBar(findViewById(R.id.toolbar))

        var id = intent.getIntExtra("id", 0)
        var titulo = intent.getStringExtra("titulo")
        var autor = intent.getStringExtra("autor")
        var descripcion = intent.getStringExtra("descripcion")
        var idioma = intent.getStringExtra("idioma")
        var isbn = intent.getStringExtra("isbn")

        textview_autor = binding.layoutInclude.textviewAutor
        textview_titulo = binding.layoutInclude.textviewTitulo
        textview_descripcion = binding.layoutInclude.textviewDescripcion
        textview_isbn = binding.layoutInclude.textviewIsbn
        textview_idioma = binding.layoutInclude.textviewIdioma
        butonejemplares = binding.layoutInclude.buttonEjemplares

        
        textview_titulo.text = titulo
        textview_autor.text = autor
        textview_isbn.text = isbn
        textview_descripcion.text = descripcion
        textview_idioma.text = idioma

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

                            materialDialog = MaterialAlertDialogBuilder(context)
                                .setTitle(resources.getString(R.string.bibliotecas))
                                .setSingleChoiceItems(arrayLibros, checkedItem){ dialog, which ->

                                }
                                .setNeutralButton("cerrar") { dialog, which ->
                                    dialog.cancel()
                                }
                        }
                    }
                }
            }
        }



        butonejemplares.setOnClickListener {
            materialDialog.show()
        }






    }
}