package com.mirena.appbibliotecas.ui.Subgeneros

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mirena.appbibliotecas.R
import com.mirena.appbibliotecas.SessionManager
import com.mirena.appbibliotecas.adapters.AdapterLibros
import com.mirena.appbibliotecas.databinding.ActivitySubgenerosBinding
import com.mirena.appbibliotecas.objects.LibroPre
import com.mirena.appbibliotecas.ui.Account.AccountActivity
import com.mirena.appbibliotecas.ui.Login.LoginActivity
import com.mirena.appbibliotecas.ui.MainActivity.ScrollingActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SubgenerosActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySubgenerosBinding
    private lateinit var subgenerosActivityViewModel: SubgenerosActivityViewModel
    private lateinit var context: Context
    private lateinit var adapterLibros: AdapterLibros
    private lateinit var backButton: ImageView
    private lateinit var sessionManager: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubgenerosBinding.inflate(layoutInflater)
        val idSubgenero = intent.getIntExtra("idSubgenero", -1)
        val nombreSubgenero = intent.getStringExtra("nombreSubgenero")
        setContentView(binding.root)
        binding.toolbarSubgeneros.title = nombreSubgenero
        binding.toolbarSubgeneros.setTitleTextColor(resources.getColor(R.color.text_color_dark))
        setSupportActionBar(binding.toolbarSubgeneros)
        context = this
        backButton = binding.backButtonSubgeneros
        subgenerosActivityViewModel = ViewModelProvider(this)[SubgenerosActivityViewModel::class.java]
        subgenerosActivityViewModel.getLibrosSubgeneros(idSubgenero)
        sessionManager = SessionManager(this)

        CoroutineScope(Dispatchers.IO).launch {
            var listaLibros = listOf<LibroPre>()
            subgenerosActivityViewModel.getLibrosSubgeneroFlow().collectLatest {
                listaLibros = it

                runOnUiThread {
                    val recyclerViewLibros =
                        findViewById<RecyclerView>(R.id.recyclerviewLibrosSubgenero)
                    recyclerViewLibros.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    adapterLibros = AdapterLibros(context, listaLibros)
                    recyclerViewLibros.adapter = adapterLibros
                }
            }
        }

        backButton.setOnClickListener {
            finish()
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