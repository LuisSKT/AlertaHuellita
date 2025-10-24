package com.example.alertahuellita

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class Olvidaste_tu_passwordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal_olvidaste_tu_password)


        // Referencias
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroupOpciones)
        val inputCorreo = findViewById<TextInputEditText>(R.id.Correo_Recuperacion)
        val inputCelular = findViewById<TextInputEditText>(R.id.Celular_Recuperacion)
        val btnEnviar = findViewById<Button>(R.id.btn_Olvidaste_Enviar)
        val btnAtras = findViewById<Button>(R.id.btn_Atras)

        // METODO PARA OCULTAR
        inputCorreo.visibility = View.GONE
        inputCelular.visibility = View.GONE

        // MOSTRAR
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radioCorreo -> {
                    inputCorreo.visibility = View.VISIBLE
                    inputCelular.visibility = View.GONE
                }
                R.id.radioSMS -> {
                    inputCorreo.visibility = View.GONE
                    inputCelular.visibility = View.VISIBLE
                }
            }
        }

        // Acción del botón "Enviar"
        btnEnviar.setOnClickListener {
            when (radioGroup.checkedRadioButtonId) {
                R.id.radioCorreo -> {
                    val correo = inputCorreo.text.toString().trim()
                    if (correo.isEmpty()) {
                        Toast.makeText(this, "Por favor, ingresa tu correo", Toast.LENGTH_SHORT).show()
                    } else if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
                        Toast.makeText(this, "Correo no válido", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Código enviado a $correo", Toast.LENGTH_SHORT).show()
                    }
                }

                R.id.radioSMS -> {
                    val celular = inputCelular.text.toString().trim()
                    if (celular.isEmpty()) {
                        Toast.makeText(this, "Por favor, ingresa tu número de teléfono", Toast.LENGTH_SHORT).show()
                    } else if (celular.length != 9 || !celular.all { it.isDigit() }) {
                        Toast.makeText(this, "Número de teléfono no válido", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Código enviado al número $celular", Toast.LENGTH_SHORT).show()
                    }
                }

                else -> {
                    Toast.makeText(this, "Selecciona una opción para enviar el código", Toast.LENGTH_SHORT).show()
                }
            }
        }

        btnAtras.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }





    }
}




