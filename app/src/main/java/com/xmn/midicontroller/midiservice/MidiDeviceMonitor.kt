package com.xmn.midicontroller.midiservice

import android.media.midi.MidiDeviceInfo
import android.media.midi.MidiDeviceStatus
import android.media.midi.MidiManager
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import java.util.*

class MidiDeviceMonitor private constructor(private val mMidiManager: MidiManager) {
    private val mCallbacks = HashMap<MidiManager.DeviceCallback, Handler>()
    private var mMyDeviceCallback: MyDeviceCallback? = null
    // We only need the workaround for versions before N.
    private val mUseProxy = Build.VERSION.SDK_INT <= Build.VERSION_CODES.M

    // Use an inner class so we do not clutter the API of MidiDeviceMonitor
    // with public DeviceCallback methods.
    protected inner class MyDeviceCallback : MidiManager.DeviceCallback() {

        override fun onDeviceAdded(device: MidiDeviceInfo) {
            // Call all of the locally registered callbacks.
            for ((callback, handler) in mCallbacks) {
                handler.post { callback.onDeviceAdded(device) }
            }
        }

        override fun onDeviceRemoved(device: MidiDeviceInfo) {
            for ((callback, handler) in mCallbacks) {
                handler.post { callback.onDeviceRemoved(device) }
            }
        }

        override fun onDeviceStatusChanged(status: MidiDeviceStatus) {
            for ((callback, handler) in mCallbacks) {
                handler.post { callback.onDeviceStatusChanged(status) }
            }
        }
    }

    init {
        if (mUseProxy) {
            Log.i(TAG, "Running on M so we need to use the workaround.")
            mMyDeviceCallback = MyDeviceCallback()
            mMidiManager.registerDeviceCallback(mMyDeviceCallback,
                    Handler(Looper.getMainLooper()))
        }
    }

    fun registerDeviceCallback(callback: MidiManager.DeviceCallback, handler: Handler) {
        if (mUseProxy) {
            // Keep our own list of callbacks.
            mCallbacks.put(callback, handler)
        } else {
            mMidiManager.registerDeviceCallback(callback, handler)
        }
    }

    fun unregisterDeviceCallback(callback: MidiManager.DeviceCallback) {
        if (mUseProxy) {
            mCallbacks.remove(callback)
        } else {
            // This works on N or later.
            mMidiManager.unregisterDeviceCallback(callback)
        }
    }

    companion object {
        val TAG = "MidiDeviceMonitor"

        private var mInstance: MidiDeviceMonitor? = null

        @Synchronized
        fun getInstance(midiManager: MidiManager): MidiDeviceMonitor {
            if (mInstance == null) {
                mInstance = MidiDeviceMonitor(midiManager)
            }
            return mInstance as MidiDeviceMonitor
        }
    }
}