package org.azkary.variousazkarlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.composethemeadapter.MdcTheme
import dagger.hilt.android.AndroidEntryPoint
import org.azkary.R
import org.azkary.categories.ui.CategoriesFragmentDirections
import org.azkary.databinding.FragmentVariousAzkarListBinding

@AndroidEntryPoint
class VariousAzkarListFragment : Fragment() {

    private lateinit var binding: FragmentVariousAzkarListBinding


    private val viewModel by viewModels<VariousAzkarViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVariousAzkarListBinding.inflate(inflater, container, false)

        binding.composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MdcTheme {
//                    Todo
//                    val viewModel: VariousAzkarViewModel = hiltViewModel()
                    LaunchedEffect(viewModel.variousAzkarStateFlow.value) {
                        viewModel.getVariousAzkar()
                    }
                    VariousAzkarList(variousAzkar = viewModel.variousAzkarStateFlow.value) { position ->
                        val direction =
                            CategoriesFragmentDirections.actionCategoriesFragmentToAzkarDetailsViewPagerFragment(
                                categoryName = getString(R.string.various_azkar_title),
                                zikrIndex = position
                            )
                        findNavController().navigate(direction)
                    }
                }
            }
        }



        return binding.root
    }

}