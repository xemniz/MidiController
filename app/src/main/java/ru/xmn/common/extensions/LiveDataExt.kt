package ru.xmn.common.extensions

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.Observer
import android.support.annotation.NonNull

inline fun <T> LiveData<T>.observeNonNull(@NonNull owner: LifecycleOwner, crossinline onValue: (T) -> Unit) {
    this.observe(owner, Observer { onValue(it!!) })
}

fun <T> LiveData<T>.merge(another: LiveData<T>) = object: MediatorLiveData<T>(){
    init {
        this.addSource(this@merge) { value = it }
        this.addSource(another) { value = it }
    }

}