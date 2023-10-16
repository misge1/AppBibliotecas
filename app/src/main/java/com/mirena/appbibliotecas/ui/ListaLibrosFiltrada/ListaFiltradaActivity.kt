package com.mirena.appbibliotecas.ui.ListaLibrosFiltrada

import android.content.Context
import android.os.Bundle
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mirena.appbibliotecas.R
import com.mirena.appbibliotecas.adapters.AdapterLibros
import com.mirena.appbibliotecas.adapters.AdapterLibrosObject
import com.mirena.appbibliotecas.databinding.ActivityListaFiltradaBinding
import com.mirena.appbibliotecas.objects.LibroObject
import com.mirena.appbibliotecas.objects.LibroPre
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class ListaFiltradaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListaFiltradaBinding
    private lateinit var listaFiltradaViewModel: ListaFiltradaViewModel
    private lateinit var adapterLibros: AdapterLibrosObject
    private lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityListaFiltradaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val subgenero = intent.getStringExtra("subgenero").toString()
        val biblioteca = intent.getStringExtra("biblioteca").toString()
        listaFiltradaViewModel = ViewModelProvider(this)[ListaFiltradaViewModel::class.java]
        context = this
        listaFiltradaViewModel.getFiltroSubgBiblioteca(subgenero, biblioteca)


        CoroutineScope(Dispatchers.IO).launch{
            var listaLibros = listOf<LibroObject>()
            listaFiltradaViewModel.getFiltroSubgBibliotecaFlow().collectLatest {
               listaLibros = it

                runOnUiThread {
                    val recyclerViewLibros = binding.contentListaFiltrada.recyclerviewListaFiltrada
                    recyclerViewLibros.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    adapterLibros = AdapterLibrosObject(context, listaLibros)
                    recyclerViewLibros.adapter = adapterLibros
                }
            }
        }




    }
}