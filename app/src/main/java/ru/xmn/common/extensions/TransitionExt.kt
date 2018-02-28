package ru.xmn.common.extensions

import android.transition.Transition

fun Transition.delay(i: Long): Transition {
    this.startDelay = i; return this
}

fun Transition.dur(i: Long): Transition {
    this.duration = i; return this
}