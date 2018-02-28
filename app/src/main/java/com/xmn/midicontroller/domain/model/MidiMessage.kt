package com.xmn.midicontroller.domain.model

data class MidiMessage(val channel: Int, val number: Int, val type: MidiControlType, val value: Int)