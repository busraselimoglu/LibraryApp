package com.busra.libraryapp.servis

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.busra.libraryapp.model.Book

@Database(entities = arrayOf(Book::class),version = 1)
abstract class BookDatabase : RoomDatabase(){

    abstract  fun bookbaseDao() : BookDAO

    //Singleton
    companion object{
        // Farklı threadlere görünür yapmak için @Volatile koydum
        @Volatile private  var instance : BookDatabase? = null
        private val lock = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(lock){
            instance ?: makeDatabase(context).also {
                instance = it
            }
        }

        private fun makeDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            BookDatabase::class.java,
            "BookDatabase").build()
    }
}