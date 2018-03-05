package com.xmn.midicontroller.domain.presets

import com.xmn.midicontroller.app.App
import com.xmn.midicontroller.domain.model.Preset
import com.xmn.midicontroller.domain.serialization.NodeSerializer
import java.io.File
import javax.inject.Inject

class PresetsDiskService @Inject constructor(val nodeSerializer: NodeSerializer) {
    private val folderPath = "${App.context.filesDir.absolutePath}/presets/"
    private fun filePath(name: String) = "$folderPath$name.json"

    private fun serialize(currentPreset: Preset) = nodeSerializer.serialize(currentPreset)
    private fun deserialize(currentPreset: String) = nodeSerializer.deSerialize(currentPreset)

    private fun checkFolder() {
        val folder = File(folderPath)
        if (!folder.exists()) {
            folder.mkdir()
        }
    }

    fun presetNames(): List<String> {
        val listFiles = File(folderPath).listFiles() ?: emptyArray<File>()
        return listFiles.map { it.name.replace(".json", "") }
    }

    fun preset(name: String): Preset {
        checkFolder()

        val file = File(filePath(name))
        if (file.exists()) {
            val presetData: Preset = deserialize(file.readText())
            return presetData
        } else
            throw IllegalArgumentException()
    }

    fun save(currentPreset: Preset) {
        checkFolder()

        val presetText: String = serialize(currentPreset)

        val file = File(filePath(currentPreset.name))
        if (!file.exists()) {
            file.createNewFile()
        }
        file.bufferedWriter().use { out ->
            out.write(presetText)
        }
    }

    fun clear() {
        File(folderPath).deleteRecursively()
    }
}