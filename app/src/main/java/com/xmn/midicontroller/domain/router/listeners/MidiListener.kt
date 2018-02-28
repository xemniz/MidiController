package com.xmn.midicontroller.domain.router.listeners

import com.xmn.midicontroller.domain.model.MidiControl

interface MidiListener{
    val control: MidiControl
    fun  onMessage(value: Int)
}