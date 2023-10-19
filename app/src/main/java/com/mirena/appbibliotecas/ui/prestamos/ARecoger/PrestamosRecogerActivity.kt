package com.mirena.appbibliotecas.ui.prestamos.ARecoger

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mirena.appbibliotecas.R
import com.mirena.appbibliotecas.SessionManager
import com.mirena.appbibliotecas.adapters.AdapterPrestamos
import com.mirena.appbibliotecas.databinding.ActivityPrestamosRecogerBinding
import com.mirena.appbibliotecas.objects.PrestamoUsuario
import com.mirena.appbibliotecas.ui.Account.AccountActivity
import com.mirena.appbibliotecas.ui.Login.LoginActivity
import com.mirena.appbibliotecas.ui.MainActivity.ScrollingActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PrestamosRecogerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPrestamosRecogerBinding
    private lateinit var sessionManager: SessionManager
    private lateinit var context: Context
    private lateinit var mAdapter: AdapterPrestamos
    private lateinit var cancelarButton: Button
    private lateinit var precogerviewmodel: ARecogerViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sessionManager = SessionManager(this)
        binding = ActivityPrestamosRecogerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        context = this

        binding.arecogerLayout.root.isVisible = false
        binding.imagenVacioRecoger.isVisible = true

        setSupportActionBar(findViewById(R.id.toolbar))

        precogerviewmodel = ViewModelProvider(this)[ARecogerViewModel::class.java]

        precogerviewmodel.getARecoger()

        CoroutineScope(Dispatchers.IO).launch {
            var listaPrestamos = listOf<PrestamoUsuario>()
            precogerviewmodel.getPRecogerLD().collectLatest {

                runOnUiThread {
                    listaPrestamos = it
                    val mrecyclerview = findViewById<RecyclerView>(R.id.recyclerview_prestamos)

                    mrecyclerview.layoutManager = LinearLayoutManager(context)
                    mAdapter = AdapterPrestamos(context, listaPrestamos)
                    mrecyclerview.adapter = mAdapter
                    binding.arecogerLayout.root.isVisible = true
                    binding.imagenVacioRecoger.isVisible = false
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