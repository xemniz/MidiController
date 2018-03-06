package com.xmn.midicontroller.domain.presets

import com.xmn.midicontroller.domain.model.PresetData
import io.reactivex.Flowable
import io.reactivex.processors.BehaviorProcessor
import javax.inject.Inject

class PresetRepository @Inject constructor(val presetsDiskService: PresetsDiskService) {
    private val presetNames: BehaviorProcessor<List<String>> = BehaviorProcessor.create<List<String>>()

    init {
        refreshNames()
    }

    fun presetNames(): Flowable<List<String>> {
        return presetNames
    }

    fun preset(name: String): PresetData {
        return presetsDiskService.preset(name)
    }

    fun savePreset(preset: PresetData) {
        presetsDiskService.save(preset)
        refreshNames()
    }

    private fun refreshNames() {
        presetNames.onNext(presetsDiskService.presetNames())
    }
}