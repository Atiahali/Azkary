package com.misbahah.categories.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.misbahah.databinding.FragmentCategoriesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoriesFragment : Fragment() {

    private lateinit var binding: FragmentCategoriesBinding

    private val viewModel by viewModels<CategoriesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoriesBinding.inflate(inflater, container, false)

        val adapter = CategoryRecyclerAdapter()
        binding.recyclerView.adapter = adapter
        subscribeUi(adapter)

        viewModel.getAllCategories()

        return binding.root
    }

    private fun subscribeUi(adapter: CategoryRecyclerAdapter) {
        viewModel.categoryList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

}