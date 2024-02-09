package com.mirena.appbibliotecas.ui.Filtros

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import com.mirena.appbibliotecas.R
import com.mirena.appbibliotecas.databinding.ActivityFiltrosBinding
import com.mirena.appbibliotecas.ui.Account.AccountActivity
import com.mirena.appbibliotecas.ui.EleccionFiltros.EleccionFiltrosActivity
import com.mirena.appbibliotecas.ui.EleccionFiltros.EleccionFiltrosViewModel
import com.mirena.appbibliotecas.ui.Filtros.Generos.FiltroGenerosActivity
import com.mirena.appbibliotecas.ui.ListaLibrosFiltrada.ListaFiltradaActivity
import com.mirena.appbibliotecas.ui.Login.LoginActivity
import com.mirena.appbibliotecas.ui.MainActivity.ScrollingActivity


class FiltrosActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFiltrosBinding
    private lateinit var menuGeneros: CardView
    private lateinit var menuBibliotecas: CardView
    private lateinit var menuIdiomas: CardView
    private lateinit var opcionDisponibles: CheckBox
    private lateinit var elecctionFiltrosViewModel: EleccionFiltrosViewModel
    private lateinit var context: Context
    private lateinit var adapterGeneros: ArrayAdapter<String>
    private lateinit var botonBuscar: Button
    private lateinit var filtrosViewmodel: FiltrosViewModel
    private lateinit var checkDisponibles: CheckBox
    private lateinit var backButton: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFiltrosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        filtrosViewmodel = ViewModelProvider(this)[FiltrosViewModel::class.java]

        setSupportActionBar(findViewById(R.id.toolbar))
        val intentthis = Intent(this, EleccionFiltrosActivity::class.java)
        val intentGeneros = Intent(this, FiltroGenerosActivity::class.java)
        val intentMain = Intent(this, ScrollingActivity::class.java)

        menuGeneros = binding.contentfiltros.cardviewGeneros
        menuBibliotecas = binding.contentfiltros.cardviewBibliotecas
        menuIdiomas = binding.contentfiltros.cardviewIdiomas
        botonBuscar = binding.contentfiltros.botonBuscar
        checkDisponibles = binding.contentfiltros.buttonSoloDisponibles
        backButton = binding.backButtonFilters

        checkDisponibles.isChecked = filtrosViewmodel.getFiltroDisponibles()==1


        menuGeneros.setOnClickListener {
            startActivity(intentGeneros)
            this.finish()
        }

        menuBibliotecas.setOnClickListener {
            intentthis.putExtra("tipo", "bibliotecas")
            startActivity(intentthis)
            this.finish()
        }

        menuIdiomas.setOnClickListener {
            intentthis.putExtra("tipo", "idiomas")
            startActivity(intentthis)
            this.finish()
        }

        botonBuscar.setOnClickListener {
            val intentListaFiltrada = Intent(this, ListaFiltradaActivity::class.java)
            startActivity(intentListaFiltrada)
            this.finish()

        }

        checkDisponibles.setOnClickListener {
            if (checkDisponibles.isChecked){
                filtrosViewmodel.saveFiltroDisponibles(1)
            }else{
                filtrosViewmodel.saveFiltroDisponibles(0)
            }

        }

        backButton.setOnClickListener {
            startActivity(intentMain)
            this.finish()
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this,ScrollingActivity::class.java)
        startActivity(intent)
        this.finish()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_filtros, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.usuario -> {
                if (filtrosViewmodel.getAuthToken() == 0){
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                }else{
                    val intent = Intent(this, AccountActivity::class.java)
                    startActivity(intent)
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }


}