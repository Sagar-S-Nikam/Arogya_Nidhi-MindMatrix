package com.arogyanidhi.app.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arogyanidhi.app.databinding.ItemSchemeCardBinding
import com.arogyanidhi.app.models.Scheme

class SchemeCardAdapter(
    private val schemes: List<Scheme>,
    private val showWhy: Boolean = true,
    private val onViewDocs: (Scheme) -> Unit
) : RecyclerView.Adapter<SchemeCardAdapter.VH>() {

    inner class VH(val b: ItemSchemeCardBinding) : RecyclerView.ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val b = ItemSchemeCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(b)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val s = schemes[position]
        holder.b.tvSchemeName.text = s.name
        holder.b.tvBenefit.text = "✓ ${s.benefit}"
        holder.b.tvDescription.text = s.description
        if (showWhy) {
            holder.b.tvWhyQualify.visibility = android.view.View.VISIBLE
            holder.b.tvWhyQualify.text = "💡 Why you qualify: ${s.whyQualify}"
        } else {
            holder.b.tvWhyQualify.visibility = android.view.View.GONE
        }
        holder.b.tvViewDocs.setOnClickListener { onViewDocs(s) }
    }

    override fun getItemCount(): Int = schemes.size
}
