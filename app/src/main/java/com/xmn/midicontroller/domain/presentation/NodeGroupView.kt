package com.xmn.midicontroller.domain.presentation

import android.view.ViewGroup

interface NodeGroupView: NodeView {
    val viewGroup: ViewGroup
    val requestAdd: (String) -> Unit
    fun add(node: NodeView)
}

