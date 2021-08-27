package com.misbahah.azkardetails.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.misbahah.data.db.CategoryDao
import com.misbahah.databinding.FragmentAzkarDetailsViewPagerBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import timber.log.Timber
import javax.inject.Inject

const val VARIOUS_AZKAR_CATEGORY = -1

@AndroidEntryPoint
class AzkarDetailsViewPagerFragment : Fragment() {

    private lateinit var binding: FragmentAzkarDetailsViewPagerBinding

    private val viewModel by viewModels<AzkarDetailsViewPagerViewModel>()

    private val args by navArgs<AzkarDetailsViewPagerFragmentArgs>()

    @Inject
    lateinit var dao: CategoryDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAzkarDetailsViewPagerBinding.inflate(inflater, container, false)

        initView()

        if (args.categoryId != VARIOUS_AZKAR_CATEGORY) {
            viewModel.getAzkarByCategoryId(args.categoryId)
        } else {
            viewModel.getVariousAzkar()
        }


        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.stateFlow.collect {
                if (it != null) {
                    binding.viewPager.adapter =
                        AzkarDetailsViewPagerAdapter(
                            this@AzkarDetailsViewPagerFragment,
                            it
                        )
                    if (binding.viewPager.adapter != null) {
                        if (args.zikrIndex >= 1)
                            binding.viewPager.currentItem = args.zikrIndex
                    } else {
                        Timber.e(
                            IllegalStateException("onViewCreated: view pager adapter is null"),
                        )
                    }
                }
            }
        }

        return binding.root
    }

    fun initView() {
        binding.toolbarText.text = args.categoryName
        binding.toolbar.setNavigationOnClickListener { view ->
            view.findNavController().navigateUp()
        }
    }
}