package com.mirena.appbibliotecas.ui.MainActivity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.mirena.appbibliotecas.R
import com.mirena.appbibliotecas.SessionManager
import com.mirena.appbibliotecas.adapters.AdapterGeneros
import com.mirena.appbibliotecas.adapters.AdapterLibros
import com.mirena.appbibliotecas.databinding.ActivityScrollingBinding
import com.mirena.appbibliotecas.objects.Favoritos
import com.mirena.appbibliotecas.objects.Generos
import com.mirena.appbibliotecas.objects.LibroPre
import com.mirena.appbibliotecas.ui.Account.AccountActivity
import com.mirena.appbibliotecas.ui.Filtros.FiltrosActivity
import com.mirena.appbibliotecas.ui.Login.LoginActivity
import com.mirena.appbibliotecas.ui.Search.SearchActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.security.AccessController.getContext
import java.util.StringTokenizer
import kotlin.math.abs


class ScrollingActivity : AppCompatActivity() {

private lateinit var binding: ActivityScrollingBinding
    lateinit var mAdapter: AdapterGeneros
    lateinit var librosAdapter: AdapterLibros
    private lateinit var context: Context
    private var appBarExpanded: Boolean = true
    lateinit var collapsedMenu: Menu
    private lateinit var sessionManager: SessionManager
    //private lateinit var filtros: ExtendedFloatingActionButton
    private lateinit var scrollingActivityViewModel: ScrollingActivityViewModel
    private lateinit var searchFilterButton: ImageButton
    private lateinit var searchbar: SearchView
    private lateinit var listaFavoritos: List<Favoritos>


    @SuppressLint("StringFormatInvalid")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        context = this

        sessionManager = SessionManager(this)
        binding = ActivityScrollingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        searchFilterButton = binding.filterSearchButton
        searchbar = binding.layoutIncludeMain.layoutSearchbarMain!!.searchbarMain
        scrollingActivityViewModel = ViewModelProvider(this)[ScrollingActivityViewModel::class.java]

        scrollingActivityViewModel.getGeneros()
        scrollingActivityViewModel.getLibrosRandom()
        if (sessionManager.fetchAuthToken()!= 0){
            scrollingActivityViewModel.getFavoritosTabla(sessionManager.fetchAuthToken())
        }


        listaFavoritos = listOf<Favoritos>()

        setSupportActionBar(findViewById(R.id.toolbar))

        CoroutineScope(Dispatchers.IO).launch {

            var listaNoticias = listOf<Generos>()

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

        if(sessionManager.fetchAuthToken()!=0){
            CoroutineScope(Dispatchers.IO).launch {
                var listafavs = listOf<Favoritos>()
                scrollingActivityViewModel.getFavoritosTablaFlow().collectLatest {
                    listafavs = it
                    listaFavoritos = it
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
                    librosAdapter = AdapterLibros(context, listaLibros, listaFavoritos)

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

        searchFilterButton.setOnClickListener{
            val intent = Intent(this, FiltrosActivity::class.java)
            startActivity(intent)
            this.finish()
        }

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("TAG token", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast
            val msg = getString(R.string.msg_token_fmt, token)
            Log.d("TAG token", msg)
            Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
        })

        searchbar.setOnQueryTextListener(object : OnQueryTextListener{
            override fun onQueryTextChange(newText: String?): Boolean {
                return false;
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                val intent = Intent(context, SearchActivity::class.java)
                //val savedList = isolateKeyWords(query!!)
                intent.putExtra("query", query)
                searchbar.setQuery("", false);
                searchbar.clearFocus();
                searchbar.isIconified = true;
                startActivity(intent)
                return true;
            }
        })
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

    fun isolateKeyWords(query: String): ArrayList<String>{
        val st = StringTokenizer(query, " ")
        val savedList = arrayListOf<String>()
        val returnList = arrayListOf<String>()
        while(st.hasMoreTokens()){
            savedList.add(st.nextToken())
        }

        for(str in savedList){
            if (str != "a" && str != "en" && str != "de" && str != "el" && str != "un" && str != "una" && str != "los" && str != "las" && str != "del" && str != "que" && str != "lo" && str != "por" && str != "with" && str != "the" && str != "les" && str != "la") {
                returnList.add(str);
            }
        }

        return returnList;
    }



}