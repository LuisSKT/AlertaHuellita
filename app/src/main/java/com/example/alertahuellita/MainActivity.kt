package com.example.alertahuellita

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.ActionBarDrawerToggle
import com.example.alertahuellita.INTERFAZ.AbandonoActivity
import com.example.alertahuellita.INTERFAZ.AccidentesActivity
import com.example.alertahuellita.INTERFAZ.BusquedaActivity
import com.example.alertahuellita.INTERFAZ.EnfermedadesActivity
import com.example.alertahuellita.MENU.ContactanosActivity
import com.example.alertahuellita.MENU.PerfilActivity
import android.widget.FrameLayout


class MainActivity : AppCompatActivity() {
    // VARIABLES
    private lateinit var menuDrawer: DrawerLayout
    private lateinit var vistaDeNavegacion: NavigationView
    private lateinit var barraDeOpciones: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //CONECTAMOS
        menuDrawer = findViewById(R.id.menu_huella)
        vistaDeNavegacion = findViewById(R.id.navegador_huella)
        barraDeOpciones = findViewById(R.id.titulo_huella)

        // METODO DEL TOOLBAR
        setSupportActionBar(barraDeOpciones)

        // METODO TOGGLE OSEA EL ICONO ☰
        val toggle = ActionBarDrawerToggle(
            this,
            menuDrawer,
            barraDeOpciones,
            R.string.abrir_menu,
            R.string.cerrar_menu
        )

        // METODO PARA SINCRONIZAR
        menuDrawer.addDrawerListener(toggle)
        toggle.syncState()


        //METODOS PARA LOS BOTONES AHI LO CAMBIO
        val Accidente = findViewById<android.widget.FrameLayout>(R.id.btnAccidente)
        val Enfermedades = findViewById<android.widget.FrameLayout>(R.id.btnEnfermedades)
        val Abandono = findViewById<android.widget.FrameLayout>(R.id.btnAbandono)
        val Busqueda = findViewById<android.widget.FrameLayout>(R.id.btnBusqueda)


        Accidente.setOnClickListener {
            val intent = Intent(this, AccidentesActivity::class.java)
            startActivity(intent)
        }

        Enfermedades.setOnClickListener {
            val intent = Intent(this, EnfermedadesActivity::class.java)
            startActivity(intent)
        }

        Abandono.setOnClickListener {
            val intent = Intent(this, AbandonoActivity::class.java)
            startActivity(intent)
        }

        Busqueda.setOnClickListener {
            val intent = Intent(this, BusquedaActivity::class.java)
            startActivity(intent)
        }

        // METODOS PARA EL MENU
        val perfil = vistaDeNavegacion.findViewById<LinearLayout>(R.id.perfil)
        val contactanos = vistaDeNavegacion.findViewById<LinearLayout>(R.id.contactanos)
        val ubicacion = vistaDeNavegacion.findViewById<LinearLayout>(R.id.item_ubicacion)
        val nosotros = vistaDeNavegacion.findViewById<LinearLayout>(R.id.item_nosotros)
        val reportes = vistaDeNavegacion.findViewById<LinearLayout>(R.id.item_reportes)
        val donaciones = vistaDeNavegacion.findViewById<LinearLayout>(R.id.item_donaciones)
        val cerrarSesion = vistaDeNavegacion.findViewById<LinearLayout>(R.id.item_cerrar_sesion)

        perfil.setOnClickListener {
            val intent = Intent(this, PerfilActivity::class.java)
            startActivity(intent)
        }

        contactanos.setOnClickListener {
            val intent = Intent(this, ContactanosActivity::class.java)
            startActivity(intent)
        }

        ubicacion.setOnClickListener {
            showMessage("UBICACIÓN")
        }

        nosotros.setOnClickListener {
            showMessage("NOSOTROS")
        }

        reportes.setOnClickListener {
            showMessage("REPORTES")
        }

        donaciones.setOnClickListener {
            showMessage("DONACIONES")
        }


        cerrarSesion.setOnClickListener {
            val builder = android.app.AlertDialog.Builder(this)
            builder.setTitle("Cerrar sesión")
            builder.setMessage("Estás seguro que quieres cerrar sesión?")
            builder.setPositiveButton("Sí") { dialog, _ ->

                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
            builder.setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            builder.show()
            menuDrawer.closeDrawers()
        }
    }

    private fun showMessage(mensaje: String) {
        android.widget.Toast.makeText(this, mensaje, android.widget.Toast.LENGTH_SHORT).show()
        menuDrawer.closeDrawers()
    }


}
