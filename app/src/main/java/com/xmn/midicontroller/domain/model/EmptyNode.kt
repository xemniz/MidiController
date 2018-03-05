package com.xmn.midicontroller.domain.model

import com.xmn.midicontroller.domain.presentation.PresetNode

class EmptyNode : NodeGroup() {
    override val id: String = "empty"
    override val presetNode: PresetNode = object : PresetNode {}
}