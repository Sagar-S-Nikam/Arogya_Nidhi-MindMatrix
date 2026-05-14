package com.arogyanidhi.app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.arogyanidhi.app.R
import com.arogyanidhi.app.databinding.ItemDocSectionBinding
import com.arogyanidhi.app.models.Scheme

class DocSectionAdapter(
    private val schemes: List<Scheme>,
    private val checkedMap: MutableMap<String, MutableSet<Int>>,
    private val onToggle: (schemeId: String, index: Int, isChecked: Boolean) -> Unit
) : RecyclerView.Adapter<DocSectionAdapter.VH>() {

    inner class VH(val b: ItemDocSectionBinding) : RecyclerView.ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val b = ItemDocSectionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(b)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val scheme = schemes[position]
        holder.b.tvSchemeTitle.text = "📑 ${scheme.name}"
        holder.b.docList.removeAllViews()

        val checks = checkedMap.getOrPut(scheme.id) { mutableSetOf() }

        scheme.requiredDocs.forEachIndexed { i, doc ->
            val row = LayoutInflater.from(holder.itemView.context)
                .inflate(R.layout.item_doc_row, holder.b.docList, false)
            val tick = row.findViewById<View>(R.id.tick)
            val name = row.findViewById<TextView>(R.id.tvDocName)
            val upload = row.findViewById<TextView>(R.id.tvUpload)

            name.text = doc
            fun applyState(checked: Boolean) {
                tick.setBackgroundResource(
                    if (checked) R.drawable.bg_tick_on else R.drawable.bg_tick_off
                )
                if (checked) {
                    name.paintFlags = name.paintFlags or android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
                    name.setTextColor(
                        androidx.core.content.ContextCompat.getColor(row.context, R.color.text_secondary)
                    )
                } else {
                    name.paintFlags = name.paintFlags and android.graphics.Paint.STRIKE_THRU_TEXT_FLAG.inv()
                    name.setTextColor(
                        androidx.core.content.ContextCompat.getColor(row.context, R.color.text_primary)
                    )
                }
            }
            applyState(checks.contains(i))

            row.setOnClickListener {
                val nowChecked = !checks.contains(i)
                if (nowChecked) checks.add(i) else checks.remove(i)
                applyState(nowChecked)
                onToggle(scheme.id, i, nowChecked)
            }
            upload.setOnClickListener {
                Toast.makeText(row.context, "Upload coming soon", Toast.LENGTH_SHORT).show()
            }
            holder.b.docList.addView(row)
        }
    }

    override fun getItemCount(): Int = schemes.size
}
