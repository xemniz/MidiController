package com.xmn.midicontroller.domain.serialization

import com.google.gson.GsonBuilder
import com.google.gson.TypeAdapterFactory
import com.xmn.midicontroller.domain.presentation.PresetNode

fun factory(nodeSubtypes: Map<Class<out PresetNode>, String>): RuntimeTypeAdapterFactory<PresetNode> =
        RuntimeTypeAdapterFactory.of(PresetNode::class.java)
        .apply { nodeSubtypes.forEach { clazz, label -> registerSubtype(clazz, label) } }

fun gson(factory: TypeAdapterFactory) = GsonBuilder()
        .registerTypeAdapterFactory(factory)
        .setPrettyPrinting()
        .create()

