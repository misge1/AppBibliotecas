package com.mirena.appbibliotecas

import android.content.Context
import android.os.Bundle
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mirena.appbibliotecas.adapters.AdapterGeneros
import com.mirena.appbibliotecas.adapters.AdapterLibros
import com.mirena.appbibliotecas.databinding.ActivitySubgenerosBinding
import com.mirena.appbibliotecas.objects.Generos
import com.mirena.appbibliotecas.objects.LibroPre
import com.mirena.appbibliotecas.retrofit.RetrofitInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SubgenerosActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySubgenerosBinding
    lateinit var librosAdapter: AdapterLibros
    private lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySubgenerosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.toolbar))
        context = this

        val id_genero = intent.getIntExtra("id_genero", 0)

        CoroutineScope(Dispatchers.IO).launch {
            val callibro = RetrofitInstance.api.getLibrosSubgenero(id_genero)
            var listaLibros = listOf<LibroPre>()

            runOnUiThread{

                if (callibro.isSuccessful){
                    callibro.body().let {
                        if (it != null){
                            listaLibros = it
                            val recyclerviewlibros =
                                findViewById<RecyclerView>(R.id.recyclerview_libros_subgenero)
                            recyclerviewlibros.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                            librosAdapter = AdapterLibros(context, listaLibros)
                            recyclerviewlibros.adapter = librosAdapter
                        }
                    }
                }
            }
        }

    }
}