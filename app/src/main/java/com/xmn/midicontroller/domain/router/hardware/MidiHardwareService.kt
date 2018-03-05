package com.xmn.midicontroller.domain.router.hardware

import com.xmn.midicontroller.domain.model.NrpnValue
import com.xmn.midicontroller.domain.model.MidiControlType
import com.xmn.midicontroller.domain.model.MidiMessage
import com.xmn.midicontroller.midiservice.MidiConstants

class MidiHardwareService(private val midiHardwareGateway: MidiHardwareGateway) {

    private val buffer: ByteArray = ByteArray(3)

    fun send(message: MidiMessage) {
        when (message.type) {
            MidiControlType.CC -> {
                midiHardwareGateway.send(buffer.midi(message.channel, message.number, message.value))
            }
            MidiControlType.NRPN -> {
                midiHardwareGateway.send(buffer.midi(message.channel, 99, NrpnValue.from(message.number).msb))
                midiHardwareGateway.send(buffer.midi(message.channel, 98, NrpnValue.from(message.number).lsb))
                midiHardwareGateway.send(buffer.midi(message.channel, 6, message.value))
            }
        }
    }
}

fun ByteArray.midi(channel: Int, controlNumber: Int, value: Int) = this.apply {
    this[0] = (MidiConstants.STATUS_CONTROL_CHANGE + channel).toByte()
    this[1] = controlNumber.toByte()
    this[2] = value.toByte()
}