plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.alertahuellita"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.alertahuellita"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    //ESTA LIBRERIA ES PARA ACCEDER A LA UBICACION DEL DISPOSITIVO (FREE)
    implementation("com.google.android.gms:play-services-location:21.3.0")

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // ESTA LIBRERIA ES DE COSTO PERO TE DA DISEÑO INTERACTIVO, COMO MOVER HACER ZOOM Y COSAS ASI XD
    implementation("com.google.android.gms:play-services-maps:18.2.0")

    ///FULL SPRINT BOOT (AHORITA NI VERLO LA VERDAD)
    // Retrofit para conectar con Spring Boot
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    // Conversor de JSON a objetos Kotlin
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    // Interceptor para ver lo que se manda por red (opcional pero útil)
    implementation("com.squareup.okhttp3:logging-interceptor:4.10.0")
    // Corrutinas para ejecutar llamadas en segundo plano (obligatorio)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
}