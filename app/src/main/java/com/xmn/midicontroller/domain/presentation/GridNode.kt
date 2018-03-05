package com.xmn.midicontroller.domain.presentation

import com.xmn.midicontroller.domain.model.NodeGroup
import java.util.*

class GridNode(override val presetNode: PresetNode) : NodeGroup() {
    override val id: String = UUID.randomUUID().toString()
}