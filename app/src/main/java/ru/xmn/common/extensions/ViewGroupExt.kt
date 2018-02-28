package ru.xmn.common.extensions

import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

val ViewGroup.views: List<View>
    get() = (0..childCount - 1).map { getChildAt(it) }

fun ViewGroup.inflate(@LayoutRes layoutRes: Int): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, false)
}