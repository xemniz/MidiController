package ru.xmn.common.extensions

import java.lang.ref.WeakReference
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class WeakReferenceDecorator<T>(obj: T? = null) : ReadWriteProperty<Any?, T?> {

    private var wref: WeakReference<T>?

    init {
        this.wref = obj?.let { WeakReference(it) }
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T? {
        return wref?.get()
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) {
        wref = value?.let { WeakReference(it) }
    }
}

fun <T>T.weak() = WeakReference(this)