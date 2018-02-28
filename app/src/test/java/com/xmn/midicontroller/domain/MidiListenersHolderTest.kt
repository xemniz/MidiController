package com.xmn.midicontroller.domain

import com.xmn.midicontroller.domain.model.MidiControl
import com.xmn.midicontroller.domain.model.MidiControlType
import com.xmn.midicontroller.domain.model.MidiMessage
import com.xmn.midicontroller.domain.router.listeners.MidiListener
import com.xmn.midicontroller.domain.router.listeners.MidiListenersHolder
import org.junit.Test
import org.junit.Assert.*

class MidiListenersHolderTest {

    @Test
    fun canListenSendedMessages() {
        val midiRouter = MidiListenersHolder()
        val midiControl = MidiControl("name", 1, 1, MidiControlType.CC)
        val midiListener1 = fakeMidiListener(midiControl)
        midiRouter.addListener(midiListener1)
        midiRouter.updateListeners(MidiMessage(1, 1, MidiControlType.CC, 100))
        assertEquals(100, midiListener1.currentValue)
    }

    @Test
    fun canListenSendedMessagesMultipleListeners() {
        val midiRouter = MidiListenersHolder()
        val midiControl = MidiControl("name", 1, 1, MidiControlType.CC)
        val midiListener1 = fakeMidiListener(midiControl)
        val midiListener2 = fakeMidiListener(midiControl)
        midiRouter.addListener(midiListener1)
        midiRouter.addListener(midiListener2)
        midiRouter.updateListeners(midiControl.message(100))
        assertEquals(100, midiListener1.currentValue)
        assertEquals(100, midiListener2.currentValue)
    }

    @Test
    fun removedListenersDontReceiveValue() {
        val midiRouter = MidiListenersHolder()
        val midiControl = MidiControl("name", 1, 1, MidiControlType.CC)
        val midiListener1 = fakeMidiListener(midiControl)
        midiRouter.addListener(midiListener1)
        midiRouter.updateListeners(midiControl.message(100))
        assertEquals(100, midiListener1.currentValue)

        midiRouter.removeListener(midiListener1)
        midiRouter.updateListeners(midiControl.message(200))
        assertEquals(100, midiListener1.currentValue)
    }


    companion object {
        private fun fakeMidiControl() = MidiControl("Name", 0, 0)
        private fun fakeMidiListener(midiControl: MidiControl = fakeMidiControl()) = TestMidiListener(midiControl)
    }
}

class TestMidiListener(override val control: MidiControl) : MidiListener {
    var currentValue = 0
    override fun onMessage(value: Int) {
        currentValue = value
    }

}
