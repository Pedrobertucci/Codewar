package com.code.wars

import android.app.Application
import com.code.wars.di.ApplicationComponent
import com.code.wars.di.DaggerApplicationComponent

class MainApplication : Application() {

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerApplicationComponent.builder().build()
    }
}