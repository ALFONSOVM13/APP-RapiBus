package com.example.RapiBus

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow
import java.util.Random
import android.graphics.Color
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Activity2 : AppCompatActivity() {
    private lateinit var favoritosButton: Button
    private lateinit var numeroParadaEditText: EditText
    private lateinit var favoritosTableLayout: TableLayout
    private lateinit var tiempoEsperaTextView: TextView
    private lateinit var busquedaButton: Button
    private lateinit var favoritosList: MutableList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_2)

        title = "Paradas"

        // Cargar nombre
        val extras = intent.extras
        val nombreUsuario = extras?.getString("USUARIO")
        val txtNombre = findViewById<TextView>(R.id.txtBienvenido)
        txtNombre.text = getString(R.string.bienvenido, nombreUsuario)

        // Inicializar vistas
        favoritosButton = findViewById(R.id.favoritosButton)
        numeroParadaEditText = findViewById(R.id.numeroParadaEditText)
        favoritosTableLayout = findViewById(R.id.favoritosTableLayout)
        tiempoEsperaTextView = findViewById(R.id.tiempoEsperaTextView)
        busquedaButton = findViewById(R.id.busquedaButton)
        favoritosList = mutableListOf()

        favoritosButton.setOnClickListener {
            val codigoParada = numeroParadaEditText.text.toString().trim()

            if (codigoParada.isNotEmpty()) {
                addCodigoParadaToFavoritos(codigoParada)
                numeroParadaEditText.setText("")
            }
        }

        busquedaButton.setOnClickListener {
            val codigoParada = numeroParadaEditText.text.toString().trim()

            if (codigoParada.isNotEmpty()) {
                val tiempoEspera = obtenerTiempoEspera(codigoParada)
                mostrarTiempoEspera(tiempoEspera)
            }
        }

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
                    // Lógica para el elemento "Incidencias"
                    val intent = Intent(this, Activity4::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
        // Establecer la pestaña activa en función de la actividad actual
        navView.menu.findItem(R.id.menu_item1).isChecked = true

        // Obtener las paradas favoritas del usuario actual
        val userId = FirebaseAuth.getInstance().currentUser?.uid // Obtener el ID del usuario actual
        if (userId != null) {
            val db = FirebaseFirestore.getInstance()
            val favoritosRef = db.collection("usuarios").document(userId).collection("favoritos")
            favoritosRef.get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        val codigoParada = document.getString("codigoParada")
                        if (codigoParada != null) {
                            favoritosList.add(codigoParada)
                            mostrarParadaFavorita(codigoParada)
                        }
                    }
                }
                .addOnFailureListener { e ->
                    // Error al obtener las paradas favoritas del usuario
                }
        }
    }

    private fun addCodigoParadaToFavoritos(codigoParada: String) {
        val row = TableRow(this)

        val button = Button(this)
        button.text = codigoParada
        button.setOnClickListener {
            // Acción cuando se hace clic en el botón de la parada
            val tiempoEspera = obtenerTiempoEspera(codigoParada)
            mostrarTiempoEspera(tiempoEspera)
        }

        row.addView(button)
        favoritosTableLayout.addView(row)

        favoritosList.add(codigoParada)

        // Guardar el código de parada en Firestore junto con el ID del usuario
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            val db = FirebaseFirestore.getInstance()
            val favoritosRef = db.collection("usuarios").document(userId).collection("favoritos")
            favoritosRef.document(codigoParada)
                .set(mapOf("codigoParada" to codigoParada))
                .addOnSuccessListener {
                    // Éxito al guardar la parada en favoritos
                }
                .addOnFailureListener { e ->
                    // Error al guardar la parada en favoritos
                }
        }
    }

    private fun obtenerTiempoEspera(codigoParada: String): Int {
        val random = Random()
        return random.nextInt(10) // Genera un número aleatorio entre 0 y 10
    }

    private fun mostrarTiempoEspera(tiempoEspera: Int) {
        val mensaje: String = if (tiempoEspera < 3) {
            "El tiempo de espera estimado es de: $tiempoEspera minutos.\nEl bus está próximo a llegar al destino"
        } else {
            "El tiempo de espera estimado es de: $tiempoEspera minutos"
        }

        val spannableBuilder = SpannableStringBuilder(mensaje)
        if (tiempoEspera < 3) {
            val startIndex = mensaje.indexOf("El bus está próximo a llegar al destino")
            val endIndex = startIndex + "El bus está próximo a llegar al destino".length
            val colorRojo = ForegroundColorSpan(Color.RED)
            spannableBuilder.setSpan(colorRojo, startIndex, endIndex, 0)
        }

        tiempoEsperaTextView.text = spannableBuilder
    }

    private fun mostrarParadaFavorita(codigoParada: String) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            val row = TableRow(this)

            val button = Button(this)
            button.text = codigoParada
            button.setOnClickListener {
                // Acción cuando se hace clic en el botón de la parada
                val tiempoEspera = obtenerTiempoEspera(codigoParada)
                mostrarTiempoEspera(tiempoEspera)
            }

            row.addView(button)
            favoritosTableLayout.addView(row)
        }
    }
}








