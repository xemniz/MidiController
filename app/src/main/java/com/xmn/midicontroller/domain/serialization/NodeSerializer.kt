package com.xmn.midicontroller.domain.serialization

import com.google.gson.Gson
import com.xmn.midicontroller.domain.model.PresetData
import javax.inject.Inject

class NodeSerializer @Inject constructor(val gson: Gson) {
    fun serialize(preset: PresetData): String {
        return gson.toJson(preset)
    }

    fun deSerialize(presetString: String): PresetData {
        return gson.fromJson(presetString, PresetData::class.java)
    }
}

