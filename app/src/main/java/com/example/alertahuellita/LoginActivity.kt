package com.example.alertahuellita
//CTRL + ENTER PARA QUE IMPORTEN LAS LIBRERIAS DE ALGUNOS METODOS CUERDATE
import android.content.Intent                             // Para navegar entre pantallas (Activities)
import android.os.Bundle                                  // Ciclo de vida de Activity (onCreate, etc.)
import android.widget.EditText                            // Referencia a campos de texto editables
import android.widget.TextView                            // Referencia a textos como "¿olvidaste?"
import android.widget.Toast                               // Para mostrar mensajes emergentes (tipo alerta)
import androidx.appcompat.app.AppCompatActivity           // Base para todas las Activities
import androidx.appcompat.widget.SwitchCompat
import com.google.android.material.button.MaterialButton  // Botón de Material Design

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal_login)
        // REFERENCIAS
        val TextInputLayout_dni             = findViewById<EditText>(R.id.et_DNI)
        val TextInputLayout_password        = findViewById<EditText>(R.id.et_Password)
        val MaterialButton_Ingresar         = findViewById<MaterialButton>(R.id.btn_Ingresar)
        val SwitchCompat_RecordarContraseña = findViewById<SwitchCompat>(R.id.sw_RecordarContraseña)
        val TextView_OlvidasteTuContraseña  = findViewById<TextView>(R.id.olvidaste_contraseña)
        val TextView_Registrarme            = findViewById<TextView>(R.id.tv_registrarme)


        // ESTO ES PARA GUARDAR EL USUARIO EN EL CHECKBOX EN UNA VARIABLE CON EL METODO getSharedPreferences Y EN OTRA LO LLAMAS CON UN BOOLEANO
        val Guardadito                          = getSharedPreferences("Datos_login", MODE_PRIVATE)
        var Recordar                            = Guardadito.getBoolean("RECORDAR", false)
        //SI EN CASO YA SE GUARDO AQUI SE AUTORELLENA
        if (Recordar){
            TextInputLayout_dni.setText(Guardadito.getString("DNI", ""))
            TextInputLayout_password.setText(Guardadito.getString("PASSWORD", ""))
            SwitchCompat_RecordarContraseña.isChecked = true
        }
        // ESTO ES EL METODO SETONCLICKLISTENER "TE REDIRIGA A LA PANTALLA PRINCIPAL" INCLUYENDO LA VALIDACION, Y CON EL CHECKBOX SI DESEAS GUARDAR
        MaterialButton_Ingresar.setOnClickListener {
            val dni                     = TextInputLayout_dni.text.toString().trim()
            val password                = TextInputLayout_password.text.toString().trim()
            // VALIDACIONES
            if (dni.length != 8) {
                Toast.makeText(this, "El DNI debe tener 8 dígitos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                Toast.makeText(this, "La contraseña no puede estar vacía", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            // GUARDA Y LIMPIA SEGUN EL CHECBOX
            if(SwitchCompat_RecordarContraseña.isChecked){
                Guardadito.edit()
                    .putBoolean("RECORDAR", true)
                    .putString("DNI", dni)
                    .putString("PASSWORD", password)
                    .apply()
                Toast.makeText(this, "SE GUARDO EL USUARIO", Toast.LENGTH_SHORT).show()
            }else{
                Guardadito.edit()
                    .clear()
                    .apply()
                Toast.makeText(this, "SE BORRO EL USUARIO", Toast.LENGTH_SHORT).show()
            }
            // ME MANDA A LA PANTALLA PRINGIPAL
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        //ESTO ES EL METODO SETONCLICKLISTENER "TE REDIRIGA LA PANTALLA DE RECUPERAR CONTRASEÑA"
        TextView_OlvidasteTuContraseña.setOnClickListener {
            val intent = Intent(this, Olvidaste_tu_passwordActivity::class.java)
            startActivity(intent)
        }


        //ESTO ES EL METODO SETONCLICKLISTENER "TE REDIRIGA LA PANTALLA DE REGISTRO"
        TextView_Registrarme.setOnClickListener {
            val intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
        }
    }




}
