package com.colekainz.adaptiveadapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

fun Fragment.registerUnbinder(onViewDestroyed: () -> Unit) = parentFragmentManager
    .registerFragmentLifecycleCallbacks(UnbinderCallback(this, onViewDestroyed), false)

class UnbinderCallback(
    private val fragment: Fragment,
    private val onViewDestroyed: () -> Unit
) : FragmentManager.FragmentLifecycleCallbacks() {
    override fun onFragmentViewDestroyed(fm: FragmentManager, f: Fragment) {
        super.onFragmentViewDestroyed(fm, f)
        if (fragment !== f) return

        onViewDestroyed()
        fm.unregisterFragmentLifecycleCallbacks(this)
    }
}
