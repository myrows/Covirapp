package com.example.covirapp.di

import android.app.Application
import com.example.covirapp.api.generator.NetworkModule
import com.example.covirapp.common.SharedPreferencesModule
import com.example.covirapp.models.GraphicCountryResponse
import com.example.covirapp.models.UsersResponse
import com.example.covirapp.ui.countries.CountryActivity
import com.example.covirapp.ui.countries.CountryFragment
import com.example.covirapp.ui.graphics.PaisesResponseItemFragment
import com.example.covirapp.ui.provinceStats.ProvinceStatsActivity
import com.example.covirapp.ui.users.UserResponseItemFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component( modules = [ NetworkModule::class, SharedPreferencesModule::class ] )
interface ApplicationComponent {
    fun inject(userListFragment: UserResponseItemFragment)
    fun inject(usersResponse: ProvinceStatsActivity)
    fun inject(countryFragment: CountryFragment)
    fun inject(graphicCountryResponse: CountryActivity)
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
