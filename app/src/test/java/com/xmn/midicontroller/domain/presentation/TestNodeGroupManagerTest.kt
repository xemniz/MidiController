package com.xmn.midicontroller.domain.presentation

import android.view.ViewGroup
import androidx.view.get
import junit.framework.Assert.*
import org.junit.Before
import org.junit.Test

class TestNodeGroupManagerTest {
    lateinit var node: TestNodeGroup
    lateinit var root: ViewGroup

    @Before
    fun setUp() {
        val testMidiViewProvider = TestIMidiViewProvider()
        node = testNode()
        root = testMidiViewProvider.viewGroup(node).view as ViewGroup
        val viewGroup = testMidiViewProvider.viewGroup(node)
        val view = viewGroup.view
        root.addView(view)
        viewGroup.drawChildes(node, testMidiViewProvider)
    }

    @Test
    fun checkHierarchy() {
        val firstChild = root[0]
        assertTrue(firstChild is ViewGroup)

        firstChild as ViewGroup
        assertEquals(3, firstChild.childCount)

        val deeperChild = firstChild[2]
        assertTrue(deeperChild is ViewGroup)

        deeperChild as ViewGroup
        assertEquals(2, deeperChild.childCount)

        //removal test
        val deeperNode = deeperChild[0].getTag(NODE_TAG) as PresetNode
        (deeperChild.getTag(GROUP_VIEW_TAG) as NodeGroupView).removeChild(deeperNode)
        assertEquals(1, deeperChild.childCount)
        assertEquals(1, ((node as PresetNodeGroup).childs[2] as PresetNodeGroup).childs.count())
    }

    private fun testNode(): TestNodeGroup {
        return TestNodeGroup(mutableListOf(
                TestNode(),
                TestNode(),
                TestNodeGroup(mutableListOf(
                        TestNode(),
                        TestNode()
                ))
        ))
    }
}

