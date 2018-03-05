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

    fun requestAddNode(nodeId: String) {
        addNodeDialogState.value = AddNodeDialogState.ChooseNodeType(nodeId, listOf("Grid"))
    }

    fun nodeChoosen(nodeId: String, nodeType: String) {
        addNodeDialogState.value = AddNodeDialogState.ChooseNodeType(nodeId, listOf("Grid"))
    }
}

sealed class AddNodeDialogState {
    object Idle : AddNodeDialogState()
    class ChooseNodeType(val parentNodeId: String, val nodeTypeNames: List<String>) : AddNodeDialogState()
}

interface NodeBuilder {
    val name: String
    val needParams: Boolean
    val onNodeCreated: (Node) -> Unit
}

class GridNodeBuilder(override val onNodeCreated: (Node) -> Unit) : NodeBuilder {

    override val name: String = "Grid"
    override val needParams: Boolean = true

    fun drawCreateParams(parent: ViewGroup) {
        parent.inflate(R.layout.grid_params).apply {
            ok.setOnClickListener {
                val gridNode = GridNode(GridGroupNode(height = grid_height.text.toString().toInt(), width = grid_width.text.toString().toInt()))
                onNodeCreated(gridNode)
            }
        }
    }
}


class GridNodeParams(val height: Int, val width: Int)