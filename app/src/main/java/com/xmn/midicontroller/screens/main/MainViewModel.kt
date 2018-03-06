package com.xmn.midicontroller.screens.main

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.view.ViewGroup
import com.xmn.midicontroller.R
import com.xmn.midicontroller.domain.model.Node
import com.xmn.midicontroller.domain.nodes.GridGroupNode
import com.xmn.midicontroller.domain.presentation.GridNode
import com.xmn.midicontroller.domain.presentation.MidiViewProviderGateway
import kotlinx.android.synthetic.main.grid_params.view.*
import ru.xmn.common.extensions.inflate
import javax.inject.Inject

class MainViewModel @Inject constructor(val openPresetUseCase: OpenPresetUseCase, val midiViewProviderGateway: MidiViewProviderGateway) : ViewModel() {
    val addNodeUseCase: AddNodeUseCase = AddNodeUseCase()

}

class AddNodeUseCase {
    val addNodeDialogState: MutableLiveData<AddNodeDialogState> = MutableLiveData()

    init {
        addNodeDialogState.value = AddNodeDialogState.Idle
    }

    private val nodeBuilders = listOf(GridNodeBuilder())

    fun requestAddNode(nodeId: String) {
        addNodeDialogState.value = AddNodeDialogState.ChooseNodeType(nodeId, nodeBuilders)
    }
}

sealed class AddNodeDialogState {
    object Idle : AddNodeDialogState()
    class ChooseNodeType(val parentNodeId: String, val nodeTypeNames: List<NodeBuilder>) : AddNodeDialogState()
}

interface NodeBuilder {
    val name: String
    fun drawCreateParams(parent: ViewGroup, onNodeCreated: (Node) -> Unit)
}

class GridNodeBuilder : NodeBuilder {
    override val name: String = "Grid"

    override fun drawCreateParams(parent: ViewGroup, onNodeCreated: (Node) -> Unit) {
        parent.apply {
            removeAllViews()
            inflate(R.layout.grid_params).apply {
                ok.setOnClickListener {
                    val gridNode = GridNode(GridGroupNode(height = grid_height.text.toString().toInt(), width = grid_width.text.toString().toInt()))
                    onNodeCreated(gridNode)
                }
            }
        }
    }
}
