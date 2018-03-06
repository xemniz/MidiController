package com.xmn.midicontroller.domain.serialization

import com.xmn.midicontroller.domain.model.PresetData
import com.xmn.midicontroller.domain.nodes.ControlNode
import com.xmn.midicontroller.domain.nodes.GridGroupNode
import com.xmn.midicontroller.domain.nodes.GridItemNode
import org.junit.Assert.assertEquals
import org.junit.Test

class NodeSerializerTest {

    @Test
    fun serialize() {
        val preset = PresetData("name", GridGroupNode(mutableListOf(
                GridItemNode(mutableListOf(ControlNode("", "")), 1, 1, 1, 1),
                GridItemNode(mutableListOf(ControlNode("", "")), 1, 1, 1, 1),
                GridItemNode(mutableListOf(ControlNode("", "")), 1, 1, 1, 1)
        )))
        val factory = factory(mapOf(
                GridGroupNode::class.java to "grid group",
                GridItemNode::class.java to "grid item",
                ControlNode::class.java to "control"
        ))
        val gson = gson(factory)
        val nodeSerializer = NodeSerializer(gson)
        val serialize = nodeSerializer.serialize(preset)
        val deSerialize = nodeSerializer.deSerialize(serialize)
        assertEquals(preset, deSerialize)
    }

}
