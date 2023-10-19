package com.mirena.appbibliotecas.ui.prestamos.adevolver

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mirena.appbibliotecas.R
import com.mirena.appbibliotecas.SessionManager
import com.mirena.appbibliotecas.adapters.AdapterPrestamos
import com.mirena.appbibliotecas.databinding.ActivityPrestamosAdevolverBinding
import com.mirena.appbibliotecas.objects.PrestamoUsuario
import com.mirena.appbibliotecas.ui.Account.AccountActivity
import com.mirena.appbibliotecas.ui.Login.LoginActivity
import com.mirena.appbibliotecas.ui.MainActivity.ScrollingActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PrestamosAdevolverActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPrestamosAdevolverBinding
    private lateinit var sessionManager: SessionManager
    private lateinit var context: Context
    private lateinit var mAdapter: AdapterPrestamos
    private lateinit var devolverviewModel: AdevolverViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPrestamosAdevolverBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.toolbar))

        context = this
        sessionManager = SessionManager(this)
        setSupportActionBar(findViewById(R.id.toolbar))

        binding.devolverlayout.root.isVisible = false
        binding.imagenVacioDevolver.isVisible = true

        devolverviewModel = ViewModelProvider(this)[AdevolverViewModel::class.java]

        devolverviewModel.getPDevolver()

        CoroutineScope(Dispatchers.IO).launch {


            var listaPrestamos = listOf<PrestamoUsuario>()

            devolverviewModel.getPDevolverLD().collectLatest {
                if (it.isNotEmpty()){
                    listaPrestamos = it

                    runOnUiThread {
                        binding.devolverlayout.root.isVisible = true
                        binding.imagenVacioDevolver.isVisible = false
                        listaPrestamos = it
                        val mrecyclerview = findViewById<RecyclerView>(R.id.recyclerview_prestamos)
                        mrecyclerview.layoutManager = LinearLayoutManager(context)
                        mAdapter = AdapterPrestamos(context, listaPrestamos)
                        mrecyclerview.adapter = mAdapter

                    }
                }
            }
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