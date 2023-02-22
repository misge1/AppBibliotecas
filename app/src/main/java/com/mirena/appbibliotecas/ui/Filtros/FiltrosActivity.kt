package com.mirena.appbibliotecas.ui.Filtros

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.mirena.appbibliotecas.R
import com.mirena.appbibliotecas.databinding.ActivityFiltrosBinding
import com.mirena.appbibliotecas.ui.Search.SearchActivity
import com.mirena.appbibliotecas.ui.EleccionFiltros.EleccionFiltrosActivity

class FiltrosActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFiltrosBinding
    private lateinit var searchviewfiltros: SearchView
    private lateinit var generoslinearlayout: LinearLayout
    private lateinit var subgeneroslinear: LinearLayout
    private lateinit var bibliotecalinearlayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFiltrosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.toolbar))
        searchviewfiltros = findViewById(R.id.searchviewFiltros)
        generoslinearlayout = findViewById(R.id.generosLinearLayout)
        subgeneroslinear = findViewById(R.id.subgenerosLinearLayout)
        bibliotecalinearlayout = findViewById(R.id.bibliotecaLinearLayout)

        val intentfiltros = Intent(this, EleccionFiltrosActivity::class.java)

        searchviewfiltros.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }

        generoslinearlayout.setOnClickListener {
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
        }


    }
}