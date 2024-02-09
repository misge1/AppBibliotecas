package com.mirena.appbibliotecas.ui.EleccionFiltros

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mirena.appbibliotecas.R
import com.mirena.appbibliotecas.SessionManager
import com.mirena.appbibliotecas.adapters.*
import com.mirena.appbibliotecas.objects.Biblioteca
import com.mirena.appbibliotecas.objects.Generos
import com.mirena.appbibliotecas.objects.Idioma
import com.mirena.appbibliotecas.objects.LibroPre
import com.mirena.appbibliotecas.objects.Subgeneros
import com.mirena.appbibliotecas.ui.Account.AccountActivity
import com.mirena.appbibliotecas.ui.Filtros.FiltrosActivity
import com.mirena.appbibliotecas.ui.Filtros.Generos.FiltroGenerosActivity
import com.mirena.appbibliotecas.ui.ListaLibrosFiltrada.ListaFiltradaActivity
import com.mirena.appbibliotecas.ui.Login.LoginActivity
import com.mirena.appbibliotecas.ui.MainActivity.ScrollingActivity
import kotlinx.android.synthetic.main.chips_filters_view.chipBiblioteca
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

class EleccionFiltrosActivity() : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var elecctionFiltrosViewModel: EleccionFiltrosViewModel
    private lateinit var context: Context
    private lateinit var sessionManager: SessionManager
    private lateinit var listaIdsGeneros:ArrayList<Int>
    private lateinit var listaIdsSubgeneros: ArrayList<Int>
    private lateinit var listaIdsBibliotecas: ArrayList<Int>
    private lateinit var listaIdsIdiomas: ArrayList<Int>
    private lateinit var listaIdsSubgenerosEliminar: ArrayList<Int>
    private lateinit var listaIdsIdiomasEliminar: ArrayList<Int>
    private lateinit var listIdsBibliEliminar: ArrayList<Int>
    private lateinit var backbutton: ImageView
    private lateinit var listCheckboxes: ArrayList<CheckBox>

    private var comesfromgenders by Delegates.notNull<Int>()
    private var comesfrom by Delegates.notNull<Int>()
    private var tipoEnvio by Delegates.notNull<Int>()
    private var comesfromlistaparageneros by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eleccion_filtros)

        val tipo = intent.getStringExtra("tipo")
        val idGenero = intent.getIntExtra("id_genero", -1)
        comesfromgenders = intent.getIntExtra("comesfromgenders", -1)
        comesfrom = intent.getIntExtra("comesfromlistafiltrada", -1)
        comesfromlistaparageneros = intent.getIntExtra("comesfromlistaparageneros", -1);
        tipoEnvio = -1;

        context = this
        recyclerView = findViewById(R.id.recyclerviewEleccionFiltros)
        elecctionFiltrosViewModel = ViewModelProvider(this)[EleccionFiltrosViewModel::class.java]
        sessionManager = SessionManager(this)
        listaIdsGeneros = arrayListOf<Int>()
        listaIdsSubgeneros = arrayListOf<Int>()
        listaIdsSubgenerosEliminar = arrayListOf<Int>()
        listaIdsBibliotecas = arrayListOf<Int>()
        listIdsBibliEliminar = arrayListOf<Int>()
        listaIdsIdiomas = arrayListOf<Int>()
        listaIdsIdiomasEliminar = arrayListOf<Int>()
        backbutton = findViewById(R.id.backButtonFiltersElection)
        listCheckboxes = arrayListOf<CheckBox>()


        val arraySubgeneros = elecctionFiltrosViewModel.getFiltroSubgenerosSession()
        val arrayIdiomas = elecctionFiltrosViewModel.getFiltroIdiomasSession()
        val arrayBibliotecas = elecctionFiltrosViewModel.getFiltroBibliotecasSession()
        val disponibilidad = elecctionFiltrosViewModel.getFiltroDisponibles()

        if(idGenero!=-1){
            tipoEnvio = 1
            val bindingInterface = object: GenericInterface<Subgeneros>{
                override fun bindData(item:Subgeneros, view: View){
                    val checkBox: CheckBox = view.findViewById(R.id.textviewEleccion)
                    checkBox.text = item.subgenero
                    if (item.id in arraySubgeneros){
                        checkBox.isChecked=true
                        listCheckboxes.add(checkBox)
                        listaIdsSubgeneros.add(item.id)
                    }

                    checkBox.setOnCheckedChangeListener { compoundButton, b ->
                        if (b){
                            listaIdsSubgeneros.add(item.id)
                            listCheckboxes.add(checkBox)
                            listaIdsSubgenerosEliminar.remove(item.id)
                        }
                        if(!b && arraySubgeneros.contains(item.id)){
                            listaIdsSubgeneros.remove(item.id)
                            listaIdsSubgenerosEliminar.add(item.id)
                        }

                    }
                }
            }
            elecctionFiltrosViewModel.getSubGenerosByGenero(idGenero)

            CoroutineScope(Dispatchers.IO).launch {
                var listaSubgeneros = listOf<Subgeneros>()

                elecctionFiltrosViewModel.getSubGenerosByGeneroflow().collectLatest {
                    listaSubgeneros = it
                    runOnUiThread {
                        val mrecyclerview =
                            findViewById<RecyclerView>(R.id.recyclerviewEleccionFiltros)
                        val adapter =  AdapterGeneric<Subgeneros>(listaSubgeneros, R.layout.card_eleccion_filtro, bindingInterface)
                        mrecyclerview.layoutManager = LinearLayoutManager(context)
                        mrecyclerview.adapter = adapter

                    }
                }
            }
        }

        if (tipo.equals("bibliotecas")){
            tipoEnvio = 2;

            val bindingInterface = object: GenericInterface<Biblioteca>{
                override fun bindData(item:Biblioteca, view: View){
                    val checkBox: CheckBox = view.findViewById(R.id.textviewEleccion)
                    checkBox.text = item.nombre
                    if(item.id in arrayBibliotecas){
                        if(!checkBox.isChecked){
                            checkBox.isChecked=true
                            listCheckboxes.add(checkBox)
                            listaIdsBibliotecas.add(item.id)
                        }
                    }

                    checkBox.setOnCheckedChangeListener { compoundButton, b ->
                        if (b){
                            listaIdsBibliotecas.add(item.id)
                            listCheckboxes.add(checkBox)
                        }
                        if(!b && arrayBibliotecas.contains(item.id)){
                            listaIdsBibliotecas.remove(item.id)
                        }
                    }
                }
            }
            elecctionFiltrosViewModel.getBibliotecas()

            CoroutineScope(Dispatchers.IO).launch {
                var listaBibliotecas = listOf<Biblioteca>()

                elecctionFiltrosViewModel.getBibliotecasFlow().collectLatest {
                    listaBibliotecas = it
                    runOnUiThread {
                        val mrecyclerview =
                            findViewById<RecyclerView>(R.id.recyclerviewEleccionFiltros)
                        val adapter =  AdapterGeneric<Biblioteca>(listaBibliotecas, R.layout.card_eleccion_filtro, bindingInterface)
                        mrecyclerview.layoutManager = LinearLayoutManager(context)
                        mrecyclerview.adapter = adapter

                    }
                }
            }
        }

        if(tipo.equals("idiomas")){
            tipoEnvio = 3
            val bindingInterface = object: GenericInterface<Idioma>{
                override fun bindData(item:Idioma, view: View){
                    val checkbox: CheckBox = view.findViewById(R.id.textviewEleccion)
                    checkbox.text = item.idioma.toString()
                    if (item.id in arrayIdiomas){
                        checkbox.isChecked = true
                        listaIdsIdiomas.add(item.id)
                    }
                    checkbox.setOnCheckedChangeListener { compoundButton, b ->
                        if (b){
                            listaIdsIdiomas.add(item.id)
                        }
                        if(!b && arrayIdiomas.contains(item.id)){
                            listaIdsIdiomas.remove(item.id)
                        }
                    }
                }
            }

            elecctionFiltrosViewModel.getIdiomas()

            CoroutineScope(Dispatchers.IO).launch {

                var listaIdiomas = listOf<Idioma>()
                var listaLibros = listOf<LibroPre>()

                elecctionFiltrosViewModel.getIdiomasFlow().collectLatest {
                    listaIdiomas = it
                    runOnUiThread {
                        val mrecyclerview =
                            findViewById<RecyclerView>(R.id.recyclerviewEleccionFiltros)
                        val adapter =  AdapterGeneric<Idioma>(listaIdiomas, R.layout.card_eleccion_filtro, bindingInterface)
                        mrecyclerview.layoutManager = LinearLayoutManager(context)
                        mrecyclerview.adapter = adapter
                    }
                }
            }
        }

        backbutton.setOnClickListener {
            var intentback = Intent(this, FiltrosActivity::class.java)
            if (comesfromgenders != -1){
                intentback.putExtra("comesfromlistaparageneros",comesfromlistaparageneros )
                intentback = Intent(this, FiltroGenerosActivity::class.java)
            }

            if(comesfrom!=-1){
                intentback = Intent(this, ListaFiltradaActivity::class.java)
            }

            if(tipoEnvio == 1){
                elecctionFiltrosViewModel.deleteFiltroSubgeneros(listaIdsSubgenerosEliminar)
                elecctionFiltrosViewModel.saveFiltroSubgenero(listaIdsSubgeneros)
            }
            if(tipoEnvio == 2){
                elecctionFiltrosViewModel.deleteFiltroBiblio()
                elecctionFiltrosViewModel.saveFiltroBibliotecas(listaIdsBibliotecas)
            }

            if (tipoEnvio == 3){
                elecctionFiltrosViewModel.deleteFiltroIdioma()
                elecctionFiltrosViewModel.saveFiltroIdiomas(listaIdsIdiomas)
            }

            this.finish()
            startActivity(intentback)
        }
    }
    override fun onBackPressed() {
        var intentback = Intent(this, FiltrosActivity::class.java)
        if (comesfromgenders != -1){
            intentback = Intent(this, FiltroGenerosActivity::class.java)
        }

        if(comesfrom!=-1){
            intentback = Intent(this, ListaFiltradaActivity::class.java)
        }
        if(tipoEnvio==1){
            elecctionFiltrosViewModel.deleteFiltroSubgeneros(listaIdsSubgenerosEliminar)
            elecctionFiltrosViewModel.saveFiltroSubgenero(listaIdsSubgeneros)
        }
        if(tipoEnvio==2){
            elecctionFiltrosViewModel.deleteFiltroBiblio()
            elecctionFiltrosViewModel.saveFiltroBibliotecas(listaIdsBibliotecas)
        }
        if(tipoEnvio==3){
            elecctionFiltrosViewModel.deleteFiltroIdioma()
            elecctionFiltrosViewModel.saveFiltroIdiomas(listaIdsIdiomas)
        }
        startActivity(intentback)
        this.finish()



    }

}