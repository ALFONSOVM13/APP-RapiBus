package com.example.RapiBus

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomnavigation.BottomNavigationView

class Activity3 : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mapView: MapView
    private lateinit var googleMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_3)

        title = "Mapa de Paradas"

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
                    // Aquí debes abrir la actividad que muestra el mapa
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
        val menu = navView.menu
        val incidenciasItem = menu.findItem(R.id.menu_item2)
        incidenciasItem.isChecked = true

        // Obtener el MapView y llamar al método onCreate
        mapView = findViewById(R.id.map_view)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onMapReady(map: GoogleMap?) {
        if (map != null) {
            googleMap = map

            // Configurar el mapa aquí
            val latLng = LatLng(37.422, -122.084)
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10f))
            googleMap.addMarker(MarkerOptions().position(latLng).title("Marker en Mountain View"))
        }
    }
}

