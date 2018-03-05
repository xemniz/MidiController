package com.xmn.midicontroller.screens.choosepreset

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.xmn.midicontroller.domain.presets.PresetRepository
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class PresetsViewModel @Inject constructor(presetRepository: PresetRepository): ViewModel(){
    val presetNames: MutableLiveData<List<String>> = MutableLiveData()
    private val disposable: Disposable

    init {
        disposable = presetRepository.presetNames().subscribe { presetNames.value = it }
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}