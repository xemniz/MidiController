package ru.xmn.common.extensions

import android.app.Activity
import android.graphics.Point

fun Activity.windowSize(): Point {
    val display = this.windowManager.defaultDisplay
    val size = Point()
    display.getSize(size)
    return size
}