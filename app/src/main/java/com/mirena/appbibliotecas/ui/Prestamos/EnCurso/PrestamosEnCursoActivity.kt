package com.mirena.appbibliotecas.ui.Prestamos.EnCurso

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mirena.appbibliotecas.R
import com.mirena.appbibliotecas.SessionManager
import com.mirena.appbibliotecas.adapters.AdapterPrestamos
import com.mirena.appbibliotecas.databinding.ActivityPrestamosEnCursoBinding
import com.mirena.appbibliotecas.objects.PrestamoUsuario
import com.mirena.appbibliotecas.retrofit.RetrofitInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PrestamosEnCursoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPrestamosEnCursoBinding
    private lateinit var sessionManager: SessionManager
    private lateinit var context: Context
    private lateinit var mAdapter: AdapterPrestamos
    private lateinit var enCursoViewModel: EnCursoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPrestamosEnCursoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.toolbar))

        sessionManager = SessionManager(this)
        binding = ActivityPrestamosEnCursoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.encursoLayout.root.isVisible = false
        binding.imagenVacioEncurso.isVisible = true

        enCursoViewModel = ViewModelProvider(this)[EnCursoViewModel::class.java]

        enCursoViewModel.getCurso()

        context = this

        setSupportActionBar(findViewById(R.id.toolbar))

        CoroutineScope(Dispatchers.IO).launch {

            var listaPrestamos = listOf<PrestamoUsuario>()
            enCursoViewModel.getPCursoLD().collectLatest {
                runOnUiThread {

                    if (it!!.isNotEmpty()){
                        binding.encursoLayout.root.isVisible = false
                        binding.imagenVacioEncurso.isVisible = true
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
}