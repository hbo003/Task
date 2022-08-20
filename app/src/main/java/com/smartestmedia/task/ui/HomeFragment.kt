package com.smartestmedia.task.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.smartestmedia.task.R
import com.smartestmedia.task.adapter.RepoAdapter
import com.smartestmedia.task.databinding.FragmentHomeBinding
import com.smartestmedia.task.viewmodel.CharacterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var characterAdapter: RepoAdapter
    private val viewModel: CharacterViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        setupRecyclerView()
        loadData()

    }


    private fun setupRecyclerView() {

        characterAdapter = RepoAdapter()

        binding.recyclerView.apply {
            adapter = characterAdapter
            layoutManager = StaggeredGridLayoutManager(
                2, StaggeredGridLayoutManager.VERTICAL
            )
            setHasFixedSize(false)
        }

        binding.filterTv.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                characterAdapter.filter.filter(p0.toString())
            }
            override fun afterTextChanged(p0: Editable?) {
                if (p0.toString().trim().isEmpty()) {
                    binding.recyclerView.adapter?.notifyDataSetChanged()
                }
            }
        })
    }

    private fun loadData() {
        characterAdapter.addLoadStateListener { loadState ->

            if (loadState.refresh is LoadState.Loading) {
                binding.homeProgress.visibility = View.VISIBLE
                binding.filterTv.isEnabled = false
            } else {
                binding.homeProgress.visibility = View.GONE
                binding.filterTv.isEnabled = true
            }
        }
        loadDataToPagingAdapter()

    }

    private fun loadDataToPagingAdapter() {
        lifecycleScope.launch {
            viewModel.listData.collectLatest {
                characterAdapter.submitData(it)
            }
        }
    }

}