package com.misbahah.variousazkarlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.misbahah.R
import com.misbahah.categories.ui.CategoriesFragmentDirections
import com.misbahah.data.model.Zikr
import com.misbahah.databinding.FragmentVariousAzkarListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VariousAzkarListFragment : Fragment(), OnVariousAzkarItemClickListener {

    private lateinit var binding: FragmentVariousAzkarListBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVariousAzkarListBinding.inflate(inflater, container, false)

        initView()

        val adapter = DayCategoryAzkarRecyclerAdapter(this)
        binding.recyclerView.adapter = adapter
        subscribeUi(adapter)

        return binding.root
    }

    private fun initView() {
//        binding.categoryName = getString(R.string.various_azkar_title)
    }

    private fun subscribeUi(adapter: DayCategoryAzkarRecyclerAdapter) {
//        viewModel.categoryList.observe(viewLifecycleOwner) {
//            adapter.submitList(it)
//        }
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