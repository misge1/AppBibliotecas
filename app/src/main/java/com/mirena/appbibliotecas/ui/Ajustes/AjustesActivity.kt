package com.mirena.appbibliotecas.ui.Ajustes

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.mirena.appbibliotecas.R
import com.mirena.appbibliotecas.SessionManager
import com.mirena.appbibliotecas.databinding.ActivityAjustesBinding
import com.mirena.appbibliotecas.ui.Account.AccountActivity
import com.mirena.appbibliotecas.ui.Cambiarpass.CambiarPassActivity
import com.mirena.appbibliotecas.ui.EditarPerfil.EditarPerfilActivity
import com.mirena.appbibliotecas.ui.Login.LoginActivity
import com.mirena.appbibliotecas.ui.MainActivity.ScrollingActivity


class AjustesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAjustesBinding
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAjustesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.toolbar_ajustes))
        sessionManager = SessionManager(this)

        binding.includeAjustes.cardviewCambiarpass.setOnClickListener {
            val intent = Intent(this, CambiarPassActivity::class.java)
            startActivity(intent)
        }

        binding.includeAjustes.cardviwEditarperfil.setOnClickListener {
            val intent = Intent(this, EditarPerfilActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_favoritos, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.usuario -> {
                if (sessionManager.fetchAuthToken() == 0){
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                }else{
                    val intent = Intent(this, AccountActivity::class.java)
                    startActivity(intent)
                }
                true
            }
            R.id.home_menu -> {
                val intent = Intent(this, ScrollingActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }
}