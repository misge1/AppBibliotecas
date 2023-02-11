package com.mirena.appbibliotecas.ui.EditarPerfil

import android.os.Bundle
import android.widget.Button
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.mirena.appbibliotecas.R
import com.mirena.appbibliotecas.databinding.ActivityEditarPerfilBinding
import com.mirena.appbibliotecas.objects.Usuario
import com.mirena.appbibliotecas.ui.Account.AccountViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.w3c.dom.Text

class EditarPerfilActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditarPerfilBinding
    private lateinit var nombre_layout: TextInputLayout
    private lateinit var telefono_layout: TextInputLayout
    private lateinit var domicilio_layout: TextInputLayout
    private lateinit var localidad_layout: TextInputLayout
    private lateinit var cp_layout: TextInputLayout

    private lateinit var nombre_edittext: TextInputEditText
    private lateinit var telefono_editext: TextInputEditText
    private lateinit var domicilio_editext: TextInputEditText
    private lateinit var localidad_editext: TextInputEditText
    private lateinit var cp_editext: TextInputEditText

    private lateinit var editarPerfilViewModel: EditarPerfilViewModel

    private lateinit var aceptar_boton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditarPerfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        editarPerfilViewModel = ViewModelProvider(this)[EditarPerfilViewModel::class.java]
        setSupportActionBar(findViewById(R.id.toolbar_editarperfil))

        editarPerfilViewModel.getUserInfo()

        nombre_layout = binding.editarperfilLayout.editlayoutNombre
        telefono_layout = binding.editarperfilLayout.editlayoutTelefono
        domicilio_layout = binding.editarperfilLayout.editlayoutDomicilio
        localidad_layout = binding.editarperfilLayout.editlayoutLocalidad
        cp_layout = binding.editarperfilLayout.editlayoutCp

        nombre_edittext = binding.editarperfilLayout.edittexNombre
        telefono_editext = binding.editarperfilLayout.editextTelefono
        domicilio_editext = binding.editarperfilLayout.editextDomicilio
        localidad_editext = binding.editarperfilLayout.editextLocalidad
        cp_editext = binding.editarperfilLayout.editextCp

        aceptar_boton = binding.editarperfilLayout.aceptarButton


        CoroutineScope(Dispatchers.IO).launch {
            var usuario = Usuario()
            editarPerfilViewModel.getUserInfoWork().collect {

                usuario = it

                runOnUiThread {
                    nombre_edittext.setText(usuario.nombre)
                    telefono_editext.setText(usuario.telefono)
                    domicilio_editext.setText(usuario.domicilio)
                    localidad_editext.setText(usuario.localidad)
                    cp_editext.setText(usuario.codigo_postal)
                }
            }
        }



        aceptar_boton.setOnClickListener {
            var nombre = nombre_edittext.text.toString()
            var tel = telefono_editext.text.toString()
            var domi = domicilio_editext.text.toString()
            var loc = localidad_editext.text.toString()
            var cp = cp_editext.text.toString()
            editarPerfilViewModel.updateUserInfo(nombre, tel, domi, loc, cp,this, applicationContext)
        }



    }
}