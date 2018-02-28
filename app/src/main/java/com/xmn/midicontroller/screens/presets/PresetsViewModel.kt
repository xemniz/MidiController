package com.xmn.midicontroller.screens.presets

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.xmn.midicontroller.domain.model.Preset
import com.xmn.midicontroller.domain.presets.PresetsDiskService
import io.reactivex.Flowable
import io.reactivex.disposables.Disposable
import io.reactivex.processors.BehaviorProcessor

class PresetsViewModel(presetRepository: PresetRepository): ViewModel(){
    val presetNames: MutableLiveData<List<String>> = MutableLiveData()
    val disposable: Disposable

    init {
        disposable = presetRepository.presetNames().subscribe { presetNames.value = it }
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}

class PresetRepository(val presetsDiskService: PresetsDiskService) {
    private val behaviorProcessor: BehaviorProcessor<List<String>> = BehaviorProcessor.create<List<String>>()

    init {
        refreshNames()
    }

    fun presetNames(): Flowable<List<String>> {
        return behaviorProcessor
    }

    fun preset(name: String) = presetsDiskService.preset(name)

    fun savePreset(preset: Preset){
        presetsDiskService.save(preset)
        refreshNames()
    }

    private fun refreshNames() {
        behaviorProcessor.onNext(presetsDiskService.presetNames())
    }
}
