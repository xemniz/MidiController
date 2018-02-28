package com.xmn.midicontroller.domain.router

import com.xmn.midicontroller.domain.router.hardware.MidiHardwareService
import com.xmn.midicontroller.domain.model.MidiMessage
import com.xmn.midicontroller.domain.router.listeners.MidiListenersHolder

class MidiRouter(private val service: MidiHardwareService) {
    val listenersHolder: MidiListenersHolder = MidiListenersHolder()
    fun send(message: MidiMessage) {
        service.send(message)
        listenersHolder.updateListeners(message)
    }

    fun updateListeners(message: MidiMessage){
        listenersHolder.updateListeners(message)
    }
}