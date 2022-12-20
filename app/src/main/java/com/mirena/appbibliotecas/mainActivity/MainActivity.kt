package com.mirena.appbibliotecas.mainActivity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.mirena.appbibliotecas.AccountActivity
import com.mirena.appbibliotecas.R
import com.mirena.appbibliotecas.adapters.AdapterGeneros
import com.mirena.appbibliotecas.objects.Generos
import com.mirena.appbibliotecas.retrofit.RetrofitInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var mainActivityViewModel: MainActivityViewModel
    lateinit var mAdapter: AdapterGeneros
    private lateinit var context:Context
    lateinit var toolbar: MaterialToolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        context = this
        toolbar = findViewById(R.id.toolbar_main)

        mainActivityViewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]


        toolbar.inflateMenu(R.menu.upper_menu)

        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.usuario -> {
                    val intent = Intent(this, AccountActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> super.onOptionsItemSelected(it)
            }

        }
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


}