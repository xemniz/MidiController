package com.xmn.midicontroller.domain.model


data class MidiControl(
        val name: String,
        val channel: Int,
        val number: Int,
        val type: MidiControlType = MidiControlType.CC,
        val range: Range = Range(0, 127),
        val offset: Int = 0,
        val defaultValue: Int = 0
) {
    fun message(value: Int): MidiMessage {
        return MidiMessage(channel, number, type, value)
    }
}

