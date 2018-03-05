package com.xmn.midicontroller.domain.presentation

import android.view.View
import android.view.ViewGroup
import com.xmn.midicontroller.R
import com.xmn.midicontroller.domain.model.Node
import ru.xmn.common.extensions.inflateCast

class GridNodeView(val parent: ViewGroup, override val node: Node, override val requestAdd: (String) -> Unit) : NodeGroupView {
    override val view: View
        get() = viewGroup

    override fun add(node: NodeView) {

    }

    override val viewGroup: ViewGroup = parent.inflateCast(R.layout.grid_group)
}