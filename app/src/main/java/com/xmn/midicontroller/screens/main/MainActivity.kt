package com.xmn.midicontroller.screens.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.ViewGroup
import com.xmn.midicontroller.R
import com.xmn.midicontroller.app.App
import com.xmn.midicontroller.domain.model.Node
import com.xmn.midicontroller.domain.model.NodeGroup
import com.xmn.midicontroller.domain.model.PresetData
import com.xmn.midicontroller.domain.presentation.NodeGroupView
import com.xmn.midicontroller.domain.presentation.NodeView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.choose_node_type.view.*
import kotlinx.android.synthetic.main.node_type_item.view.*
import ru.xmn.common.adapter.LastAdapter
import ru.xmn.common.adapter.lastAdapterItems
import ru.xmn.common.adapter.sameItem
import ru.xmn.common.extensions.*

class MainActivity : AppCompatActivity() {
    private val mainViewModel by viewModelProvider { App.component.mainViewModel() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViewModel()
    }

    private fun setupViewModel() {
        val activity = this

        mainViewModel.apply {
            openPresetUseCase.apply {
                presetLiveData.observeNonNull(activity, activity::bindPreset)
                editStateLiveData.observeNonNull(activity) {
                    when (it) {
                        true -> {
                        }
                        false -> {
                        }
                    }
                }
            }
            addNodeUseCase.apply {
                addNodeDialogState.observeNonNull(activity) { nodeDialogState ->
                    when (nodeDialogState) {
                        AddNodeDialogState.Idle -> {
                            add_node_dialog.gone()
                        }
                        is AddNodeDialogState.ChooseNodeType -> {
                            add_node_dialog.apply {
                                visible()
                                inflate(R.layout.choose_node_type).apply {
                                    node_types.apply {
                                        layoutManager = LinearLayoutManager(activity)
                                        lastAdapterItems = nodeDialogState.nodeTypeNames.map {
                                            NodeTypeItem(it, onclick = {
                                                it.drawCreateParams(add_node_dialog, onNodeCreated = {node ->
                                                    mainViewModel.openPresetUseCase.addNode(nodeDialogState.parentNodeId, node)
                                                })
                                            })
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun bindPreset(preset: PresetData) {
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
                val nodeView = mainViewModel.midiViewProviderGateway.viewGroup(root.viewGroup, node, { requestAdd(it) })
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

class NodeTypeItem(override val value: NodeBuilder, onclick: () -> Unit) : LastAdapter.Item<NodeBuilder> {
    override fun bindOn(view: View, scopeOwner: LastAdapter.ScopeOwner, position: Int) {
        view.apply {
            node_type_name.text = value.name
        }
    }

    override fun layoutId() = R.layout.node_type_item

    override fun sameItem(anotherItem: LastAdapter.Item<*>) = sameItem(anotherItem) { value.name }
    override fun sameContent(anotherItem: LastAdapter.Item<*>) = sameItem(anotherItem) { value.name }
}
