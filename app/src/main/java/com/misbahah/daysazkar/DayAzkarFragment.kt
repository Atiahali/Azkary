package com.misbahah.daysazkar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.misbahah.databinding.FragmentDayAzkarBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DayAzkarFragment : Fragment() {

    private lateinit var binding: FragmentDayAzkarBinding

    private val viewModel by viewModels<DayAzkarViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDayAzkarBinding.inflate(inflater, container, false)

        val adapter = DayAzkarRecyclerAdapter()
        binding.recyclerView.adapter = adapter
        subscribeUi(adapter)


        viewModel.getAllCategories()

        return binding.root
    }

    private fun subscribeUi(adapter: DayAzkarRecyclerAdapter) {
        viewModel.categoryList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

}