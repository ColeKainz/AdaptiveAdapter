package com.colekainz.adaptiveadapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlin.reflect.KClass

class AdaptiveAdapter<T: Any> : RecyclerView.Adapter<AdaptiveViewHolder<*, T, out T>>() {

    private val adaptiveViewHolderFactories = mutableListOf<AdaptiveViewHolderFactory<*, T, out T>>()

    var items: List<T> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inline fun <reified V, reified D: T> register() where V : RecyclerViewItem<D>, V : View =
        register(V::class, D::class)

    fun <V, D: T> register(
        viewClass: KClass<V>,
        dataClass: KClass<D>
    ): AdaptiveAdapter<T> where V : RecyclerViewItem<D>, V : View {
        val factory = AdaptiveViewHolderFactory.create<V, T, D>(viewClass, dataClass)
        adaptiveViewHolderFactories.add(factory)
        return this
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: AdaptiveViewHolder<*, T, out T>, position: Int) =
        holder.bindView(items[position])

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdaptiveViewHolder<*, T, out T> {
        val factory = adaptiveViewHolderFactories
            .getOrNull(viewType)
            ?: throw IllegalStateException("Data not registered to view.")
        return factory.createViewHolder(parent.context)
    }

    override fun getItemViewType(position: Int) = adaptiveViewHolderFactories.indexOfFirst {
        val item = items[position]
        it.isFactoryFor(item)
    }
}
