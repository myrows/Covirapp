package com.example.covirapp.common

import android.content.Context
import android.content.SharedPreferences
import com.example.covirapp.di.MyApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SharedPreferencesModule {

    @Singleton
    @Provides
    fun provideSharedPreferences(): SharedPreferences {
        val sharedPref = MyApplication?.instance.getSharedPreferences(
            Constantes.SHARED_PREFS_FILE, Context.MODE_PRIVATE)
        return sharedPref
    }

}