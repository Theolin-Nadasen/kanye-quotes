package com.theo.kanyequotes

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [quote :: class], version = 1)
abstract class appDatabase : RoomDatabase() {

    abstract fun quotesdao() : QuotesDao

    companion object {

        @Volatile
        private var INSTANCE: appDatabase? = null

        fun getDatabase(context : Context) : appDatabase{

            val tempInstance = INSTANCE

            if (tempInstance != null){
                return tempInstance
            }

            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    appDatabase::class.java,
                    "app_database"
                ).build()

                INSTANCE = instance
                return instance
            }

        }
    }

}