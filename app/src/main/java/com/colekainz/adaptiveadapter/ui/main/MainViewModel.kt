package com.colekainz.adaptiveadapter.ui.main

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class MainViewModel : ViewModel() {
    private val colorListFactory = MainColorListFactory()
    val colorListState = MutableStateFlow(colorListFactory.generateColorList())
}