package com.xmn.midicontroller.domain.presentation

import android.view.View
import android.view.ViewGroup
import com.xmn.midicontroller.R
import com.xmn.midicontroller.domain.model.Node
import kotlinx.android.synthetic.main.root_group.view.*
import ru.xmn.common.extensions.inflateCast

class EmptyNodeView(val parent: ViewGroup, override val node: Node, override val requestAdd: (String) -> Unit) : NodeGroupView {
    override val view: View
        get() = viewGroup

    override fun add(node: NodeView) {
        viewGroup.removeAllViews()
        viewGroup.addView(node.view)
    }

    override val viewGroup: ViewGroup = parent.inflateCast(R.layout.root_group)

    init {
        viewGroup.apply {
            add_button.setOnClickListener {

            }
        }
    }
}