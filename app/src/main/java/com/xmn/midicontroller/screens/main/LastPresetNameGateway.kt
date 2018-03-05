package com.xmn.midicontroller.screens.main

interface LastPresetNameGateway {
    fun put(name: String)
    fun retreive(): String?
}