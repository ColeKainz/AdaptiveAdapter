package com.colekainz.adaptiveadapter.ui.main.item

import android.content.Context
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.colekainz.adaptiveadapter.RecyclerViewItem
import com.colekainz.adaptiveadapter.databinding.MainColorListItemViewBinding

class MainColorListItemView(context: Context) : FrameLayout(context), RecyclerViewItem<MainColorListItemDisplayModel> {

    private val binding = MainColorListItemViewBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
    }

    override fun bind(data: MainColorListItemDisplayModel) {
        binding.clColor.setBackgroundColor(data.color)
    }
}