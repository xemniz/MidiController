package com.xmn.midicontroller.domain.model

data class Preset(val name: String, val node: Node) {
    companion object {
        fun new(): Preset {
            return Preset("New preset", Node.empty())
        }
    }
}

