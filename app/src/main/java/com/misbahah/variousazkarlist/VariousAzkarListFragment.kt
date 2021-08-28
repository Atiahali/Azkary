package com.misbahah.variousazkarlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.misbahah.R
import com.misbahah.categories.ui.CategoriesFragmentDirections
import com.misbahah.data.model.Zikr
import com.misbahah.databinding.FragmentVariousAzkarListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class VariousAzkarListFragment : Fragment(), OnVariousAzkarItemClickListener {

    private lateinit var binding: FragmentVariousAzkarListBinding

    private val viewModel by viewModels<VariousAzkarViewModel>()
    private lateinit var adapter: VariousAzkarRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getVariousAzkar()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVariousAzkarListBinding.inflate(inflater, container, false)


        adapter = VariousAzkarRecyclerAdapter(this)
        binding.recyclerView.adapter = adapter
        subscribeUi(adapter)

        return binding.root
    }

    private fun subscribeUi(adapter: VariousAzkarRecyclerAdapter) {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.variousAzkarStateFlow.collect { azkar ->
                    if (azkar.isNotEmpty()) {
                        adapter.submitList(azkar)
                    } else {
                        Timber.e("Azkar list is empty>")
                    }
                }
            }
        }
    }

    override fun onItemClick(item: Zikr, position: Int) {
        val direction =
            CategoriesFragmentDirections.actionCategoriesFragmentToAzkarDetailsViewPagerFragment(
                categoryName = getString(R.string.various_azkar_title),
                zikrIndex = position
            )
        findNavController().navigate(direction)
    }


}