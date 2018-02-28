package com.xmn.midicontroller.domain.presentation

import android.view.View
import android.view.ViewGroup
import ru.xmn.common.extensions.views

const val NODE_TAG = 1
const val GROUP_VIEW_TAG = 2

interface PresetNode
interface PresetNodeGroup : PresetNode {
    val childs: MutableList<PresetNode>

}

interface NodeView {
    val view: View
}

interface INodeGroupView : NodeView {
    fun addChild(childNodeView: NodeView)
    fun removeChild(node: PresetNode)
    fun addChild(node: PresetNode)

    fun drawChildes(node: PresetNodeGroup, midiViewProvider: MidiViewProvider) {
        node.childs.forEach { childNode ->
            when (childNode) {
                is PresetNodeGroup -> {
                    val viewGroup = midiViewProvider.viewGroup(childNode).apply {
                        view.setTag(NODE_TAG, childNode)
                    }
                    addChild(viewGroup)
                    viewGroup.drawChildes(childNode, midiViewProvider)
                }
                else -> {
                    addChild(midiViewProvider.view(childNode).apply {
                        view.setTag(NODE_TAG, childNode)
                    })
                }
            }
        }
    }
}

interface MidiViewProvider {
    fun viewGroup(node: PresetNodeGroup): INodeGroupView

    fun view(node: PresetNode): NodeView
}

class NodeGroupView(val layout: ViewGroup, val testMidiViewProvider: MidiViewProvider) : INodeGroupView {
    override val view: View = layout

    override fun addChild(node: PresetNode) {
        (layout.getTag(NODE_TAG) as PresetNodeGroup).childs.add(node)
        addChild(testMidiViewProvider.view(node))
    }


    override fun addChild(childNodeView: NodeView) {
        layout.addView(childNodeView.view.apply {
            setTag(GROUP_VIEW_TAG, childNodeView)
        })
    }

    override fun removeChild(node: PresetNode) {
        val viewWithTag = layout.views.first { it.getTag(NODE_TAG) == node }
        layout.removeView(viewWithTag)
        (layout.getTag(NODE_TAG) as PresetNodeGroup).childs.remove(node)
    }
}