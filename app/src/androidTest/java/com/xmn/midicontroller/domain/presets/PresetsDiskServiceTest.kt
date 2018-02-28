package com.xmn.midicontroller.domain.presets

import com.xmn.midicontroller.domain.model.Preset
import com.xmn.midicontroller.domain.nodes.ControlNode
import com.xmn.midicontroller.domain.nodes.GridGroupNode
import com.xmn.midicontroller.domain.nodes.GridItemNode
import org.junit.Assert.assertEquals
import org.junit.Test

class PresetsDiskServiceTest {
    val presetServiceModule = PresetServiceModule()
    val presetDiskService: PresetsDiskService = presetServiceModule.providePresetService(presetServiceModule.provideNodeSerializer())
    val preset = Preset("name", GridGroupNode(mutableListOf(
            GridItemNode(mutableListOf(ControlNode("", "")), 1, 1, 1, 1),
            GridItemNode(mutableListOf(ControlNode("", "")), 1, 1, 1, 1),
            GridItemNode(mutableListOf(ControlNode("", "")), 1, 1, 1, 1)
    )))

    @Test
    fun presetNames() {
        presetDiskService.clear()
        presetDiskService.save( preset.copy(name = "1"))
        presetDiskService.save( preset.copy(name = "2"))
        assertEquals(listOf("1", "2"), presetDiskService.presetNames())
    }

    @Test
    fun save() {
        presetDiskService.clear()
        presetDiskService.save(preset)
        val result = presetDiskService.preset(preset.name)
        assertEquals(preset.toString(), result.toString())
    }
}