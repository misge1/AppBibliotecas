package com.mirena.appbibliotecas.ui.Prestamos

import android.os.Bundle
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.mirena.appbibliotecas.R
import com.mirena.appbibliotecas.databinding.ActivityPrestamosRecogerBinding

class PrestamosRecogerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPrestamosRecogerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPrestamosRecogerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.toolbar))

    }
}