package com.mirena.appbibliotecas.ui.Search

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.mirena.appbibliotecas.R
import com.mirena.appbibliotecas.adapters.AdapterLibros
import com.mirena.appbibliotecas.objects.LibroPre
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.StringTokenizer

class SearchActivity : AppCompatActivity() {

    private lateinit var searchViewModel: SearchActivityViewModel;
    private lateinit var context: Context
    private lateinit var mAdapter: AdapterLibros
    private lateinit var searchManager: SearchManager
    private lateinit var searchview: SearchView
    private lateinit var mrecyclerview: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        context = this
        searchViewModel = ViewModelProvider(this)[SearchActivityViewModel::class.java]
        swipeRefreshLayout = findViewById<SwipeRefreshLayout>(R.id.swipeResfresBusqueda)
        var query = intent.getStringExtra("query")

        if (query != null) {
            val savedList = isolateKeyWords(query)
            searchViewModel.getLibrosBusqueda(savedList)
            CoroutineScope(Dispatchers.IO).launch{
                var listalibros = listOf<LibroPre>()

                searchViewModel.getLibrosBusquedaFlow().collectLatest {
                    listalibros = it
                    runOnUiThread {
                        val mrecyclerview =
                            findViewById<RecyclerView>(R.id.recyclreviewBusqueda)
                        mrecyclerview.layoutManager =
                            LinearLayoutManager(context)
                        mAdapter = AdapterLibros(context, listalibros)
                        mrecyclerview.adapter = mAdapter

                    }
                }
            }
        }

        searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager;
        searchview = findViewById(R.id.searchviewactivity)

        swipeRefreshLayout.setOnRefreshListener {
            if (query != null) {
                val savedList = isolateKeyWords(query)
                searchViewModel.getLibrosBusqueda(savedList)
                CoroutineScope(Dispatchers.IO).launch{
                    var listalibros = listOf<LibroPre>()

                    searchViewModel.getLibrosBusquedaFlow().collectLatest {
                        listalibros = it
                        runOnUiThread {
                            val mrecyclerview =
                                findViewById<RecyclerView>(R.id.recyclreviewBusqueda)
                            mrecyclerview.layoutManager =
                                LinearLayoutManager(context)
                            mAdapter = AdapterLibros(context, listalibros)
                            mrecyclerview.adapter = mAdapter

                        }
                    }
                }
            }

            swipeRefreshLayout.isRefreshing = false;
        }


        // listening to search query text change
        searchview.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                intent.putExtra("query", query)
                searchview.clearFocus()
                startActivity(intent)
                finish()

                return true;
            }
            override fun onQueryTextChange(query: String): Boolean {
                // filter recycler view when text is changed
                return false
            }

        })
    }

    override fun onBackPressed() {
        this.finish()
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