package com.mirena.appbibliotecas.ui.Account

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.button.MaterialButton
import com.mirena.appbibliotecas.ui.Favoritos.FavoritosActivity
import com.mirena.appbibliotecas.ui.Login.LoginActivity
import com.mirena.appbibliotecas.R
import com.mirena.appbibliotecas.ui.MainActivity.ScrollingActivity
import com.mirena.appbibliotecas.SessionManager
import com.mirena.appbibliotecas.adapters.AdapterBibliotecaPersonal
import com.mirena.appbibliotecas.databinding.ActivityAccountBinding
import com.mirena.appbibliotecas.objects.BibliotecaPersonal
import com.mirena.appbibliotecas.objects.Usuario
import com.mirena.appbibliotecas.ui.Ajustes.AjustesActivity
import com.mirena.appbibliotecas.ui.EditarPerfil.EditarPerfilActivity
import com.mirena.appbibliotecas.ui.nuevaBiblioteca.AnyadirBiblioteca
import com.mirena.appbibliotecas.ui.prestamos.EnCurso.PrestamosEnCursoActivity
import com.mirena.appbibliotecas.ui.prestamos.ARecoger.PrestamosRecogerActivity
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class AccountActivity : AppCompatActivity() {
    private lateinit var titulo_textview: TextView
    private lateinit var nombre_textview: TextView
    private lateinit var alta_textview: TextView
    private lateinit var telefono_textview: TextView
    private lateinit var email_textview: TextView
    private lateinit var domicilio_textview: TextView
    private lateinit var localidad_textview: TextView
    private lateinit var cp_textview: TextView
    private lateinit var sessionManager: SessionManager
    private lateinit var cerrar_sesion_button: Button
    private lateinit var boton_favorito: ImageButton
    private lateinit var boton_recogida: ImageButton
    private lateinit var boton_encurso: ImageButton
    private lateinit var boton_editar: Button
    private lateinit var foto_perfil: CircleImageView
    private lateinit var binding: ActivityAccountBinding
    private lateinit var accountViewModel: AccountViewModel
    private lateinit var backbutton: ImageView
    private lateinit var anayadirBiblioteca: MaterialButton
    private lateinit var context: Context
    private lateinit var adapterBibliotecaPersonal: AdapterBibliotecaPersonal

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)

        binding = ActivityAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.toolbar_account))
        accountViewModel = ViewModelProvider(this)[AccountViewModel::class.java]
        context = this

        accountViewModel.getUserInfo()
        titulo_textview = findViewById(R.id.titulo_nombre)
        nombre_textview = findViewById(R.id.nombre_text)
        alta_textview = findViewById(R.id.alta_text)
        telefono_textview = findViewById(R.id.telefono_text)
        email_textview = findViewById(R.id.email_text)
        domicilio_textview = findViewById(R.id.domicilio_text)
        localidad_textview = findViewById(R.id.localidad_text)
        cp_textview = findViewById(R.id.cp_text)
        cerrar_sesion_button = findViewById(R.id.cerrar_sesion_button)
        boton_favorito = findViewById(R.id.boton_favoritos)
        boton_recogida = findViewById(R.id.boton_recogida)
        boton_encurso = findViewById(R.id.boton_encurso)
        boton_editar = findViewById(R.id.boton_editar)
        foto_perfil = findViewById(R.id.circle_image)
        sessionManager = SessionManager(this)
        backbutton = findViewById(R.id.back_button_account)
        anayadirBiblioteca = binding.anyadirBiblioteca
        accountViewModel.getBibliosPersonales(sessionManager.fetchAuthToken())



        backbutton.setOnClickListener {
            this.finish()
        }

        CoroutineScope(Dispatchers.IO).launch {
            var usuario = Usuario()
            accountViewModel.getUserInfoWork().collect{
                usuario = it

                runOnUiThread {
                    titulo_textview.text= usuario.nombre
                    nombre_textview.text = usuario.nombre
                    alta_textview.text = usuario.fecha_alta
                    telefono_textview.text = usuario.telefono
                    email_textview.text = usuario.email
                    domicilio_textview.text = usuario.domicilio
                    localidad_textview.text = usuario.localidad
                    cp_textview.text = usuario.codigo_postal
                    Picasso.get()
                        .load(usuario.foto)
                        .into(foto_perfil)
                }
            }
        }

        CoroutineScope(Dispatchers.IO).launch {


            accountViewModel.getBibliosPersonalesFlow().collectLatest{
                var bibliotecasPersonales = listOf<BibliotecaPersonal>()
                bibliotecasPersonales = it

                runOnUiThread {
                    val recyclerView = binding.recyclerviewMisBibliotecas
                    recyclerView.layoutManager =  LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    adapterBibliotecaPersonal = AdapterBibliotecaPersonal(context, bibliotecasPersonales)
                    recyclerView.adapter = adapterBibliotecaPersonal
                }
            }
        }

        cerrar_sesion_button.setOnClickListener {
            sessionManager.deleteAuthToken()
            val intent = Intent(this, LoginActivity::class.java)
            finish()
            startActivity(intent)
        }

        boton_favorito.setOnClickListener {
            val intent = Intent(this, FavoritosActivity::class.java)
            startActivity(intent)
        }

        boton_recogida.setOnClickListener {
            val intent = Intent(this, PrestamosRecogerActivity::class.java)
            startActivity(intent)
        }

        boton_encurso.setOnClickListener {
            val intent = Intent(this, PrestamosEnCursoActivity::class.java)
            startActivity(intent)
        }

        boton_editar.setOnClickListener {
            val intent = Intent(this, EditarPerfilActivity::class.java)
            startActivity(intent)
        }

        anayadirBiblioteca.setOnClickListener {
            val intent = Intent(this, AnyadirBiblioteca::class.java)
            startActivity(intent)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_account, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
           R.id.home_button_menu -> {
               val intent = Intent(this, ScrollingActivity::class.java)
               startActivity(intent)
               true
           }
            R.id.configuration_button_menu -> {
                val intent = Intent(this, AjustesActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }
}