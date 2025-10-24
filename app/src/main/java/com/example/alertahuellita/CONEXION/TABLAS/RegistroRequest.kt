package com.example.alertahuellita.CONEXION.TABLAS

data class RegistroRequest(
    val nombres: String,
    val apellidoPaterno: String,
    val apellidoMaterno: String,
    val dni: String,
    val telefono: String,
    val correo: String?,
    val password: String,
    val ubicacion: String
)