package com.example.alertahuellita

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.util.Patterns
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.button.MaterialButton
import java.util.Locale
//IMPORTACION PARA LA CONEXION CON LA BD
import com.example.alertahuellita.CONEXION.ApiClient
import com.example.alertahuellita.CONEXION.TABLAS.RegistroRequest
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class RegistroActivity : AppCompatActivity() {

    //VARIABLES
    private lateinit var clienteUbicacion: FusedLocationProviderClient
    //V-UBICACION
    private var ubicacionValida = false
    //V-ZONA DE SJL
    private val ZONA_CENTRO_LAT = -12.0160
    private val ZONA_CENTRO_LON = -76.9900
    private val ZONA_RADIO_METROS = 9000f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal_registro)
        //PEDIMOS PERMISOS DE UBICACION
        clienteUbicacion = LocationServices.getFusedLocationProviderClient(this)

        val nombres         = findViewById<EditText>(R.id.TextInputLayout_Nombres)
        val apellidoP       = findViewById<EditText>(R.id.TextInputLayout_ApellidoPaterno)
        val apellidoM       = findViewById<EditText>(R.id.TextInputLayout_ApellidoMaterno)
        val dni             = findViewById<EditText>(R.id.TextInputLayout_DNI)
        val telefono        = findViewById<EditText>(R.id.TextInputLayout_Numero_Telefono)
        val correo          = findViewById<EditText>(R.id.TextInputLayout_Email)
        val password        = findViewById<EditText>(R.id.TextInputLayout_Password)
        val rPassword       = findViewById<EditText>(R.id.TextInputLayout_R_Password)

        val btnRegistrar    = findViewById<MaterialButton>(R.id.btn_Registrar)
        val ubicacion       = findViewById<EditText>(R.id.TextInputLayout_Ubicacion)
        val btnUbicacion    = findViewById<ImageButton>(R.id.ImageButton_Ubicacion)

        // UBICACION
        btnUbicacion.setOnClickListener {
            // PEDIMOS PERMISO DE UBICACION
            if (!tengoPermisoUbicacion()) {
                pedirPermisoUbicacion()
                return@setOnClickListener
            }
            try {
                // ABRIMOS LA UBICACIÓN DEL DISPOSITIVO
                clienteUbicacion.lastLocation.addOnSuccessListener { ubicacionActual_Dispositivo ->
                    // SE PUDO OBTENER LA UBICACIÓN
                    if (ubicacionActual_Dispositivo != null) {
                        val Latitud = ubicacionActual_Dispositivo.latitude
                        val Longitud = ubicacionActual_Dispositivo.longitude
                        val direccion_Del_Usuario = obtenerDireccion(Latitud, Longitud)
                        // SE PUDO OBTENER LA DIRECCIÓN EN TEXTO PARA MOSTRAR EN EL CUADRITO Y CALCULAMOS EL PERIMETRO
                        if (!direccion_Del_Usuario.isNullOrBlank()) {
                            ubicacion.setText(direccion_Del_Usuario)
                            // CALCULAMOS LA DISTANCIA DEL USUARIO A LA ZONA PERMITIDA
                            val resultado = FloatArray(1)
                            android.location.Location.distanceBetween(
                                Latitud, Longitud,
                                ZONA_CENTRO_LAT, ZONA_CENTRO_LON,
                                resultado
                            )
                            // SI LA DISTANCIA ES MENOR O IGUAL A LA ZONA PERMITIDA, LA UBICACIÓN ES VÁLIDA
                            val distanciaMetros = resultado[0]
                            if (distanciaMetros <= ZONA_RADIO_METROS) {
                                ubicacionValida = true
                                Toast.makeText(this, "UBICACIÓN VÁLIDA", Toast.LENGTH_LONG).show()
                            } else {
                                ubicacionValida = false
                                Toast.makeText(this, "FUERA DE LA ZONA PERMITIDA", Toast.LENGTH_LONG).show()
                            }

                        } else {
                            Toast.makeText(this, "NO SE PUDO OBTENER LA DIRECCION", Toast.LENGTH_SHORT).show()
                        }

                    } else {
                        Toast.makeText(this, "NO SE PUDO OBTENER LA UBICACIÓN", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: SecurityException) {
                Toast.makeText(this, "PERMISO DE UBICACION NO CONCEDIDO", Toast.LENGTH_SHORT).show()
            }
        }


        // REGISTRO
        btnRegistrar.setOnClickListener {
            val txtNombres      = nombres.text.toString().trim()
            val txtApellidoP    = apellidoP.text.toString().trim()
            val txtApellidoM    = apellidoM.text.toString().trim()
            val txtDni          = dni.text.toString().trim()
            val txtTelefono     = telefono.text.toString().trim()
            val txtCorreo       = correo.text.toString().trim()
            val txtPassword     = password.text.toString()
            val txtRPassword    = rPassword.text.toString()
            val txtUbicacion    = ubicacion.text.toString().trim()

            // VALIDACIONES
            if (txtNombres.isEmpty() || txtApellidoP.isEmpty() || txtApellidoM.isEmpty() ||
                txtDni.isEmpty() || txtTelefono.isEmpty() ||
                txtPassword.isEmpty() || txtRPassword.isEmpty() || txtUbicacion.isEmpty()
            ) {
                Toast.makeText(this, "Completa todos los campos obligatorios", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (txtDni.length != 8 || !txtDni.all { it.isDigit() }) {
                Toast.makeText(this, "El DNI debe tener 8 dígitos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (txtTelefono.length != 9 || !txtTelefono.all { it.isDigit() }) {
                Toast.makeText(this, "El número debe tener 9 dígitos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (txtPassword != txtRPassword) {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (txtCorreo.isNotEmpty() && !Patterns.EMAIL_ADDRESS.matcher(txtCorreo).matches()) {
                Toast.makeText(this, "Correo inválido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (!ubicacionValida) {
                Toast.makeText(this, "No puedes registrarte porque no estás en San Juan de Lurigancho", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            //CONEXION CON LA BD-SPRINTBOOT//
            lifecycleScope.launch {
                val request = RegistroRequest(
                    nombres = txtNombres,
                    apellidoPaterno = txtApellidoP,
                    apellidoMaterno = txtApellidoM,
                    dni = txtDni,
                    telefono = txtTelefono,
                    correo = txtCorreo.ifEmpty { null },
                    password = txtPassword,
                    ubicacion = txtUbicacion
                )
                try {
                    val EnviadoABD = ApiClient.api.registrar(request)

                    if (EnviadoABD.isSuccessful) {
                        Toast.makeText(this@RegistroActivity, "Registro exitoso", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@RegistroActivity, MainActivity::class.java).apply {
                        }
                        startActivity(intent)
                        finish()
                    }
                    else {
                        Toast.makeText(this@RegistroActivity, "Error del servidor: ${EnviadoABD.code()}", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(this@RegistroActivity, "Sin conexión", Toast.LENGTH_SHORT).show()
                }
            }
            /////////////////////////////////////
        }
    }


    private fun tengoPermisoUbicacion(): Boolean {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    private fun pedirPermisoUbicacion() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 100)
    }

    private fun obtenerDireccion(latitud: Double, longitud: Double): String? {
        return try {
            Geocoder(this, Locale.getDefault())
                .getFromLocation(latitud, longitud, 1)
                ?.firstOrNull()
                ?.getAddressLine(0)
        } catch (e: Exception) {
            null
        }
    }

}

