package com.xmn.midicontroller.domain.presentation

import android.view.ViewGroup
import com.xmn.midicontroller.domain.model.EmptyNode
import com.xmn.midicontroller.domain.model.Node
import com.xmn.midicontroller.domain.model.NodeGroup

class MidiViewProvider : MidiViewProviderGateway {
    override fun viewGroup(view: ViewGroup, node: NodeGroup, requestAdd: (String) -> Unit): NodeGroupView {
        return when (node) {
            is EmptyNode -> EmptyNodeView(view, node, requestAdd)
            is GridNode -> GridNodeView(view, node, requestAdd)
            else -> throw IllegalArgumentException()
        }
    }

    override fun view(parent: ViewGroup, node: Node): NodeView {
        TODO()
    }

}