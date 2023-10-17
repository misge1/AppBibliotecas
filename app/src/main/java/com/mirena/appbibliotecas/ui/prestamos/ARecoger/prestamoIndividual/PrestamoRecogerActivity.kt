package com.mirena.appbibliotecas.ui.prestamos.ARecoger.prestamoIndividual

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.mirena.appbibliotecas.R
import com.mirena.appbibliotecas.ui.prestamos.ARecoger.PrestamosRecogerActivity

class PrestamoRecogerActivity : AppCompatActivity() {
    private lateinit var cancelarButton: Button
    private lateinit var materialDialog: MaterialAlertDialogBuilder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prestamo_recoger)

        cancelarButton = findViewById(R.id.cancelarButton)



        materialDialog = MaterialAlertDialogBuilder(this)
            .setTitle(resources.getString(R.string.cancelarPrestamo))
            .setMessage("Al cancelar el préstamo ya no podrás ir a recoger y disfrutar de tu libro. ¿Estás seguro?")

            .setNeutralButton("Cancelar") { dialog, which ->
                dialog.cancel()
            }
            .setPositiveButton("Aceptar") { dialog, which ->
                //eliminar préstamo
                val intent = Intent(this, PrestamosRecogerActivity::class.java)
                startActivity(intent)
            }

        cancelarButton.setOnClickListener {
            materialDialog.show()

        }
    }
}