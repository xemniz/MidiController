package com.xmn.midicontroller.midiservice

import android.media.midi.MidiDeviceInfo
import android.media.midi.MidiDeviceStatus
import android.media.midi.MidiManager
import android.os.Handler
import android.os.Looper
import android.util.Log
import java.util.HashSet
import kotlin.collections.ArrayList
import kotlin.collections.List
import kotlin.collections.MutableList
import kotlin.collections.filter
import kotlin.collections.forEach

abstract class MidiPortProvider(val midiManager: MidiManager, val type: Int) : MidiManager.DeviceCallback() {
    private var onChange: (List<MidiPortWrapper>) -> Unit = {}
    private var busyPorts = HashSet<MidiPortWrapper>()
    private val midiPortList: MutableList<MidiPortWrapper> = ArrayList<MidiPortWrapper>()
    private var currentWrapper: MidiPortWrapper? = null

    init {
        MidiDeviceMonitor.getInstance(midiManager).registerDeviceCallback(this,
                Handler(Looper.getMainLooper()))

        midiPortList.add(MidiPortWrapper(null, 0, 0))

        val infos = midiManager.devices
        for (info in infos) {
            onDeviceAdded(info)
        }
    }

    fun observePorts(onChange: (List<MidiPortWrapper>) -> Unit) {
        this.onChange = onChange
        onChange(midiPortList)
    }

    fun selectPort(i: Int) {
        currentWrapper = midiPortList[i]
        currentWrapper?.selected = true
        midiPortList.filter { it != currentWrapper }.forEach { it.selected = false }
        onPortSelected(currentWrapper)
    }

    private fun notifyListeners() {
        onChange(midiPortList)
    }

    override fun onDeviceAdded(info: MidiDeviceInfo) {
        val portCount = getInfoPortCount(info)
        for (i in 0 until portCount) {
            val wrapper = MidiPortWrapper(info, type, i)
            midiPortList.add(wrapper)
            Log.i(MidiConstants.TAG, "$wrapper was added to " + this)
            notifyListeners()
        }
    }

    override fun onDeviceRemoved(info: MidiDeviceInfo) {
        val portCount = getInfoPortCount(info)
        for (i in 0 until portCount) {
            val wrapper = MidiPortWrapper(info, type, i)
            val currentWrapper = currentWrapper
            midiPortList.remove(wrapper)
            // If the currently selected port was removed then select no port.
            if (wrapper.equals(currentWrapper)) {
                clearSelection()
            }
            notifyListeners()
            Log.i(MidiConstants.TAG, "$wrapper was removed")
        }
    }

    override fun onDeviceStatusChanged(status: MidiDeviceStatus) {
        // If an input port becomes busy then clear it from the menu.
        // If it becomes free then add it back to the menu.
        if (type == MidiDeviceInfo.PortInfo.TYPE_INPUT) {
            val info = status.deviceInfo
            Log.i(MidiConstants.TAG, "MidiPortSelector.onDeviceStatusChanged status = " + status
                    + ", type = " + type
                    + ", info = " + info)
            // Look for transitions from free to busy.
            val portCount = info.inputPortCount
            for (i in 0 until portCount) {
                val wrapper = MidiPortWrapper(info, type, i)
                if (!wrapper.equals(currentWrapper)) {
                    if (status.isInputPortOpen(i)) { // busy?
                        if (!busyPorts.contains(wrapper)) {
                            // was free, now busy
                            busyPorts.add(wrapper)
                            midiPortList.remove(wrapper)
                            notifyListeners()
                        }
                    } else {
                        if (busyPorts.remove(wrapper)) {
                            // was busy, now free
                            midiPortList.add(wrapper)
                            notifyListeners()
                        }
                    }
                }
            }
        }
    }

    private fun getInfoPortCount(info: MidiDeviceInfo): Int {
        return if (type == MidiDeviceInfo.PortInfo.TYPE_INPUT)
            info.inputPortCount
        else
            info.outputPortCount
    }

    private fun clearSelection() {
        //todo set selected 0
    }


    /**
     * Implement this method to handle the user selecting a port on a device.
     *
     * @param wrapper
     */
    abstract fun onPortSelected(wrapper: MidiPortWrapper?)

    /**
     * Implement this method to clean up any open resources.
     */
    open fun onClose() {}

    /**
     * Implement this method to clean up any open resources.
     */
    fun onDestroy() {
        MidiDeviceMonitor.getInstance(midiManager).unregisterDeviceCallback(this)
    }

    /**
     *
     */
    fun close() {
        onClose()
    }
}