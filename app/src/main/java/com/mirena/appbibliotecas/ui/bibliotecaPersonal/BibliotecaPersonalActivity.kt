package com.mirena.appbibliotecas.ui.bibliotecaPersonal

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mirena.appbibliotecas.R
import com.mirena.appbibliotecas.databinding.ActivityBibliotecaPersonalBinding

class BibliotecaPersonalActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBibliotecaPersonalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBibliotecaPersonalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.toolbar))
    }
}