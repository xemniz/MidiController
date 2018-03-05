package com.xmn.midicontroller.domain.presentation

import android.view.ViewGroup
import com.xmn.midicontroller.domain.model.Node
import com.xmn.midicontroller.domain.model.NodeGroup

interface MidiViewProviderGateway {
    fun view(parent: ViewGroup, node: Node): NodeView
    fun viewGroup(view: ViewGroup, node: NodeGroup, requestAdd: (String) -> Unit): NodeGroupView
}