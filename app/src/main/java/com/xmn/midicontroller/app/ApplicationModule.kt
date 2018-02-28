package com.xmn.midicontroller.app

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module()
class ApplicationModule(private val app: Context) {
    @Provides
    @Singleton
    fun provideApplicationContext(): Context = app
}