package com.xmn.midicontroller.domain.router.listeners

import com.xmn.midicontroller.domain.model.MidiMessage

class MidiListenersHolder {
    private val listeners: ArrayList<MidiListener> = ArrayList()

    fun addListener(midiListener: MidiListener) {
        listeners += midiListener
    }

    fun removeListener(midiListener: MidiListener) {
        listeners -= midiListener
    }

    fun updateListeners(message: MidiMessage) {
        listeners.forEach {
            if (it.control.channel == message.channel
                    && it.control.number == message.number
                    && it.control.type == message.type)
                it.onMessage(message.value)
        }
    }
}

