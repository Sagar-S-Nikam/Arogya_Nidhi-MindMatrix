package com.arogyanidhi.app.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arogyanidhi.app.R
import com.arogyanidhi.app.databinding.ItemOnboardingBinding

data class OnboardingSlide(
    val iconRes: Int,
    val title: String,
    val desc: String
)

class OnboardingAdapter(
    private val slides: List<OnboardingSlide>
) : RecyclerView.Adapter<OnboardingAdapter.VH>() {

    inner class VH(val b: ItemOnboardingBinding) : RecyclerView.ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val b = ItemOnboardingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(b)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val s = slides[position]
        holder.b.imgIcon.setImageResource(s.iconRes)
        holder.b.tvTitle.text = s.title
        holder.b.tvDesc.text = s.desc
    }

    override fun getItemCount(): Int = slides.size
}
