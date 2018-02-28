package com.xmn.midicontroller.domain.presets

import com.google.gson.Gson
import com.xmn.midicontroller.domain.model.Preset
import com.xmn.midicontroller.domain.nodes.ControlNode
import com.xmn.midicontroller.domain.nodes.GridGroupNode
import com.xmn.midicontroller.domain.nodes.GridItemNode
import com.xmn.midicontroller.domain.serialization.NodeSerializer
import com.xmn.midicontroller.domain.serialization.factory
import com.xmn.midicontroller.domain.serialization.gson
import dagger.Module
import dagger.Provides

@Module
class PresetServiceModule{
    @Provides
    fun provideNodeSerializer(): NodeSerializer{
        val factory = factory(mapOf(
                GridGroupNode::class.java to "grid group",
                GridItemNode::class.java to "grid item",
                ControlNode::class.java to "control"
        ))

        return NodeSerializer(gson = gson(factory))
    }
    @Provides
    fun providePresetService(nodeSerializer: NodeSerializer): PresetsDiskService {
        return PresetsDiskService(nodeSerializer)
    }

}