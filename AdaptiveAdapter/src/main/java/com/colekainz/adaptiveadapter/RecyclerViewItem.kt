package com.colekainz.adaptiveadapter

interface RecyclerViewItem<D> {
    fun bind(data: D)
}
