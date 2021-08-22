package com.misbahah.azkarlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.misbahah.databinding.FragmentDayCategoryAzkarListBinding

class DayCategoryAzkarListFragment : Fragment() {

    private lateinit var binding: FragmentDayCategoryAzkarListBinding

    private val args by navArgs<DayCategoryAzkarListFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDayCategoryAzkarListBinding.inflate(inflater, container, false)

        initView()

        return binding.root
    }

    private fun initView() {
        binding.categoryName = args.categoryName
        binding.toolbar.setNavigationOnClickListener { view ->
            view.findNavController().navigateUp()
        }
        binding.executePendingBindings()
    }
}