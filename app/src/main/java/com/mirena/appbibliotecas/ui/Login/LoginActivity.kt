package com.mirena.appbibliotecas.ui.Login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.mirena.appbibliotecas.R

class LoginActivity : AppCompatActivity() {

    private lateinit var loginActivityViewModel: LoginActivityViewModel

    private lateinit var emailedit: TextInputEditText
    private lateinit var passedit: TextInputEditText

    private lateinit var emaillayout: TextInputLayout
    private lateinit var passlayout: TextInputLayout

    private lateinit var enterButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginActivityViewModel = ViewModelProvider(this).get(
            LoginActivityViewModel::class.java
        )

        emailedit = findViewById(R.id.email_edit)
        passedit = findViewById(R.id.password_edit)

        emaillayout = findViewById(R.id.email_layout)
        passlayout = findViewById(R.id.password_layout)

        enterButton = findViewById(R.id.enterButton)



        enterButton.setOnClickListener {
            var email = emailedit.text.toString()
            var pass = passedit.text.toString()

            loginActivityViewModel.login(email, pass, applicationContext,this)
        }




    }
}