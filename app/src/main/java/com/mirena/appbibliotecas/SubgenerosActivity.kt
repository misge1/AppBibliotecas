package com.mirena.appbibliotecas

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mirena.appbibliotecas.ui.Account.AccountActivity
import com.mirena.appbibliotecas.adapters.AdapterLibros
import com.mirena.appbibliotecas.databinding.ActivitySubgenerosBinding
import com.mirena.appbibliotecas.objects.LibroPre
import com.mirena.appbibliotecas.retrofit.RetrofitInstance
import com.mirena.appbibliotecas.ui.Login.LoginActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SubgenerosActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySubgenerosBinding
    lateinit var librosAdapter: AdapterLibros
    private lateinit var context: Context
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySubgenerosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.toolbar))
        context = this

        val id_genero = intent.getIntExtra("id_genero", 0)
        sessionManager = SessionManager(this)

        CoroutineScope(Dispatchers.IO).launch {
            val callibro = RetrofitInstance.api.getLibrosSubgenero(id_genero)
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

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.upper_menu, menu)
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
            else -> super.onOptionsItemSelected(item)
        }

    }
}