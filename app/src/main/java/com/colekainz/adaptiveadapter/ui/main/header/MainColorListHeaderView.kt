package com.colekainz.adaptiveadapter.ui.main.header

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.colekainz.adaptiveadapter.RecyclerViewItem
import com.colekainz.adaptiveadapter.databinding.MainColorListHeaderViewBinding

class MainColorListHeaderView(context: Context) : FrameLayout(context), RecyclerViewItem<MainColorListHeaderDisplayModel> {

    private val binding = MainColorListHeaderViewBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
    }

    override fun bind(data: MainColorListHeaderDisplayModel) {
        binding.tvTitle.text = data.title
    }
}