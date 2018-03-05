package com.xmn.midicontroller.domain.model

abstract class NodeGroup : Node {
    protected val childNodes: MutableList<Node> = ArrayList()
    val childes: List<Node>
        get() = childNodes

    fun add(node: Node) {
        childNodes.add(node)
    }

    fun remove(node: Node) {
        childNodes.removeAll { it.id == node.id }
    }
}