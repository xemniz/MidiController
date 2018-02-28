package com.xmn.midicontroller.domain

import com.xmn.midicontroller.domain.router.hardware.MidiHardwareGateway
import com.xmn.midicontroller.domain.router.hardware.MidiHardwareService
import com.xmn.midicontroller.domain.router.hardware.midi
import com.xmn.midicontroller.domain.model.MidiControlType
import com.xmn.midicontroller.domain.model.MidiMessage
import com.xmn.midicontroller.domain.model.NrpnValue
import org.junit.Test

import org.junit.Assert.*

class MidiHardwareServiceTest {

    @Test
    fun send() {
        val hardwareGateway = TestMidiHardwareGateway()
        val hardwareService = MidiHardwareService(hardwareGateway)
        val message = MidiMessage(1, 1, MidiControlType.CC, 100)
        hardwareService.send(message)
        val expectedMessages = bytesFrom(message)

        assertEquals(expectedMessages.count(), hardwareGateway.recievedBytes.count())
        expectedMessages.forEachIndexed { index, bytes ->
            assertEquals(bytes[0], hardwareGateway.recievedBytes[index][0])
            assertEquals(bytes[1], hardwareGateway.recievedBytes[index][1])
            assertEquals(bytes[2], hardwareGateway.recievedBytes[index][2])
        }

    }

    @Test
    fun sendNrpn() {
        val hardwareGateway = TestMidiHardwareGateway()
        val hardwareService = MidiHardwareService(hardwareGateway)
        val message = MidiMessage(1, 1, MidiControlType.NRPN, 100)
        hardwareService.send(message)
        val expectedMessages = bytesFrom(message)

        assertEquals(3, hardwareGateway.recievedBytes.count())
        assertEquals(expectedMessages.count(), hardwareGateway.recievedBytes.count())
        expectedMessages.forEachIndexed { index, bytes ->
            assertEquals(bytes[0], hardwareGateway.recievedBytes[index][0])
            assertEquals(bytes[1], hardwareGateway.recievedBytes[index][1])
            assertEquals(bytes[2], hardwareGateway.recievedBytes[index][2])
        }

    }


    private fun bytesFrom(message: MidiMessage): ArrayList<ByteArray> {
        return when (message.type) {
            MidiControlType.CC -> {
                arrayListOf(ByteArray(3).midi(message.channel, message.number, message.value))
            }
            MidiControlType.NRPN -> {
                arrayListOf(
                        ByteArray(3).midi(message.channel, 99, NrpnValue.from(message.number).msb),
                        ByteArray(3).midi(message.channel, 98, NrpnValue.from(message.number).lsb),
                        ByteArray(3).midi(message.channel, 6, message.value)
                )
            }
        }
    }

}

class TestMidiHardwareGateway : MidiHardwareGateway {
    val recievedBytes: ArrayList<ByteArray> = ArrayList()
    override fun send(midiBytes: ByteArray) {
        recievedBytes += ByteArray(3).apply {
            set(0, midiBytes[0])
            set(1, midiBytes[1])
            set(2, midiBytes[2])
        }
    }

}
