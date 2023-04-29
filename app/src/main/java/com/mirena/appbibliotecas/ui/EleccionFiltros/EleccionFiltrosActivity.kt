package com.mirena.appbibliotecas.ui.EleccionFiltros

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mirena.appbibliotecas.R
import com.mirena.appbibliotecas.adapters.*
import com.mirena.appbibliotecas.objects.Biblioteca
import com.mirena.appbibliotecas.objects.Generos
import com.mirena.appbibliotecas.objects.LibroPre
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class EleccionFiltrosActivity(clickListener: (Generos) -> Unit) : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var elecctionFiltrosViewModel: EleccionFiltrosViewModel
    private lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eleccion_filtros)

        val tipo = intent.getStringExtra("tipo")


        context = this
        recyclerView = findViewById(R.id.recyclerviewEleccionFiltros)
        elecctionFiltrosViewModel = ViewModelProvider(this)[EleccionFiltrosViewModel::class.java]


        if(tipo.equals("generos")){

            val bindingInterface = object: GenericInterface<Generos>{
                override fun bindData(item:Generos, view: View){
                    val textView: TextView = view.findViewById(R.id.textviewEleccion)
                    textView.text = item.genero.toString()
                }
            }

            elecctionFiltrosViewModel.getGeneros()

            CoroutineScope(Dispatchers.IO).launch {

                var listaNoticias = listOf<Generos>()
                var listaLibros = listOf<LibroPre>()

                elecctionFiltrosViewModel.getgenerosflow().collectLatest {
                    listaNoticias = it
                    runOnUiThread {

                        val mrecyclerview =
                            findViewById<RecyclerView>(R.id.recyclerviewEleccionFiltros)
                        val adapter =  AdapterGeneric<Generos>(listaNoticias, R.layout.card_eleccion_filtro, bindingInterface)
                        mrecyclerview.layoutManager = LinearLayoutManager(context)
                        mrecyclerview.adapter = adapter
                    }
                }
            }
        }
        if (tipo.equals("bibliotecas")){

            val bindingInterface = object: GenericInterface<Biblioteca>{
                override fun bindData(item:Biblioteca, view: View){
                    val textView: TextView = view.findViewById(R.id.textviewEleccion)
                    textView.text = item.nombre.toString()
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
    }

}