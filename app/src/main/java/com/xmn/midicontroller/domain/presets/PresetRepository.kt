package com.xmn.midicontroller.domain.presets

import com.xmn.midicontroller.domain.model.Preset
import io.reactivex.Flowable
import io.reactivex.processors.BehaviorProcessor
import javax.inject.Inject

class PresetRepository @Inject constructor(val presetsDiskService: PresetsDiskService) {
    private val behaviorProcessor: BehaviorProcessor<List<String>> = BehaviorProcessor.create<List<String>>()

    init {
        refreshNames()
    }

    fun presetNames(): Flowable<List<String>> {
        return behaviorProcessor
    }

    fun preset(name: String): Preset {
        return presetsDiskService.preset(name)
    }

    fun savePreset(preset: Preset) {
        presetsDiskService.save(preset)
        refreshNames()
    }

    private fun refreshNames() {
        behaviorProcessor.onNext(presetsDiskService.presetNames())
    }
}