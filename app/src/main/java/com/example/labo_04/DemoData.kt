package com.example.labo_04

import android.app.Application
import com.example.labo_04.data.local.FileStorageManager
import com.example.labo_04.data.local.DemoDatabase
//import com.example.labo_04.data.repository.AudioRepository
import com.example.labo_04.data.repository.GpsRepository
//import com.example.labo_04.data.repository.MediaRepository
import com.example.labo_04.data.session.SessionManager

class DemoData : Application() {

    // Inicialización perezosa: solo se crea al primer acceso

    // La BD se crea una sola vez cuando se accede por primera vez
    val database by lazy { DemoDatabase.getDatabase(this) }
    val fileStorage by lazy { FileStorageManager(this) }


// El repositorio se construye sobre la misma instancia de la BD
    val gpsRepository by lazy {
       GpsRepository(database.gpsGoogleDao(), database.gpsSensorsDao())
    }
    // SessionManager también vive aquí para no duplicarlo en MainActivity
    val sessionManager by lazy { SessionManager(this) }
//    val mediaRepository by lazy {
//        MediaRepository(database.mediaDao(), fileStorage)
//    }
//    val audioRepository by lazy {
//        AudioRepository(database.audioDao(), fileStorage)
//    }
}