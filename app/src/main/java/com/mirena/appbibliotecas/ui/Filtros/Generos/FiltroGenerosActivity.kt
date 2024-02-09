package com.mirena.appbibliotecas.ui.Filtros.Generos

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mirena.appbibliotecas.R
import com.mirena.appbibliotecas.adapters.AdapterFiltroGeneros
import com.mirena.appbibliotecas.adapters.AdapterLibrosObject
import com.mirena.appbibliotecas.objects.Generos
import com.mirena.appbibliotecas.ui.Filtros.FiltrosActivity
import com.mirena.appbibliotecas.ui.ListaLibrosFiltrada.ListaFiltradaActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.properties.Delegates


class FiltroGenerosActivity : AppCompatActivity() {
    private lateinit var  mAdapter: AdapterFiltroGeneros
    private lateinit var filtroGenerosViewModel: FiltroGenerosViewModel
    private lateinit var context: Context
    private lateinit var backButton: ImageView
    private var comesfrom by Delegates.notNull<Int>()
    private var comesfromlistaparageneros by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filtro_generos)
        context = this
        backButton = findViewById(R.id.backButtonFiltersGenero)
        comesfrom = intent.getIntExtra("comesfromlistafiltrada", -1)
        comesfromlistaparageneros = intent.getIntExtra("comesfromlistaparageneros", -1)
        filtroGenerosViewModel = ViewModelProvider(this)[FiltroGenerosViewModel::class.java]

        filtroGenerosViewModel.getGeneros()
        CoroutineScope(Dispatchers.IO).launch {
            var listaGeneros = listOf<Generos>()
            filtroGenerosViewModel.getgenerosflow().collectLatest {
                listaGeneros = it

                runOnUiThread {
                    val mRecyclerview = findViewById<RecyclerView>(R.id.recyclerviewFiltrosGeneros)
                    mRecyclerview.layoutManager = LinearLayoutManager(context)
                    mAdapter = AdapterFiltroGeneros(context,listaGeneros)
                    mRecyclerview.adapter = mAdapter
                }
            }
        }

        backButton.setOnClickListener {
            var intentback = Intent(this, FiltrosActivity::class.java)

            if(comesfrom!=-1 || comesfromlistaparageneros!=-1){
                intentback = Intent(this, ListaFiltradaActivity::class.java)
            }

            startActivity(intentback)
            this.finish()
        }

    }

    override fun onBackPressed() {
        var intentback = Intent(this, FiltrosActivity::class.java)

        if(comesfrom!=-1 || comesfromlistaparageneros!= -1){
            intentback = Intent(this, ListaFiltradaActivity::class.java)
        }

        startActivity(intentback)
        this.finish()

    }




}