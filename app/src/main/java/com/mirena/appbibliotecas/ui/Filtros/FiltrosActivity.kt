package com.mirena.appbibliotecas.ui.Filtros

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.mirena.appbibliotecas.R
import com.mirena.appbibliotecas.adapters.AdapterGeneric
import com.mirena.appbibliotecas.databinding.ActivityFiltrosBinding
import com.mirena.appbibliotecas.objects.Generos
import com.mirena.appbibliotecas.objects.LibroPre
import com.mirena.appbibliotecas.objects.Subgeneros
import com.mirena.appbibliotecas.ui.Search.SearchActivity
import com.mirena.appbibliotecas.ui.EleccionFiltros.EleccionFiltrosActivity
import com.mirena.appbibliotecas.ui.EleccionFiltros.EleccionFiltrosViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.w3c.dom.Text

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

            elecctionFiltrosViewModel.getgenerosflow().collectLatest {
                listaGeneros = it
                runOnUiThread {
                    val adapter = ArrayAdapter(context , R.layout.list_item, listaGeneros)
                    (menuGeneros.editText as? AutoCompleteTextView)?.setAdapter(adapter)
                }
            }
        }

        //val items = listOf("Option 1", "Option 2", "Option 3", "Option 4")


        /*generoslinearlayout.setOnClickListener {
            intentfiltros.putExtra("tipo", "generos")
            startActivity(intentfiltros)

        }

        subgeneroslinear.setOnClickListener {
            intentfiltros.putExtra("tipo", "subgeneros")
            startActivity(intentfiltros)
        }

        bibliotecalinearlayout.setOnClickListener {
            intentfiltros.putExtra("tipo", "bibliotecas")
            startActivity(intentfiltros)
        }*/


    }
}