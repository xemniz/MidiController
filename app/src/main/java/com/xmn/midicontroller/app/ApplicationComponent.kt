package com.xmn.midicontroller.app

import android.content.Context
import com.xmn.midicontroller.domain.presentation.MidiViewProvider
import com.xmn.midicontroller.domain.presentation.MidiViewProviderGateway
import com.xmn.midicontroller.domain.presets.PresetRepository
import com.xmn.midicontroller.domain.presets.PresetServiceModule
import com.xmn.midicontroller.screens.choosepreset.PresetsViewModel
import com.xmn.midicontroller.screens.main.*
import com.xmn.midicontroller.screens.settings.MidiSettingsViewModel
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        ApplicationModule::class,
        MidiServiceModule::class,
        PresetServiceModule::class,
        MidiViewProviderModule::class,
        MainModule::class
))
interface ApplicationComponent {
    fun inject(midiSettingsViewModel: MidiSettingsViewModel)
    fun presetViewModel(): PresetsViewModel
    fun mainViewModel(): MainViewModel
}

@Module
class MainModule {
    @Provides
    fun provideMainViewModel(openPresetUseCase: OpenPresetUseCase, midiViewProviderGateway: MidiViewProviderGateway): MainViewModel {
        return MainViewModel(openPresetUseCase, midiViewProviderGateway)
    }
    @Provides
    fun provideOpenPresetUseCase(presetRepository: PresetRepository, lastPresetNameGateway: LastPresetNameGateway): OpenPresetUseCase {
        return OpenPresetUseCase(presetRepository, lastPresetNameGateway)
    }

    @Provides
    fun provideLastPresetNameGateway(context: Context): LastPresetNameGateway {
        return LastPresetNamePrefs(context)
    }
}

@Module
class MidiViewProviderModule {
    @Provides
    fun provideMidiViewProvider(): MidiViewProviderGateway {
        return MidiViewProvider()
    }
}

