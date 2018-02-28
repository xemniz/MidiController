package com.xmn.midicontroller.domain.presentation

import android.view.View
import android.view.ViewGroup
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever

class TestIMidiViewProvider : MidiViewProvider {
    override fun view(node: PresetNode): NodeView {
        return TestView(ViewMock().view())
    }

    override fun viewGroup(node: PresetNodeGroup): INodeGroupView {
        return NodeGroupView(ViewMock().viewGroup(), this)
    }
}

class ViewMock{
    var nodeTag: Any? = null
    var viewGroupTag: Any? = null
    val views: ArrayList<View> = ArrayList()

    fun view(): View {
        val mock = mock<View>()
        mockTag(mock)
        return mock
    }

    fun viewGroup(): ViewGroup {
        val layout: ViewGroup = mock()
        whenever(layout.addView(any())).thenAnswer { views += (it.getArgument(0) as View); Unit }
        whenever(layout.removeView(any())).thenAnswer { views -= it.getArgument<View>(0); Unit }
        whenever(layout.childCount).then { views.count() }
        whenever(layout.getChildAt(any())).thenAnswer { views[it.getArgument<Int>(0)] }
        mockTag(layout)
        return layout
    }

    private fun mockTag(layout: View) {
        whenever(layout.getTag(any())).then {
            when {
                it.getArgument<Int>(0) == GROUP_VIEW_TAG -> viewGroupTag
                it.getArgument<Int>(0) == NODE_TAG -> nodeTag
                else -> null
            } }
        whenever(layout.setTag(any(), any())).then {
            when {
                it.getArgument<Int>(0) == GROUP_VIEW_TAG -> viewGroupTag = it.getArgument(1)
                it.getArgument<Int>(0) == NODE_TAG -> nodeTag = it.getArgument(1)
            }
            Unit
        }
    }
}