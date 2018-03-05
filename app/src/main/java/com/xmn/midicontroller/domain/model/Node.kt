package com.xmn.midicontroller.domain.model

import com.xmn.midicontroller.domain.presentation.PresetNode

interface Node {
    val id: String
    val presetNode: PresetNode

    companion object {
        fun empty(): EmptyNode {
            return EmptyNode()
        }
    }
}