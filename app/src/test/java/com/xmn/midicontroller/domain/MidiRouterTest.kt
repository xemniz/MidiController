package com.xmn.midicontroller.domain

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.verify
import com.xmn.midicontroller.domain.router.hardware.MidiHardwareService
import com.xmn.midicontroller.domain.model.MidiControl
import com.xmn.midicontroller.domain.model.MidiControlType
import com.xmn.midicontroller.domain.model.MidiMessage
import com.xmn.midicontroller.domain.router.MidiRouter
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class MidiRouterTest {
    val testMidiListener = TestMidiListener(MidiControl("Name", 1, 1))
    private val midiHardwareService = mock<MidiHardwareService>()
    val midiRouter = MidiRouter(midiHardwareService)
    @Before
    fun setUp() {
        midiRouter.listenersHolder.addListener(testMidiListener)
    }

    @Test
    fun send() {
        val message = MidiMessage(1, 1, MidiControlType.CC, 100)
        midiRouter.send(message)
        assertEquals(100, testMidiListener.currentValue)
        verify(midiHardwareService).send(message)
    }

    @Test
    fun update() {
        val message = MidiMessage(1, 1, MidiControlType.CC, 100)
        midiRouter.updateListeners(message)
        assertEquals(100, testMidiListener.currentValue)
        verify(midiHardwareService, never()).send(message)
    }
}