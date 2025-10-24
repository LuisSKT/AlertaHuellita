package com.example.alertahuellita.CONEXION

import com.example.alertahuellita.CONEXION.TABLAS.RegistroRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    // REGISTRO DE USUARIO
    @POST("api/registro")
    suspend fun registrar(@Body body: RegistroRequest): Response<Void>




}
