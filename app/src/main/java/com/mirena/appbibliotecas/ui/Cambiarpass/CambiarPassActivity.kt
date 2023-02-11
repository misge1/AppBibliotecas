package com.mirena.appbibliotecas.ui.Cambiarpass

import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.Toast
import androidx.annotation.Nullable
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.mirena.appbibliotecas.R
import com.mirena.appbibliotecas.databinding.ActivityCambiarPassBinding
import org.w3c.dom.Text

class CambiarPassActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCambiarPassBinding

    private lateinit var actualpasslayout: TextInputLayout
    private lateinit var newpasslayout: TextInputLayout
    private lateinit var newpasslayout2: TextInputLayout

    private lateinit var actualpassedit: TextInputEditText
    private lateinit var newpassedit: TextInputEditText
    private lateinit var newspassedit2: TextInputEditText

    private lateinit var updatebutton: Button

    private lateinit var cambiarPassViewModel: CambiarPassViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCambiarPassBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.toolbar))
        /*binding.toolbarLayout.title = title
        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }*/

        cambiarPassViewModel = ViewModelProvider(this)[CambiarPassViewModel::class.java]


        updatebutton = binding.passcontentLayout.updatePassButton
        actualpasslayout = binding.passcontentLayout.prevPassLayout
        newpasslayout = binding.passcontentLayout.newPassLayout
        newpasslayout2 = binding.passcontentLayout.newPass2Layout

        actualpassedit = binding.passcontentLayout.prevPassEdit
        newpassedit = binding.passcontentLayout.newPassEdit
        newspassedit2 = binding.passcontentLayout.newPass2Edit


        updatebutton.setOnClickListener {

            if (actualpassedit.text.isNullOrEmpty()) {

                actualpasslayout.error = "El campo no puede estar vacio"
                actualpasslayout.isErrorEnabled = true

            }else{
                actualpasslayout.isErrorEnabled = false
            }

            if (newpassedit.text.isNullOrEmpty()){
                newpasslayout.error = "El campo no puede estar vacio"
                newpasslayout.isErrorEnabled = true
            }else{
                newpasslayout.isErrorEnabled = false
            }

            if (newspassedit2.text.isNullOrEmpty()){
                newpasslayout2.error = "El campo no puede estar vacio"
                newpasslayout2.isErrorEnabled = true
            }else{
                newpasslayout2.isErrorEnabled = false
            }

            if(!actualpassedit.text.isNullOrEmpty() && !newpassedit.text.isNullOrEmpty() && !newspassedit2.text.isNullOrEmpty()){
                if (oldpassequal() && newpassequal()){

                    Toast.makeText(this, newpassedit.text.toString(), Toast.LENGTH_LONG).show()
                    cambiarPassViewModel.changePass(newpassedit.text.toString(), applicationContext, this)

                }
            }
        }
    }


    fun oldpassequal(): Boolean{

        var devolver = true
        //contraseña nueva la misma que la actual
        if(newpassedit.text.toString() == actualpassedit.text.toString()){
            newpasslayout.error = "La contaseña nueva no puede ser como la actual"
            newpasslayout.isErrorEnabled= true
            devolver = false

        }else{
            newpasslayout.isErrorEnabled= false
            devolver = true
        }

        return devolver

    }


    fun newpassequal(): Boolean{

        var devolver = true
        //contraseña nueva y nueva2 no coinciden
        if (newpassedit.text.toString() != newspassedit2.text.toString()){
            newpasslayout2.error = "Las contraseñas no coinciden"
            newpasslayout2.isErrorEnabled = true
            devolver = false
        }else{
            newpasslayout2.isErrorEnabled = false
            devolver = true
        }

        return devolver

    }


    fun emptyfield(text: Editable): Boolean{
        return text.isEmpty()

    }
}