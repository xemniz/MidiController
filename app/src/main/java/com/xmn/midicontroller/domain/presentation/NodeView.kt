package com.xmn.midicontroller.domain.presentation

import android.view.View
import com.xmn.midicontroller.domain.model.Node

interface NodeView {
    val view: View
    val node: Node
}