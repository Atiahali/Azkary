package org.azkary.utilities

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.mikhaellopez.circularprogressbar.CircularProgressBar
import org.azkary.R


@BindingAdapter("repeatingNumber")
fun bindRepeatingNumber(view: TextView, repeatingNumber: Int) {
    if (repeatingNumber <= 0) {
        view.visibility = View.GONE
    } else {
        view.text = view.context.getString(R.string.repeating_times, repeatingNumber)
        view.visibility = View.VISIBLE
    }
}

@BindingAdapter("repeatingNumber")
fun bindRepeatingNumber(view: CircularProgressBar, repeatingNumber: Int) {
    if (repeatingNumber <= 0) {
        view.visibility = View.GONE
    } else {
        view.progressMax = repeatingNumber.toFloat()
        view.visibility = View.VISIBLE
    }
}