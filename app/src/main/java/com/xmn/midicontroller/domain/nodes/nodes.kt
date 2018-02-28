package com.xmn.midicontroller.domain.nodes

import com.xmn.midicontroller.domain.presentation.PresetNode
import com.xmn.midicontroller.domain.presentation.PresetNodeGroup


data class PagerNode(override val childs: MutableList<PresetNode>) : PresetNodeGroup
data class GridGroupNode(override val childs: MutableList<PresetNode>) : PresetNodeGroup
data class GridItemNode(
        override val childs: MutableList<PresetNode>,
        val row: Int,
        val column: Int,
        val height: Int,
        val width: Int
) : PresetNodeGroup
data class ControlNode(val name: String, val widget: String) : PresetNode