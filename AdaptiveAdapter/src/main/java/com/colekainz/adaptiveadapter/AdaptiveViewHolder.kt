package com.colekainz.adaptiveadapter
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class AdaptiveViewHolder<V, T: Any, D>(val view: V) : RecyclerView.ViewHolder(view) where V : View, V : RecyclerViewItem<D>, D: T {
    fun bindView(data: T) {
        val convertedData = data as D
        view.bind(convertedData)
    }
}
