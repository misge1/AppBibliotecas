package com.mirena.appbibliotecas.ui.Filtros

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isEmpty
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputLayout
import com.mirena.appbibliotecas.R
import com.mirena.appbibliotecas.databinding.ActivityFiltrosBinding
import com.mirena.appbibliotecas.objects.Biblioteca
import com.mirena.appbibliotecas.objects.Generos
import com.mirena.appbibliotecas.objects.LibroPre
import com.mirena.appbibliotecas.objects.Subgeneros
import com.mirena.appbibliotecas.ui.EleccionFiltros.EleccionFiltrosActivity
import com.mirena.appbibliotecas.ui.EleccionFiltros.EleccionFiltrosViewModel
import com.mirena.appbibliotecas.ui.ListaLibrosFiltrada.ListaFiltradaActivity
import com.mirena.appbibliotecas.ui.Search.SearchActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class FiltrosActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFiltrosBinding
    private lateinit var searchviewfiltros: SearchView
    private lateinit var menuGeneros: TextInputLayout
    private lateinit var menuSubgeneros: TextInputLayout
    private lateinit var menuBibliotecas: TextInputLayout
    private lateinit var generoslinearlayout: LinearLayout
    private lateinit var subgeneroslinear: LinearLayout
    private lateinit var elecctionFiltrosViewModel: EleccionFiltrosViewModel
    private lateinit var context: Context
    private lateinit var bibliotecalinearlayout: LinearLayout
    private lateinit var materialDialog: MaterialAlertDialogBuilder
    private lateinit var adapterGeneros: ArrayAdapter<String>
    private lateinit var botonAplicarFiltros: ExtendedFloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFiltrosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.toolbar))
        searchviewfiltros = findViewById(R.id.searchviewFiltros)
        menuGeneros = findViewById(R.id.menu)
        menuSubgeneros = findViewById(R.id.menuSubgenero)
        menuBibliotecas = findViewById(R.id.menuBiblioteca)
        elecctionFiltrosViewModel = ViewModelProvider(this)[EleccionFiltrosViewModel::class.java]
        context = this
        botonAplicarFiltros = findViewById(R.id.buttonAplicarFiltros)
        //generoslinearlayout = findViewById(R.id.generosLinearLayout)
        //subgeneroslinear = findViewById(R.id.subgenerosLinearLayout)
        //bibliotecalinearlayout = findViewById(R.id.bibliotecaLinearLayout)

        val intentfiltros = Intent(this, EleccionFiltrosActivity::class.java)

        searchviewfiltros.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }

        elecctionFiltrosViewModel.getGeneros()

        CoroutineScope(Dispatchers.IO).launch {

            var listaGeneros = listOf<Generos>()
            var listaLibros = listOf<LibroPre>()
            var listaNombresGenero = mutableListOf<String>()

            elecctionFiltrosViewModel.getgenerosflow().collectLatest {
                listaGeneros = it
                for(generos: Generos in listaGeneros){
                    listaNombresGenero.add(generos.genero)

                }
                runOnUiThread {
                    adapterGeneros = ArrayAdapter(context , R.layout.list_item, listaNombresGenero)
                    (menuGeneros.editText as? AutoCompleteTextView)?.setAdapter(adapterGeneros)
                }
            }
        }


           if (!menuGeneros.isEmpty()) {
               (menuGeneros.getEditText() as AutoCompleteTextView).onItemClickListener =
                   OnItemClickListener { adapterView, view, position, id ->
                       val selectedValue: String = adapterGeneros.getItem(position)!!
                       elecctionFiltrosViewModel.getSubGeneros(selectedValue)
                   }


               CoroutineScope(Dispatchers.IO).launch {

                   var listaSubGeneros = listOf<Subgeneros>()
                   var listaLibros = listOf<LibroPre>()


                   elecctionFiltrosViewModel.getSubGenerosflow().collectLatest {
                       var listaNombresSubGenero = mutableListOf<String>()
                       listaSubGeneros = it
                       for (subgeneros: Subgeneros in listaSubGeneros) {
                           listaNombresSubGenero.add(subgeneros.subgenero)

                       }
                       runOnUiThread {
                           val adapter =
                               ArrayAdapter(context, R.layout.list_item, listaNombresSubGenero)
                           (menuSubgeneros.editText as? AutoCompleteTextView)?.setAdapter(adapter)
                       }
                   }
               }

           }


        elecctionFiltrosViewModel.getBibliotecas()

        CoroutineScope(Dispatchers.IO).launch {

            var listaBiblioteca = listOf<Biblioteca>()
            var listaNombresBiblioteca = mutableListOf<String>()

            elecctionFiltrosViewModel.getBibliotecasFlow().collectLatest {
                listaBiblioteca = it
                for(bibliotecas: Biblioteca in listaBiblioteca){
                    listaNombresBiblioteca.add(bibliotecas.nombre)

                }
                runOnUiThread {
                    val adapter = ArrayAdapter(context , R.layout.list_item, listaNombresBiblioteca)
                    (menuBibliotecas.editText as? AutoCompleteTextView)?.setAdapter(adapter)
                }
            }
        }

        botonAplicarFiltros.setOnClickListener{
            buscarPorFiltros()
        }






    }

    /**
     * función para cuando le das al botón de aplicar filtros
     */

    fun buscarPorFiltros() {

            val eleccionSubgenero = menuSubgeneros.editText!!.text.toString()
            val eleccionBiblioteca = menuBibliotecas.editText!!.text.toString()

            val intent = Intent(context,ListaFiltradaActivity::class.java)
            intent.putExtra("subgenero", eleccionSubgenero)
            intent.putExtra("biblioteca", eleccionBiblioteca)

            startActivity(intent)

    }
}