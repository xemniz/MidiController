package com.xmn.midicontroller.screens.main

import android.arch.lifecycle.MutableLiveData
import com.xmn.midicontroller.domain.model.Node
import com.xmn.midicontroller.domain.model.PresetData
import com.xmn.midicontroller.domain.presets.PresetRepository
import javax.inject.Inject

class OpenPresetUseCase @Inject constructor(private val presetRepository: PresetRepository,
                                            private val lastPresetNameGateway: LastPresetNameGateway) {
    val presetLiveData: MutableLiveData<PresetData> = MutableLiveData()
    val editStateLiveData: MutableLiveData<Boolean> = MutableLiveData()

    init {
        openLastPreset()
    }

    private fun openLastPreset() {
        presetLiveData.value = lastOpenedPreset() ?: PresetData.new()
        editStateLiveData.value = false
    }

    fun openPreset(presetName: String) {
        presetLiveData.value = presetRepository.preset(presetName)
        editStateLiveData.value = false
    }

    fun switchEditState() {
        val currentEditState = editStateLiveData.value ?: false
        editStateLiveData.value = !currentEditState
    }

    private fun lastOpenedPreset(): PresetData? {
        val lastPresetName = lastPresetNameGateway.retreive()
        return when (lastPresetName) {
            null -> null
            else -> presetRepository.preset(lastPresetName)
        }
    }

    fun addNode(parentNodeId: String, node: Node) {

    }
}