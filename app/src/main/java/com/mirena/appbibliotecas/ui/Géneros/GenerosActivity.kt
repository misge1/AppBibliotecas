package com.mirena.appbibliotecas.ui.Géneros

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mirena.appbibliotecas.R
import com.mirena.appbibliotecas.SessionManager
import com.mirena.appbibliotecas.ui.Account.AccountActivity
import com.mirena.appbibliotecas.adapters.AdapterLibros
import com.mirena.appbibliotecas.adapters.AdapterSubgeneros
import com.mirena.appbibliotecas.databinding.ActivityGenerosBinding
import com.mirena.appbibliotecas.objects.LibroPre
import com.mirena.appbibliotecas.objects.Subgeneros
import com.mirena.appbibliotecas.retrofit.RetrofitInstance
import com.mirena.appbibliotecas.ui.Login.LoginActivity
import com.mirena.appbibliotecas.ui.MainActivity.ScrollingActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class GenerosActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGenerosBinding
    lateinit var librosAdapter: AdapterLibros
    private lateinit var context: Context
    private lateinit var sessionManager: SessionManager
    private lateinit var generosActivityViewModel: GenerosActivityViewModel
    private lateinit var subgenerosAdapter: AdapterSubgeneros
    private lateinit var toolbar: Toolbar
    private lateinit var backButton: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGenerosBinding.inflate(layoutInflater)
        setContentView(binding.root)
        context = this
        val id_genero = intent.getIntExtra("id_genero", 0)
        val nombre_genero = intent.getStringExtra("nombre")
        toolbar = binding.toolbarGeneros
        toolbar.title = nombre_genero
        toolbar.setTitleTextColor(resources.getColor(R.color.text_color_dark))
        setSupportActionBar(findViewById(R.id.toolbarGeneros))
        backButton = binding.backButtonGeneros
        sessionManager = SessionManager(this)
        generosActivityViewModel = ViewModelProvider(this)[GenerosActivityViewModel::class.java]

        generosActivityViewModel.getSubgeneros(id_genero)

        CoroutineScope(Dispatchers.IO).launch {
            var listaSubgeneros = listOf<Subgeneros>()
            generosActivityViewModel.getSubgenerosFlow().collectLatest {
                listaSubgeneros = it

                runOnUiThread {
                    val recyclerViewSubgeneros =
                        findViewById<RecyclerView>(R.id.recyclerview_subgender)
                    recyclerViewSubgeneros.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    subgenerosAdapter = AdapterSubgeneros(context, listaSubgeneros)
                    recyclerViewSubgeneros.adapter = subgenerosAdapter
                }
            }
        }
        CoroutineScope(Dispatchers.IO).launch {
            val callibro = RetrofitInstance.api.getLibrosGenero(id_genero)
            var listaLibros = listOf<LibroPre>()

            runOnUiThread{

                if (callibro.isSuccessful){
                    callibro.body().let {
                        if (it != null){
                            listaLibros = it
                            val recyclerviewlibros =
                                findViewById<RecyclerView>(R.id.recyclerview_libros_subgenero)
                            recyclerviewlibros.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                            librosAdapter = AdapterLibros(context, listaLibros)
                            recyclerviewlibros.adapter = librosAdapter
                        }
                    }
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