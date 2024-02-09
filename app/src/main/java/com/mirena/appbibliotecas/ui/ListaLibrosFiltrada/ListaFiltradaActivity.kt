package com.mirena.appbibliotecas.ui.ListaLibrosFiltrada

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.HorizontalScrollView
import android.widget.ImageView
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.mirena.appbibliotecas.R
import com.mirena.appbibliotecas.adapters.AdapterGeneric
import com.mirena.appbibliotecas.adapters.AdapterLibros
import com.mirena.appbibliotecas.adapters.AdapterLibrosObject
import com.mirena.appbibliotecas.databinding.ActivityListaFiltradaBinding
import com.mirena.appbibliotecas.objects.Biblioteca
import com.mirena.appbibliotecas.objects.Idioma
import com.mirena.appbibliotecas.objects.LibroObject
import com.mirena.appbibliotecas.objects.LibroPre
import com.mirena.appbibliotecas.ui.Account.AccountActivity
import com.mirena.appbibliotecas.ui.EleccionFiltros.EleccionFiltrosActivity
import com.mirena.appbibliotecas.ui.Filtros.FiltrosActivity
import com.mirena.appbibliotecas.ui.Filtros.Generos.FiltroGenerosActivity
import com.mirena.appbibliotecas.ui.Login.LoginActivity
import com.mirena.appbibliotecas.ui.MainActivity.ScrollingActivity
import kotlinx.android.synthetic.main.chips_filters_view.view.chipBiblioteca
import kotlinx.android.synthetic.main.chips_filters_view.view.chipDisponibles
import kotlinx.android.synthetic.main.chips_filters_view.view.chipGenero
import kotlinx.android.synthetic.main.chips_filters_view.view.chipIdiomas
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class ListaFiltradaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListaFiltradaBinding
    private lateinit var listaFiltradaViewModel: ListaFiltradaViewModel
    private lateinit var adapterLibros: AdapterLibrosObject
    private lateinit var context: Context
    private lateinit var backbutton: ImageView
    private lateinit var nestedScrollView: HorizontalScrollView
    private lateinit var chipSubgeneros: Chip
    private lateinit var chipBiblioteca: Chip
    private lateinit var chipIdioma: Chip
    private lateinit var chipDisponible: Chip
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityListaFiltradaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        nestedScrollView = binding.scrollViewFilters.scrollViewFilters
        chipSubgeneros = nestedScrollView.chipGenero
        chipBiblioteca = nestedScrollView.chipBiblioteca
        chipIdioma = nestedScrollView.chipIdiomas
        chipDisponible = nestedScrollView.chipDisponibles
        listaFiltradaViewModel = ViewModelProvider(this)[ListaFiltradaViewModel::class.java]
        context = this
        backbutton = binding.backButtonListFiltrada

        val arraySubgeneros = listaFiltradaViewModel.getFiltroSubgenerosSession()
        val arrayIdiomas = listaFiltradaViewModel.getFiltroIdiomasSession()
        val arrayBibliotecas = listaFiltradaViewModel.getFiltroBibliotecasSession()
        var disponibles = listaFiltradaViewModel.getFiltroDisponibles()

        //setting the chips to be or not to be checked
        chipSubgeneros.isChecked = arraySubgeneros.isNotEmpty()
        if(arrayIdiomas.isNotEmpty()){
            chipIdioma.isChecked = true
        }
        if(arrayBibliotecas.isNotEmpty()){
            chipBiblioteca.isChecked = true
        }
        chipDisponible.isChecked = disponibles ==1

        if (disponibles==-1){
            disponibles = 0
            listaFiltradaViewModel
        }

        //getting the filteres books
        listaFiltradaViewModel.getLibrosFiltrados(arrayIdiomas,arrayBibliotecas, arraySubgeneros, disponibles)

        CoroutineScope(Dispatchers.IO).launch {
            var listaFiltrada = listOf<LibroPre>()
            listaFiltradaViewModel.getLibrosFiltradosLivedata().collectLatest {
                listaFiltrada = it

                runOnUiThread {
                    val mrecyclerview =
                        findViewById<RecyclerView>(R.id.recyclerviewListaFiltrada)
                    val adapter =  AdapterLibros(context, listaFiltrada)
                    mrecyclerview.layoutManager = LinearLayoutManager(context)
                    mrecyclerview.adapter = adapter
                }
            }
        }

        //function for the backbuton
        backbutton.setOnClickListener {
            val intent = Intent(this, FiltrosActivity::class.java)
            startActivity(intent)
            this.finish()
        }

        //direct path to the filter lists
        chipSubgeneros.setOnClickListener {
            val intent = Intent(this, FiltroGenerosActivity::class.java)
            intent.putExtra("comesfromlistaparageneros", 1)
            startActivity(intent)
            this.finish()
        }

        chipBiblioteca.setOnClickListener {
            val intent = Intent(this, EleccionFiltrosActivity::class.java)
            intent.putExtra("comesfromlistafiltrada", 1)
            intent.putExtra("tipo", "bibliotecas")
            startActivity(intent)
            this.finish()
        }

        chipIdioma.setOnClickListener {
            val intent = Intent(this, EleccionFiltrosActivity::class.java)
            intent.putExtra("comesfromlistafiltrada", 1)
            intent.putExtra("tipo", "idiomas")
            startActivity(intent)
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
                if (listaFiltradaViewModel.getAuthToken() == 0){
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


    override fun onBackPressed() {
        val intent = Intent(this, FiltrosActivity::class.java)
        startActivity(intent)
        this.finish()
    }
}