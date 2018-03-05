package com.xmn.midicontroller.screens.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import com.xmn.midicontroller.R
import com.xmn.midicontroller.app.App
import com.xmn.midicontroller.domain.model.Node
import com.xmn.midicontroller.domain.model.NodeGroup
import com.xmn.midicontroller.domain.model.Preset
import com.xmn.midicontroller.domain.presentation.NodeGroupView
import com.xmn.midicontroller.domain.presentation.NodeView
import kotlinx.android.synthetic.main.activity_main.*
import ru.xmn.common.extensions.observeNonNull
import ru.xmn.common.extensions.viewModelProvider

class MainActivity : AppCompatActivity() {
    private val mainViewModel by viewModelProvider { App.component.mainViewModel() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViewModel()
    }

    private fun setupViewModel() {
        mainViewModel.openPresetUseCase.presetLiveData.observeNonNull(this) {
            bindPreset(it)
        }

        mainViewModel.openPresetUseCase.editStateLiveData.observeNonNull(this) {

        }
    }

    private fun bindPreset(preset: Preset) {
        val root: NodeGroupView = object : NodeGroupView {
            override val node: Node = Node.empty()
            override val requestAdd: (String) -> Unit = {}
            override val view: View = midi_preset_container
            override val viewGroup: ViewGroup = midi_preset_container
            override fun add(node: NodeView) {
                viewGroup.addView(node.view)
            }
        }

        bindView(root, preset.node)
    }

    private fun bindView(root: NodeGroupView, node: Node) {

        when (node) {
            is NodeGroup -> {
                val nodeView = mainViewModel.midiViewProviderGateway.viewGroup(root.viewGroup, node, {requestAdd(it)})
                root.add(nodeView)
                node.childes.forEach {
                    bindView(nodeView, node)
                }
            }
            else -> {
                val nodeView = mainViewModel.midiViewProviderGateway.view(root.viewGroup, node)
                root.add(nodeView)
            }
        }
    }

    private fun requestAdd(nodeId: String) {
        mainViewModel.addNodeUseCase.requestAddNode(nodeId)
    }
}
