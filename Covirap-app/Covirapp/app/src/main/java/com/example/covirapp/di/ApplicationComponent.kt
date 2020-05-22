package com.example.covirapp.di

import android.app.Application
import com.example.covirapp.api.generator.NetworkModule
import com.example.covirapp.common.SharedPreferencesModule
import com.example.covirapp.ui.users.UserResponseItemFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component( modules = [ NetworkModule::class, SharedPreferencesModule::class ] )
interface ApplicationComponent {
    fun inject(userListFragment : UserResponseItemFragment)
}

class MyApplication: Application() {
    //TODO definimos el @Component para tener acceso a las dependencias en la aplicación
    val appComponent = DaggerApplicationComponent.create()

    companion object {
        lateinit var instance: MyApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}
