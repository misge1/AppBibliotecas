package com.mirena.appbibliotecas

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mirena.appbibliotecas.adapters.AdapterGeneros
import com.mirena.appbibliotecas.databinding.ActivityScrollingBinding
import com.mirena.appbibliotecas.mainActivity.MainActivityViewModel
import com.mirena.appbibliotecas.objects.Generos
import com.mirena.appbibliotecas.retrofit.RetrofitInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ScrollingActivity : AppCompatActivity() {

private lateinit var binding: ActivityScrollingBinding
    private lateinit var mainActivityViewModel: MainActivityViewModel
    lateinit var mAdapter: AdapterGeneros
    private lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainActivityViewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]
        context = this

        binding = ActivityScrollingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.toolbar))
        binding.toolbarLayout.title = "Bibliotecas Atenea"

        CoroutineScope(Dispatchers.IO).launch {
            val calls = RetrofitInstance.api.getGeneros()
            var listaNoticias = listOf<Generos>()

            runOnUiThread{
                if (calls.isSuccessful) {
                    calls.body().let {
                        if (it != null) {
                            listaNoticias = it
                            val mrecyclerview =
                                findViewById<RecyclerView>(R.id.recyclerview_gender)
                            mrecyclerview.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                            mAdapter = AdapterGeneros(context, listaNoticias);
                            mrecyclerview.adapter = mAdapter

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
                val intent = Intent(this, AccountActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }
}