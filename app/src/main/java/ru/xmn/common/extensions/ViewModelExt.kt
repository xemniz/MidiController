package ru.xmn.common.extensions

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.FragmentActivity

inline fun <reified VM : ViewModel> FragmentActivity.viewModelProvider(crossinline provider: () -> VM) = lazyFast {
    object : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>) =
                provider() as T
    }.let {
        ViewModelProviders.of(this, it).get(VM::class.java)
    }
}

inline fun <reified VM : ViewModel> FragmentActivity.viewModelProvider() = lazyFast {
        ViewModelProviders.of(this).get(VM::class.java)
}

fun <T> lazyFast(operation: () -> T): Lazy<T> = lazy(LazyThreadSafetyMode.NONE) {
    operation()
}