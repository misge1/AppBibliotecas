package com.mirena.appbibliotecas.ui.Favoritos

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mirena.appbibliotecas.ui.Login.LoginActivity
import com.mirena.appbibliotecas.R
import com.mirena.appbibliotecas.SessionManager
import com.mirena.appbibliotecas.ui.Account.AccountActivity
import com.mirena.appbibliotecas.adapters.AdapterLibros
import com.mirena.appbibliotecas.databinding.ActivityFavoritosBinding

import com.mirena.appbibliotecas.objects.LibroPre
import com.mirena.appbibliotecas.retrofit.RetrofitInstance
import com.mirena.appbibliotecas.ui.MainActivity.ScrollingActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FavoritosActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoritosBinding
    private lateinit var sessionManager: SessionManager
    private lateinit var favoritosViewModel: FavoritosViewModel
    private lateinit var backbutton: ImageView
    private lateinit var mAdapter: AdapterLibros
    private lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFavoritosBinding.inflate(layoutInflater)
        setContentView(binding.root)
        context = this
        sessionManager = SessionManager(this)
        setSupportActionBar(findViewById(R.id.toolbar))
        backbutton = binding.backButtonFavoritos
        favoritosViewModel = ViewModelProvider(this)[FavoritosViewModel::class.java]
        favoritosViewModel.getFavoritos()

        CoroutineScope(Dispatchers.IO).launch {
            var listafaovritos = listOf<LibroPre>()
            favoritosViewModel.getFavoritosFlow().collectLatest {
                listafaovritos= it

                runOnUiThread {
                    val mrecyclerview = findViewById<RecyclerView>(R.id.recyclerviewFavoritos)

                    mrecyclerview.layoutManager = LinearLayoutManager(context)
                    mAdapter = AdapterLibros(context, listafaovritos)
                    mrecyclerview.adapter = mAdapter
                }
            }
        }

        backbutton.setOnClickListener {
            this.finish()
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