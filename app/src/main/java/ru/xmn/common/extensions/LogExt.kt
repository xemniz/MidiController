package ru.xmn.common.extensions

import android.util.Log

inline fun <reified T> T.log(logText: String) {
    Log.d(T::class.java.simpleName, logText)
}