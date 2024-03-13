package com.mirena.appbibliotecas.ui.nuevaBiblioteca

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.button.MaterialButton
import com.mirena.appbibliotecas.R
import com.mirena.appbibliotecas.SessionManager
import com.mirena.appbibliotecas.databinding.ActivityAnyadirBibliotecaBinding
import com.mirena.appbibliotecas.objects.BibliotecaPersonal

class AnyadirBiblioteca : AppCompatActivity() {

    private lateinit var binding: ActivityAnyadirBibliotecaBinding
    private lateinit var backButton: ImageView
    private lateinit var cancelarButton: MaterialButton
    private lateinit var crearButton: MaterialButton
    private lateinit var anyadirBibliotecaViewModel: AnyadirBibliotecaViewModel
    private lateinit var editTextBiblio: EditText
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnyadirBibliotecaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(findViewById(R.id.toolbar))

        backButton = binding.backButtonAnyadirBiblioteca
        anyadirBibliotecaViewModel = ViewModelProvider(this)[AnyadirBibliotecaViewModel::class.java]
        editTextBiblio = binding.anyadirBibliotecaLayout.editTextTituloBiblio
        cancelarButton = binding.anyadirBibliotecaLayout.cancelarBiblioButtpon
        crearButton = binding.anyadirBibliotecaLayout.crearBiblioButtpon
        sessionManager = SessionManager(this)

        backButton.setOnClickListener {
            this.finish()
        }

        cancelarButton.setOnClickListener {
            this.finish()
        }


        crearButton.setOnClickListener {
            if(!editTextBiblio.text.isNullOrEmpty()){
                var biblioteca = BibliotecaPersonal(null, editTextBiblio.text.toString(), sessionManager.fetchAuthToken() )
                anyadirBibliotecaViewModel.addBibliotecaPersonal(biblioteca)
            }
        }
    }
}