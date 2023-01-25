package com.mirena.appbibliotecas.ui.Account

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import com.mirena.appbibliotecas.ui.Favoritos.FavoritosActivity
import com.mirena.appbibliotecas.ui.Login.LoginActivity
import com.mirena.appbibliotecas.R
import com.mirena.appbibliotecas.SessionManager
import com.mirena.appbibliotecas.objects.Usuario
import com.mirena.appbibliotecas.retrofit.RetrofitInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
    private lateinit var boton_edit: ImageButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)

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
        boton_edit = findViewById(R.id.edit_information)

        sessionManager = SessionManager(this)

        CoroutineScope(Dispatchers.IO).launch {
            val call = RetrofitInstance.api.getUser(sessionManager.fetchAuthToken().toInt())

            var usuario = Usuario()
            runOnUiThread {
                if (call.isSuccessful) {
                    call.body().let {
                        if (it != null) {
                            usuario = it
                            titulo_textview.text = usuario.nombre
                            nombre_textview.text = usuario.nombre
                            alta_textview.text = usuario.fecha_alta
                            telefono_textview.text = usuario.telefono
                            email_textview.text = usuario.email
                            domicilio_textview.text = usuario.domicilio
                            localidad_textview.text = usuario.localidad
                            cp_textview.text = usuario.codigo_postal

                        }
                    }
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

        boton_edit.setOnClickListener {

        }
    }
}