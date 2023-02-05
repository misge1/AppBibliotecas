package com.mirena.appbibliotecas.ui.MainActivity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mirena.appbibliotecas.R
import com.mirena.appbibliotecas.SessionManager
import com.mirena.appbibliotecas.ui.Account.AccountActivity
import com.mirena.appbibliotecas.adapters.AdapterGeneros
import com.mirena.appbibliotecas.adapters.AdapterLibros
import com.mirena.appbibliotecas.databinding.ActivityScrollingBinding
import com.mirena.appbibliotecas.objects.Generos
import com.mirena.appbibliotecas.objects.LibroPre
import com.mirena.appbibliotecas.retrofit.RetrofitInstance
import com.mirena.appbibliotecas.ui.Login.LoginActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.math.abs

class ScrollingActivity : AppCompatActivity() {

private lateinit var binding: ActivityScrollingBinding
    lateinit var mAdapter: AdapterGeneros
    lateinit var librosAdapter: AdapterLibros
    private lateinit var context: Context
    private var appBarExpanded: Boolean = true
    lateinit var collapsedMenu: Menu
    lateinit var searchView: SearchView
    private lateinit var sessionManager: SessionManager
    private lateinit var scrollingActivityViewModel: ScrollingActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        context = this

        sessionManager = SessionManager(this)
        binding = ActivityScrollingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        searchView = binding.searchViewScrolling

        scrollingActivityViewModel = ViewModelProvider(this)[ScrollingActivityViewModel::class.java]

        scrollingActivityViewModel.getGeneros()
        scrollingActivityViewModel.getLibrosRandom()

        setSupportActionBar(findViewById(R.id.toolbar))

        CoroutineScope(Dispatchers.IO).launch {

            var listaNoticias = listOf<Generos>()
            var listaLibros = listOf<LibroPre>()

            scrollingActivityViewModel.getgenerosflow().collectLatest {
                listaNoticias = it
                runOnUiThread {

                    val mrecyclerview =
                        findViewById<RecyclerView>(R.id.recyclerview_gender)
                    mrecyclerview.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    mAdapter = AdapterGeneros(context, listaNoticias);
                    mrecyclerview.adapter = mAdapter

                }
            }


        }

        CoroutineScope(Dispatchers.IO).launch{
            var listaLibros = listOf<LibroPre>()
            scrollingActivityViewModel.getLibrosFlow().collectLatest {
                listaLibros = it

                runOnUiThread {
                    val recyclerviewlibros =
                        findViewById<RecyclerView>(R.id.recyclerview_novedades)
                    recyclerviewlibros.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    librosAdapter = AdapterLibros(context, listaLibros)
                    recyclerviewlibros.adapter = librosAdapter
                }

            }

        }

        binding.appBar.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (abs(verticalOffset) -appBarLayout.totalScrollRange == 0){
                //collapsed
                appBarExpanded = false
                invalidateOptionsMenu()

            }else{
                //expanded
                appBarExpanded = true
                invalidateOptionsMenu()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.upper_menu, menu)
        collapsedMenu = menu
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