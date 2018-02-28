package com.xmn.midicontroller.app

import com.xmn.midicontroller.screens.presets.settings.MidiSettingsViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        ApplicationModule::class,
        MidiServiceModule::class
))
interface ApplicationComponent {
    fun inject(midiSettingsViewModel: MidiSettingsViewModel)
}

