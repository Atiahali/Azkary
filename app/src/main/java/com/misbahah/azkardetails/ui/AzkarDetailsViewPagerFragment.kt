package com.misbahah.azkardetails.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.misbahah.databinding.FragmentAzkarDetailsViewPagerBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

const val VARIOUS_AZKAR_CATEGORY = -1

@AndroidEntryPoint
class AzkarDetailsViewPagerFragment : Fragment() {

    private lateinit var binding: FragmentAzkarDetailsViewPagerBinding

    private val viewModel by viewModels<AzkarDetailsViewPagerViewModel>()

    private val args by navArgs<AzkarDetailsViewPagerFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (args.categoryId != VARIOUS_AZKAR_CATEGORY) {
            viewModel.getAzkarByCategoryId(args.categoryId)
        } else {
            viewModel.getVariousAzkar()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAzkarDetailsViewPagerBinding.inflate(inflater, container, false)

        initView()

        viewModel.currentItemIndex.observe(viewLifecycleOwner) {
            if (binding.viewPager.adapter != null) {
                binding.viewPager.setCurrentItem(it, false)
            } else {
                Timber.e(
                    IllegalStateException("onViewCreated: view pager adapter is null"),
                )
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.stateFlow.collect {
                    val adapter = AzkarDetailsViewPagerAdapter(
                        this@AzkarDetailsViewPagerFragment,
                        it
                    )

                    binding.viewPager.adapter = adapter
                    if (args.zikrIndex >= 1
                        && (viewModel.currentItemIndex.value!! == args.zikrIndex
                                || viewModel.currentItemIndex.value!! == 0)
                    ) {
                        viewModel.setCurrentItem(args.zikrIndex)
                    }
                }
            }
        }

        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        viewModel.setCurrentItem(binding.viewPager.currentItem)
        super.onSaveInstanceState(outState)
    }

    private fun initView() {
        binding.toolbarText.text = args.categoryName
        binding.toolbar.setNavigationOnClickListener { view ->
            view.findNavController().navigateUp()
        }
    }
}