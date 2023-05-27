package com.example.RapiBus

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    val PASSWORD = "12345"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Registrar lanzamiento app Google Analytics
        val analytics:FirebaseAnalytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()
        bundle.putString("msg", "Todo OK!")
        analytics.logEvent("Inicializacion", bundle)

    }

    fun ingresarUsuario(view: View) {
        //Acceso a cajas de texto
        val edtUsuario = findViewById<EditText>(R.id.edtNombre)
        val edtPassword = findViewById<EditText>(R.id.edtPassword)
        val textoUsuario = edtUsuario.text.toString()
        val textoPassword = edtPassword.text.toString()
        //Validacion de blancos
        if (textoUsuario.isEmpty()) {
            edtUsuario.error = "El nombre está en blanco!"
            return
        }
        if (textoPassword.isEmpty()) {
            edtPassword.error = "Password en blanco!"
            return
        }
        //Validacion de login
        val login = FirebaseAuth.getInstance().signInWithEmailAndPassword(textoUsuario, textoPassword)
        login.addOnCompleteListener {
            if (it.isSuccessful) {
                //Login exitoso
                Toast.makeText(this, "Ingreso exitoso!", Toast.LENGTH_SHORT).show()
                //Invocacion de una actividad
                val intento = Intent(this, Activity2::class.java)
                intento.putExtra("USUARIO", textoUsuario)
                startActivity(intento)
            } else {
                //Login no exitoso
                edtPassword.error = it.exception?.message.toString()
            }
        }

    }

    fun registrarUsuario(view: View) {
        //Acceso a cajas de texto
        val edtUsuario = findViewById<EditText>(R.id.edtNombre)
        val edtPassword = findViewById<EditText>(R.id.edtPassword)
        val textoUsuario = edtUsuario.text.toString()
        val textoPassword = edtPassword.text.toString()
        //Validacion de blancos
        if (textoUsuario.isEmpty()) {
            edtUsuario.error = "El nombre está en blanco!"
            return
        }
        if (textoPassword.isEmpty()) {
            edtPassword.error = "Password en blanco!"
            return
        }
        //Validacion de registro
        val login = FirebaseAuth.getInstance().createUserWithEmailAndPassword(textoUsuario, textoPassword)
        login.addOnCompleteListener {
            if (it.isSuccessful) {
                //Registro exitoso
                Toast.makeText(this, "Ingreso exitoso!", Toast.LENGTH_SHORT).show()
                //Invocacion de una actividad
                val intento = Intent(this, Activity2::class.java)
                intento.putExtra("USUARIO", textoUsuario)
                startActivity(intento)
            }
            else {
                //Registro no exitoso
                edtPassword.error = it.exception?.message.toString()
            }
        }
    }


}