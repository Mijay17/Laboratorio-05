package com.example.labo_04.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
//import com.example.labo_04.data.local.dao.AudioDao
import com.example.labo_04.data.local.dao.GpsGoogleDao
import com.example.labo_04.data.local.dao.GpsSensorsDao
//import com.example.labo_04.data.local.dao.MediaDao
//import com.example.labo_04.data.local.entity.AudioEntity
import com.example.labo_04.data.local.entity.GpsGoogleEntity
import com.example.labo_04.data.local.entity.GpsSensorsEntity
//import com.example.labo_04.data.local.entity.MediaEntity

@Database(
    entities = [GpsGoogleEntity::class, GpsSensorsEntity::class],
    version = 1,
    exportSchema = false
)
abstract class DemoDatabase : RoomDatabase() {

    abstract fun gpsGoogleDao(): GpsGoogleDao
    abstract fun gpsSensorsDao(): GpsSensorsDao
    //abstract fun mediaDao(): MediaDao
    //abstract fun audioDao(): AudioDao

    companion object {
        @Volatile
        private var INSTANCE: DemoDatabase? = null

        fun getDatabase(context: Context): DemoDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DemoDatabase::class.java,
                    "demo_data_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}