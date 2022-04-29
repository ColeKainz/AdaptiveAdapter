package com.colekainz.adaptiveadapter
import android.content.Context
import android.view.View
import kotlin.reflect.KClass
import kotlin.reflect.full.createType
import kotlin.reflect.full.primaryConstructor

abstract class AdaptiveViewHolderFactory<V, T: Any, D: T> where V : View, V : RecyclerViewItem<D> {
    abstract fun createViewHolder(context: Context): AdaptiveViewHolder<V, T, D>
    abstract fun isFactoryFor(item: T): Boolean

    companion object {
        fun <V, T: Any, D : T> create(viewClass: KClass<V>, dataClass: KClass<D>) where V : RecyclerViewItem<D>, V : View =
            object : AdaptiveViewHolderFactory<V, T, D>() {
                override fun isFactoryFor(item: T) = dataClass.isInstance(item)
                override fun createViewHolder(context: Context): AdaptiveViewHolder<V, T, D> {
                    val view = viewClass.getInstance(context)
                    return AdaptiveViewHolder(view)
                }
            }

        private fun <V : View> KClass<V>.getInstance(context: Context): V {
            val constructor = primaryConstructor!!
            val params = constructor.parameters
            val contextParam = params
                .firstOrNull { it.type == Context::class.createType() }
                ?: throw IllegalStateException("View needs context parameter in default constructor.")

            return constructor.callBy(mapOf(contextParam to context))
        }
    }
}
