package com.mirena.appbibliotecas.Account

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
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
    private lateinit var sessionManager: SessionManager

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
                        }
                    }
                }
            }


        }
    }
}