package com.xmn.midicontroller.app

import android.content.Context
import android.content.pm.PackageManager
import android.media.midi.MidiManager
import com.xmn.midicontroller.midiservice.MidiReceiverPortProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MidiServiceModule {

    @Singleton
    @Provides
    fun provideInputMidiManager(context: Context): MidiManager? {
        val mMidiManager: MidiManager?
        if (context.packageManager.hasSystemFeature(PackageManager.FEATURE_MIDI)) {
            mMidiManager = context.getSystemService(Context.MIDI_SERVICE) as MidiManager?
            if (mMidiManager == null) {
                return null
            }
            return mMidiManager
        } else {
            return null
        }
    }

    @Singleton
    @Provides
    fun provideInputPortProvider(midiManager: MidiManager?): MidiReceiverPortProvider = MidiReceiverPortProvider(midiManager!!)
    //todo provide empty input port provider if manager == null
}