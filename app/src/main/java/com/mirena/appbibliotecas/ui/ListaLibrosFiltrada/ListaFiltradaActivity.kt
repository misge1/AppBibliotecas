package com.mirena.appbibliotecas.ui.ListaLibrosFiltrada

import android.os.Bundle
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.mirena.appbibliotecas.R
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityListaFiltradaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val subgenero = intent.getStringExtra("subgenero").toString()
        val biblioteca = intent.getStringExtra("biblioteca").toString()
        listaFiltradaViewModel = ViewModelProvider(this)[ListaFiltradaViewModel::class.java]

        listaFiltradaViewModel.getFiltroSubgBiblioteca(subgenero, biblioteca)


        CoroutineScope(Dispatchers.IO).launch{
            var listaLibros = listOf<LibroObject>()
            listaFiltradaViewModel.getFiltroSubgBibliotecaFlow().collectLatest {
               listaLibros = it
            }
        }




    }
}