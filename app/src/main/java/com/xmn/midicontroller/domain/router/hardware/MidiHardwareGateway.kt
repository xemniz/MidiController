package com.xmn.midicontroller.domain.router.hardware

interface MidiHardwareGateway {
    fun send(midiBytes: ByteArray)
}