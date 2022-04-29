package com.colekainz.adaptiveadapter
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

fun <T: Any> unbindingAdaptiveAdapter() = AdaptiveUnbindingDelegate<T>()

class AdaptiveUnbindingDelegate<T: Any> : ReadOnlyProperty<Fragment, AdaptiveAdapter<T>> {
    private val registerList = mutableListOf<RegisterData<*, T, out T>>()
    private var adapter: AdaptiveAdapter<T>? = null
    private var isBound = false

    override fun getValue(thisRef: Fragment, property: KProperty<*>): AdaptiveAdapter<T> {
        checkDelegateInitialized(thisRef)
        return when (adapter) {
            null -> initializeAdapter()
            else -> adapter!!
        }
    }

    fun <V, D : T> register(
        viewClass: KClass<V>,
        dataClass: KClass<D>
    ): AdaptiveUnbindingDelegate<T> where V : RecyclerViewItem<D>, V : View {
        val data = RegisterData<V, T, D>(viewClass, dataClass)
        registerList.add(data)
        return this
    }

    inline fun <reified V, reified D : T> register() where V : RecyclerViewItem<D>, V : View =
        register(V::class, D::class)

    private fun checkDelegateInitialized(thisRef: Fragment) {
        if (!thisRef.isInitialized)
            throw IllegalStateException("Should not attempt to get adapter when Fragment view is destroyed.")
        if (!isBound) {
            thisRef.registerUnbinder { adapter = null }
            isBound = true
        }
    }

    private fun initializeAdapter(): AdaptiveAdapter<T> {
        adapter = AdaptiveAdapter()
        registerList.forEach { it.registerOn(adapter!!) }
        return adapter!!
    }
}

internal data class RegisterData<V, T: Any, D : T>(
    val viewClass: KClass<V>,
    val dataClass: KClass<D>
) where V : RecyclerViewItem<D>, V : View {
    fun registerOn(adapter: AdaptiveAdapter<T>) {
        adapter.register(viewClass, dataClass)
    }
}

private val Fragment.isInitialized: Boolean
    get() {
        val lifecycle = viewLifecycleOwner.lifecycle
        val currentState = lifecycle.currentState
        return currentState.isAtLeast(Lifecycle.State.INITIALIZED)
    }
