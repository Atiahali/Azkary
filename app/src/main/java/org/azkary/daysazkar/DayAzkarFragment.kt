package org.azkary.daysazkar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.google.android.material.composethemeadapter.MdcTheme
import dagger.hilt.android.AndroidEntryPoint
import org.azkary.categories.ui.CategoriesFragmentDirections
import org.azkary.databinding.FragmentDayAzkarBinding

@AndroidEntryPoint
class DayAzkarFragment : Fragment() {

    private lateinit var binding: FragmentDayAzkarBinding

    private val viewModel by viewModels<DayAzkarViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDayAzkarBinding.inflate(inflater, container, false)

        binding.composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MdcTheme {
//                    Todo
//                    val viewModel: VariousAzkarViewModel = hiltViewModel()
                    LaunchedEffect(viewModel.categoryList.value) {
                        viewModel.getAllCategories()
                    }
                    DayAzkarList(viewModel.categoryList.value) { category ->
                        val direction =
                            CategoriesFragmentDirections.actionCategoriesFragmentToAzkarDetailsViewPagerFragment(
                                category.id,
                                category.categoryName
                            )
                        findNavController().navigate(direction)
                    }

                }
            }
        }

        return binding.root
    }

}