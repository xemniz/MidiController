package com.xmn.midicontroller.app

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class App : Application() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
        lateinit var component: ApplicationComponent
    }

    override fun onCreate() {
        super.onCreate()
        initializeDagger()
    }

    fun initializeDagger() {
        component = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
        context = this
    }
}