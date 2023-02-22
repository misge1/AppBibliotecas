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
import com.mirena.appbibliotecas.R
import com.mirena.appbibliotecas.adapters.AdapterLibros
import com.mirena.appbibliotecas.objects.LibroPre
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SearchActivity : AppCompatActivity() {

    private lateinit var searchViewModel: SearchActivityViewModel;
    private lateinit var context: Context
    private lateinit var mAdapter: AdapterLibros
    private lateinit var searchManager: SearchManager
    private lateinit var searchview: SearchView
    private lateinit var mrecyclerview: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        context = this

        searchViewModel = ViewModelProvider(this)[SearchActivityViewModel::class.java]

        searchViewModel.getLibros()

        mrecyclerview =
            findViewById<RecyclerView>(R.id.recyclreviewBusqueda)
        mrecyclerview.isVisible = false
        CoroutineScope(Dispatchers.IO).launch{
            var listalibros = listOf<LibroPre>()

            searchViewModel.getLibrosFlow().collectLatest {
                listalibros = it
                runOnUiThread {

                    mrecyclerview.layoutManager =
                        LinearLayoutManager(context)
                    mAdapter = AdapterLibros(context, listalibros)
                    mrecyclerview.adapter = mAdapter

                }


            }
        }

        searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager;
        searchview = findViewById(R.id.searchviewactivity)
        searchview.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchview.maxWidth = Integer.MAX_VALUE

        // listening to search query text change
        searchview.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                // filter recycler view when query submitted
                mAdapter.filter.filter(query)
                mrecyclerview.isVisible = true
                return false
            }

            override fun onQueryTextChange(query: String): Boolean {
                // filter recycler view when text is changed
                mAdapter.filter.filter(query)
                mrecyclerview.isVisible = true
                return false
            }

        })





    }
}