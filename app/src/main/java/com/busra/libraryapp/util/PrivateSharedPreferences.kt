package com.busra.libraryapp.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager

class PrivateSharedPreferences {
    companion object{
        private val TIME = "time"
        private var sharedPreferencesPrivate : SharedPreferences? = null

        @Volatile private var instance : PrivateSharedPreferences? = null
        private val lock = Any()

        operator fun invoke(context: Context) : PrivateSharedPreferences = instance ?: synchronized(lock){
            instance ?: makePrivateSharedPrefences(context).also {
                instance = it
            }
        }

        private fun makePrivateSharedPrefences(context: Context):PrivateSharedPreferences{
            sharedPreferencesPrivate = PreferenceManager.getDefaultSharedPreferences(context)
            return PrivateSharedPreferences()
        }
    }
    fun saveTime(time : Long){
        sharedPreferencesPrivate?.edit(commit=true){
            putLong(TIME,time)
        }
    }
    fun getTime() = sharedPreferencesPrivate?.getLong(TIME,0)
}