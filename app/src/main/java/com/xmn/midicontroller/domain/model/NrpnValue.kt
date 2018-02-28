package com.xmn.midicontroller.domain.model

class NrpnValue(val msb: Int, val lsb: Int) {
    fun value() = (msb shl 7) + lsb
    companion object {
        fun from(value: Int): NrpnValue {
            val msb = (value / 128.0f).toInt()
            val lsb = (value - msb * 128.0f).toInt()
            return NrpnValue(msb, lsb)
        }
    }
}