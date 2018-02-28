package com.xmn.midicontroller.midiservice

import android.media.midi.MidiDeviceInfo

class MidiPortWrapper
/**
 * AddButtonWrapper for a MIDI device and port description.
 * @param info
 * @param portType
 * @param portIndex
 */
(val deviceInfo: MidiDeviceInfo?, private val mType: Int, val portIndex: Int) {
    var selected = false
    private var mString: String? = null

    private fun updateString() {
        if (deviceInfo == null) {
            mString = "- - - - - -"
        } else {
            val sb = StringBuilder()
            var name = deviceInfo.properties
                    .getString(MidiDeviceInfo.PROPERTY_NAME)
            if (name == null) {
                name = deviceInfo.properties
                        .getString(MidiDeviceInfo.PROPERTY_MANUFACTURER) + ", " + deviceInfo.properties
                        .getString(MidiDeviceInfo.PROPERTY_PRODUCT)
            }
            sb.append("#" + deviceInfo.id)
            sb.append(", ").append(name)
            val portInfo = findPortInfo()
            sb.append("[$portIndex]")
            if (portInfo != null) {
                sb.append(", ").append(portInfo.name)
            } else {
                sb.append(", null")
            }
            mString = sb.toString()
        }
    }

    /**
     * @param info
     * @param portIndex
     * @return
     */
    private fun findPortInfo(): MidiDeviceInfo.PortInfo? {
        val ports = deviceInfo!!.ports
        for (portInfo in ports) {
            if (portInfo.portNumber == portIndex && portInfo.type == mType) {
                return portInfo
            }
        }
        return null
    }

    override fun toString(): String {
        if (mString == null) {
            updateString()
        }
        return mString!!
    }

    override fun equals(other: Any?): Boolean {
        if (other == null)
            return false
        if (other !is MidiPortWrapper)
            return false
        val otherWrapper = other as MidiPortWrapper?
        if (portIndex != otherWrapper!!.portIndex)
            return false
        if (mType != otherWrapper.mType)
            return false
        return if (deviceInfo == null) otherWrapper.deviceInfo == null else deviceInfo == otherWrapper.deviceInfo
    }

    override fun hashCode(): Int {
        var hashCode = 1
        hashCode = 31 * hashCode + portIndex
        hashCode = 31 * hashCode + mType
        hashCode = 31 * hashCode + deviceInfo!!.hashCode()
        return hashCode
    }

}

