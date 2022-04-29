package com.colekainz.adaptiveadapter.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.colekainz.adaptiveadapter.databinding.MainFragmentBinding
import com.colekainz.adaptiveadapter.ui.main.header.MainColorListHeaderDisplayModel
import com.colekainz.adaptiveadapter.ui.main.header.MainColorListHeaderView
import com.colekainz.adaptiveadapter.ui.main.item.MainColorListItemDisplayModel
import com.colekainz.adaptiveadapter.ui.main.item.MainColorListItemView
import com.colekainz.adaptiveadapter.unbindingAdaptiveAdapter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainFragment : Fragment() {

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<MainViewModel>()

    private val adapter by unbindingAdaptiveAdapter<MainColorListDisplayModel>()
        .register<MainColorListHeaderView, MainColorListHeaderDisplayModel>()
        .register<MainColorListItemView, MainColorListItemDisplayModel>()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvColors.layoutManager = LinearLayoutManager(requireContext())
        binding.rvColors.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.colorListState.collect { adapter.items = it }
            }
        }
    }
}