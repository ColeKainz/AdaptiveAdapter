package com.colekainz.adaptiveadapter.ui.main

import android.graphics.Color
import com.colekainz.adaptiveadapter.ui.main.header.MainColorListHeaderDisplayModel
import com.colekainz.adaptiveadapter.ui.main.item.MainColorListItemDisplayModel

class MainColorListFactory {
    fun generateColorList() = listOf(
        MainColorListHeaderDisplayModel("Red"),
        MainColorListItemDisplayModel(Color.RED),
        MainColorListHeaderDisplayModel("Blue"),
        MainColorListItemDisplayModel(Color.BLUE),
        MainColorListHeaderDisplayModel("Green"),
        MainColorListItemDisplayModel(Color.GREEN)
    )
}