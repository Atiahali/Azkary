package com.misbahah.azkarlist

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.misbahah.data.model.Zikr
import com.misbahah.databinding.AzkarListItemBinding

class DayCategoryAzkarRecyclerAdapter :
    ListAdapter<Zikr, DayCategoryAzkarRecyclerAdapter.DayCategoryAzkarViewHolder>(
        DayCategoryAzkarDiffCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayCategoryAzkarViewHolder {
        return DayCategoryAzkarViewHolder(
            AzkarListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DayCategoryAzkarViewHolder, position: Int) {
        val zikr = getItem(position)
        val isLastItem = currentList.lastOrNull() == zikr
        holder.bind(zikr, isLastItem)
    }

    class DayCategoryAzkarViewHolder(private val binding: AzkarListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.setClickListener {
                binding.zikr.let { zikr ->
//                    navigateToAzkarListFragment(category, it)
                }
            }
        }

        private fun navigateToAzkarListFragment(category: Zikr, view: View) {
//            val direction =
//                CategoriesFragmentDirections.actionCategoriesFragmentToAzkarListFragment(category.id, category.categoryName)
//            view.findNavController().navigate(direction)
        }

        fun bind(item: Zikr, isLastItem: Boolean) {
            binding.apply {
                zikr = item
                if (isLastItem)
                    divider.visibility = View.GONE
                else
                    divider.visibility = View.VISIBLE
                executePendingBindings()
            }
        }
    }
}

private class DayCategoryAzkarDiffCallback : DiffUtil.ItemCallback<Zikr>() {
    override fun areItemsTheSame(oldItem: Zikr, newItem: Zikr): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Zikr, newItem: Zikr): Boolean =
        oldItem == newItem
}