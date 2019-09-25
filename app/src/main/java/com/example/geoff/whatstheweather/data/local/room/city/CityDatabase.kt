package com.example.geoff.whatstheweather.data.local.room.city

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.geoff.whatstheweather.data.model.city.City

@Database(entities = [(City::class)], version = 1)
abstract class CityDatabase : RoomDatabase() {
    abstract fun cityDao(): CityDao

    companion object {
        @Volatile
        private var INSTANCE: CityDatabase? = null

        fun getDatabase(context: Context): CityDatabase {
            val tempInstance =
                INSTANCE
            if (tempInstance != null)
                return tempInstance
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CityDatabase::class.java,
                    "City_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

//    private class CityDatabaseCallback(
//        private val scope: CoroutineScope
//    ) : RoomDatabase.Callback() {
//
//        override fun onOpen(db: SupportSQLiteDatabase) {
//            super.onOpen(db)
//            Log.d("Weather", "Instance: " + INSTANCE.toString())
//            INSTANCE?.let { cityDatabase ->
//                scope.launch {
//                    val dao = cityDatabase.cityDao()
//
//                    dao.deleteAllCities()
//
//                    var city = City(2, "Paris", Sys("FR"), Coord(2.970f, 56.8675f))
//                    dao.insertCity(city)
//                    city = City(3, "Marseille", Sys("FR"), Coord(89.274f, 3.9869f))
//                    dao.insertCity(city)
//                    Log.d("Weather", "inserted: " + dao.getAllCities().value.toString())
//                }
//            }
//        }
//    }
}