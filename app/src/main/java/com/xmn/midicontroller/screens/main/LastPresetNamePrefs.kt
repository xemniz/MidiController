package com.xmn.midicontroller.screens.main

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.content.edit
import javax.inject.Inject

class LastPresetNamePrefs @Inject constructor(val context: Context) : LastPresetNameGateway {
    val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    override fun put(name: String) {
        prefs.edit {
            putString(KEY, name)
        }
    }

    override fun retreive(): String? {
        return prefs.getString(KEY, null)
    }

    companion object {
        private const val KEY = "com.xmn.midicontroller.screens.main.LastPresetNameGateway"
    }
}