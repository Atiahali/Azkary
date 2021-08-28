package com.misbahah.variousazkarlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.misbahah.data.model.Zikr
import com.misbahah.databinding.AzkarListItemBinding

class VariousAzkarRecyclerAdapter(private val clickListener: OnVariousAzkarItemClickListener) :
    ListAdapter<Zikr, VariousAzkarRecyclerAdapter.VariousAzkarViewHolder>(
        DayCategoryAzkarDiffCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VariousAzkarViewHolder {
        return VariousAzkarViewHolder(
            AzkarListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            clickListener
        )
    }

    override fun onBindViewHolder(holder: VariousAzkarViewHolder, position: Int) {
        val zikr = getItem(position)
        val isLastItem = currentList.lastOrNull() == zikr
        holder.bind(zikr, isLastItem, position)
    }

    class VariousAzkarViewHolder(
        private val binding: AzkarListItemBinding,
        private val clickListener: OnVariousAzkarItemClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Zikr, isLastItem: Boolean, position: Int) {
            binding.apply {
                zikr = item
                if (isLastItem)
                    divider.visibility = View.GONE
                else
                    divider.visibility = View.VISIBLE
                executePendingBindings()
                setClickListener {
                    this@VariousAzkarViewHolder.clickListener.onItemClick(
                        binding.zikr!!,
                        position
                    )
                }
            }
        }
    }

}

interface OnVariousAzkarItemClickListener {
    fun onItemClick(item: Zikr, position: Int)
}

private class DayCategoryAzkarDiffCallback : DiffUtil.ItemCallback<Zikr>() {
    override fun areItemsTheSame(oldItem: Zikr, newItem: Zikr): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Zikr, newItem: Zikr): Boolean =
        oldItem == newItem
}