package com.xmn.midicontroller.midiservice

import android.media.midi.*
import android.util.Log
import com.xmn.midicontroller.midiservice.MidiConstants
import com.xmn.midicontroller.midiservice.MidiPortProvider
import com.xmn.midicontroller.midiservice.MidiPortWrapper
import ru.xmn.common.extensions.log
import java.io.IOException

class MidiReceiverPortProvider(midiManager: MidiManager)
    : MidiPortProvider(midiManager, MidiDeviceInfo.PortInfo.TYPE_INPUT) {

    val receiver: MidiReceiver?
        get() = mInputPort

    private var mInputPort: MidiInputPort? = null
    private var mOpenDevice: MidiDevice? = null

    override fun onPortSelected(wrapper: MidiPortWrapper?) {
        close()
        val info = wrapper?.deviceInfo
        if (info != null) {
            midiManager.openDevice(info, { device ->
                if (device == null) {
                    Log.e(MidiConstants.TAG, "could not open " + info)
                } else {
                    mOpenDevice = device
                    mInputPort = mOpenDevice!!.openInputPort(
                            wrapper.portIndex)
                    if (mInputPort == null) {
                        Log.e(MidiConstants.TAG, "could not open input port on " + info)
                    }
                }
            }, null)
        }
    }

    override fun onClose() {
        try {
            if (mInputPort != null) {
                Log.i(MidiConstants.TAG, "MidiInputPortSelector.onClose() - close port")
                mInputPort!!.close()
            }
            mInputPort = null
            if (mOpenDevice != null) {
                mOpenDevice!!.close()
            }
            mOpenDevice = null
        } catch (e: IOException) {
            Log.e(MidiConstants.TAG, "cleanup failed", e)
        }

        super.onClose()
    }

    fun midiSend(buffer: ByteArray, count: Int = 3, timestamp: Long = System.nanoTime()) {
        try {
            receiver?.send(buffer, 0, count, timestamp)
        } catch (e: IOException) {
            log("mKeyboardReceiverSelector.send() failed " + e)
        }
    }
}