package com.mirena.appbibliotecas.ui.Ajustes

import android.content.Intent
import android.os.Bundle
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.mirena.appbibliotecas.R
import com.mirena.appbibliotecas.databinding.ActivityAjustesBinding
import com.mirena.appbibliotecas.ui.Cambiarpass.CambiarPassActivity
import com.mirena.appbibliotecas.ui.EditarPerfil.EditarPerfilActivity


class AjustesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAjustesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAjustesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.toolbar_ajustes))

        binding.includeAjustes.cardviewCambiarpass.setOnClickListener {
            val intent = Intent(this, CambiarPassActivity::class.java)
            startActivity(intent)
        }

        binding.includeAjustes.cardviwEditarperfil.setOnClickListener {
            val intent = Intent(this, EditarPerfilActivity::class.java)
            startActivity(intent)
        }

    }
}