package com.arogyanidhi.app.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.arogyanidhi.app.R

class CategoryChipAdapter(
    private val categories: List<String>,
    private var selected: String,
    private val onSelect: (String) -> Unit
) : RecyclerView.Adapter<CategoryChipAdapter.VH>() {

    inner class VH(val tv: TextView) : RecyclerView.ViewHolder(tv)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_chip, parent, false) as TextView
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val c = categories[position]
        holder.tv.text = c
        val active = c == selected
        holder.tv.setBackgroundResource(
            if (active) R.drawable.bg_chip_on else R.drawable.bg_chip_off
        )
        holder.tv.setTextColor(
            ContextCompat.getColor(
                holder.tv.context,
                if (active) R.color.white else R.color.text_secondary
            )
        )
        holder.tv.setOnClickListener {
            val old = selected
            selected = c
            onSelect(c)
            notifyItemChanged(categories.indexOf(old))
            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int = categories.size
}
