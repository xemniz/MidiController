package com.xmn.midicontroller.domain.serialization

import com.google.gson.Gson
import com.xmn.midicontroller.domain.model.Preset

class NodeSerializer(val gson: Gson) {
    fun serialize(preset: Preset): String {
        return gson.toJson(preset)
    }

    fun deSerialize(presetString: String): Preset {
        return gson.fromJson(presetString, Preset::class.java)
    }
}

