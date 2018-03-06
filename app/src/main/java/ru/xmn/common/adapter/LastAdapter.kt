package ru.xmn.common.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import ru.xmn.common.extensions.inflate
import kotlin.properties.Delegates


class LastAdapter : RecyclerView.Adapter<LastAdapter.ViewHolder>(), AutoUpdatableAdapter {

    var items: List<Item<*>> by Delegates.observable(emptyList()) { _, oldValue, newValue ->
        autoNotify(
                oldValue,
                newValue,
                sameItem = { item1, item2 ->
                    item1.sameItem(item2)
                },
                sameContent = { item1, item2 ->
                    item1.sameContent(item2)
                })
    }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(viewType))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getInstanceForPosition(position), position)
    }

    override fun getItemViewType(position: Int): Int {
        return getLayoutIdForPosition(position)
    }

    private fun getLayoutIdForPosition(position: Int) = getInstanceForPosition(position).layoutId()

    private fun getInstanceForPosition(position: Int): Item<*> = items[position]

    override fun onViewRecycled(holder: ViewHolder?) {
        super.onViewRecycled(holder)
        (holder as ViewHolder).recycle()
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view), ScopeOwner {
        private var scope: Scope? = null

        override fun setScope(scope: Scope) {
            this.scope = scope
        }

        fun bind(instanceForPosition: Item<*>, position: Int) {
            recycle()
            instanceForPosition.bindOn(view, this, position)
        }


        fun recycle() {
            scope?.let {
                it.onClear()
                scope = null
            }
        }

    }

    interface ScopeOwner {
        fun setScope(scope: Scope)
    }


    interface Scope {
        fun onClear()
    }

    interface Item<out T> {
        val value: T
        fun bindOn(view: View, scopeOwner: ScopeOwner, position: Int)
        fun layoutId(): Int
        fun sameItem(anotherItem: Item<*>): Boolean
        fun sameContent(anotherItem: Item<*>): Boolean = value == anotherItem.value
    }
}

inline fun <reified E, reified T : LastAdapter.Item<E>> T.sameItem(anotherItem: LastAdapter.Item<*>, identify: E.() -> Any): Boolean {
    return anotherItem is T && identify(value) == identify(anotherItem.value)
}

var RecyclerView.lastAdapterItems
    get() = when {
        this.adapter !is LastAdapter -> {
            this.adapter = LastAdapter()
            emptyList()
        }
        else -> (this.adapter as LastAdapter).items
    }
    set(items) = when {
        this.adapter !is LastAdapter -> {
            this.adapter = LastAdapter()
            (this.adapter as LastAdapter).items = items
        }
        else -> (this.adapter as LastAdapter).items = items
    }
