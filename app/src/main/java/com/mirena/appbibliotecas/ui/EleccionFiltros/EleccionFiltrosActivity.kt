package com.mirena.appbibliotecas.ui.EleccionFiltros

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mirena.appbibliotecas.R

class EleccionFiltrosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eleccion_filtros)

        val tipo = intent.getStringExtra("tipo")



    }
}