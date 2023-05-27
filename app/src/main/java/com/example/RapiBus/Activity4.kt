package com.example.RapiBus

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class Activity4 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_4)

        title = "Incidencias"

        // Habilitar la barra de navegación
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_item1 -> {
                    // Lógica para el elemento "Paradas"
                    val intent = Intent(this, Activity2::class.java)
                    startActivity(intent)
                    true
                }
                R.id.menu_item2 -> {
                    // Lógica para el elemento "Mapa"
                    val intent = Intent(this, Activity3::class.java)
                    startActivity(intent)
                    true
                }
                R.id.menu_item3 -> {
                    // No es necesario realizar ninguna acción aquí para activar la pestaña correspondiente
                    true
                }
                else -> false
            }
        }

        val menu = navView.menu
        val incidenciasItem = menu.findItem(R.id.menu_item3)
        incidenciasItem.isChecked = true

        // Agregar la lógica para enviar las incidencias por correo
        val btnEnviar: Button = findViewById(R.id.btnEnviar)
        val etAsunto: EditText = findViewById(R.id.etAsunto)
        val etDescripcion: EditText = findViewById(R.id.etDescripcion)
        val etCorreo: EditText = findViewById(R.id.etCorreo)
        val etNumeroContacto: EditText = findViewById(R.id.etNumeroContacto)

        btnEnviar.setOnClickListener {
            val asunto = etAsunto.text.toString()
            val descripcion = etDescripcion.text.toString()
            val correo = etCorreo.text.toString()
            val numeroContacto = etNumeroContacto.text.toString()

            val emailIntent = Intent(Intent.ACTION_SEND)
            emailIntent.type = "text/plain"
            emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf("alfonsovengoechea@gmail.com")) // Reemplaza con tu dirección de correo electrónico personal
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, asunto)
            emailIntent.putExtra(
                Intent.EXTRA_TEXT,
                "Descripción: $descripcion\nCorreo: $correo\nNúmero de contacto: $numeroContacto"
            )

            try {
                startActivity(Intent.createChooser(emailIntent, "Enviar correo"))
            } catch (e: Exception) {
                // Manejo de errores
            }
        }
    }
}


