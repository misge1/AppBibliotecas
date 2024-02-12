package com.mirena.appbibliotecas.ui.prestamos.EnCurso

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mirena.appbibliotecas.R
import com.mirena.appbibliotecas.SessionManager
import com.mirena.appbibliotecas.adapters.AdapterPrestamosCurso
import com.mirena.appbibliotecas.adapters.AdapterPrestamosRecoger
import com.mirena.appbibliotecas.databinding.ActivityPrestamosEnCursoBinding
import com.mirena.appbibliotecas.objects.PrestamoUsuario
import com.mirena.appbibliotecas.ui.Account.AccountActivity
import com.mirena.appbibliotecas.ui.Login.LoginActivity
import com.mirena.appbibliotecas.ui.MainActivity.ScrollingActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.w3c.dom.Text

class PrestamosEnCursoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPrestamosEnCursoBinding
    private lateinit var sessionManager: SessionManager
    private lateinit var context: Context
    private lateinit var mAdapter: AdapterPrestamosCurso
    private lateinit var enCursoViewModel: EnCursoViewModel
    private lateinit var backbutton: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrestamosEnCursoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(findViewById(R.id.toolbar))
        sessionManager = SessionManager(this)
        binding = ActivityPrestamosEnCursoBinding.inflate(layoutInflater)
        enCursoViewModel = ViewModelProvider(this)[EnCursoViewModel::class.java]
        enCursoViewModel.getCurso()
        context = this
        backbutton = findViewById(R.id.back_button_encursos)

        setSupportActionBar(findViewById(R.id.toolbar))

        CoroutineScope(Dispatchers.IO).launch {

            var listaPrestamos = listOf<PrestamoUsuario>()
            enCursoViewModel.getPCursoLD().collectLatest {
                runOnUiThread {

                    if (it!!.isNotEmpty()){

                        listaPrestamos = it
                        val mrecyclerview = findViewById<RecyclerView>(R.id.recyclerview_prestamos)

                        mrecyclerview.layoutManager = LinearLayoutManager(context)
                        mAdapter =
                            AdapterPrestamosCurso(
                                context,
                                listaPrestamos
                            )
                        mrecyclerview.adapter = mAdapter
                    }
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