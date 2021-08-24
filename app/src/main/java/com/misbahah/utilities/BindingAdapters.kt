package com.misbahah.utilities

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.misbahah.R


@BindingAdapter("imageFromUrl")
fun bindImageFromUrl(view: ImageView, imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {
        view.visibility = View.VISIBLE
        Glide.with(view.context)
            .load(imageUrl)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(view)
    } else {
        view.setImageDrawable(null)
        view.visibility = View.GONE
    }
}

@BindingAdapter("repeatingNumber")
fun bindRepeatingNumber(view: TextView, repeatingNumber: Int) {
    if (repeatingNumber <= 0) {
        view.visibility = View.GONE
    } else {
        view.text = view.context.getString(R.string.repeating_times, repeatingNumber)
        view.visibility = View.VISIBLE
    }
}