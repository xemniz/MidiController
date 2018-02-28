package ru.xmn.common.extensions

fun Float.abs(): Float = Math.abs(this)
fun Int.abs(): Int = Math.abs(this)
fun Double.abs(): Double = Math.abs(this)

fun offsetedValue(slideOffset: Float, fromSize: Float, toSize: Float) =
        fromSize + (toSize - fromSize) * slideOffset