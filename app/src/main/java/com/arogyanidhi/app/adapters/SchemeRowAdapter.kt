package com.arogyanidhi.app.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arogyanidhi.app.databinding.ItemSchemeRowBinding
import com.arogyanidhi.app.models.Scheme

class SchemeRowAdapter(
    private var schemes: List<Scheme>,
    private val onClick: (Scheme) -> Unit
) : RecyclerView.Adapter<SchemeRowAdapter.VH>() {

    inner class VH(val b: ItemSchemeRowBinding) : RecyclerView.ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val b = ItemSchemeRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(b)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val s = schemes[position]
        holder.b.tvName.text = s.name
        holder.b.tvCategory.text = s.category
        holder.b.tvDesc.text = s.description
        holder.b.tvBenefit.text = "✓ ${s.benefit}"
        holder.b.root.setOnClickListener { onClick(s) }
    }

    override fun getItemCount(): Int = schemes.size

    fun submit(list: List<Scheme>) {
        schemes = list
        notifyDataSetChanged()
    }
}
