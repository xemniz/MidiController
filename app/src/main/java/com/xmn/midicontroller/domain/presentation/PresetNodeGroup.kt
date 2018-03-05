package com.xmn.midicontroller.domain.presentation

interface PresetNodeGroup : PresetNode {
    val childs: MutableList<PresetNode>

}