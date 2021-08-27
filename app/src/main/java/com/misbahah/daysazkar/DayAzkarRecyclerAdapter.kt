package com.misbahah.daysazkar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.misbahah.categories.ui.CategoriesFragmentDirections
import com.misbahah.data.model.Category
import com.misbahah.databinding.ItemCategoryBinding


class DayAzkarRecyclerAdapter :
    ListAdapter<Category, DayAzkarRecyclerAdapter.DayAzkarViewHolder>(CategoryDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayAzkarViewHolder {
        return DayAzkarViewHolder(
            ItemCategoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DayAzkarViewHolder, position: Int) {
        val category = getItem(position)
        holder.bind(category)
    }


    class DayAzkarViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.setClickListener {
                binding.category?.let { category ->
                    navigateToAzkarListFragment(category, it)
                }
            }
        }

        private fun navigateToAzkarListFragment(category: Category, view: View) {
            val direction =
                CategoriesFragmentDirections.actionCategoriesFragmentToAzkarDetailsViewPagerFragment(
                    category.id,
                    category.categoryName
                )
            view.findNavController().navigate(direction)
        }

        fun bind(item: Category) {
            binding.apply {
                category = item
                executePendingBindings()
            }
        }
    }
}

private class CategoryDiffCallback : DiffUtil.ItemCallback<Category>() {
    override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean =
        oldItem == newItem

}