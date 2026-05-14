package com.arogyanidhi.app.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arogyanidhi.app.databinding.ItemSavedResultBinding
import com.arogyanidhi.app.db.SavedResultEntity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SavedResultsAdapter(
    private var results: List<SavedResultEntity>,
    private val onDelete: (Long) -> Unit
) : RecyclerView.Adapter<SavedResultsAdapter.VH>() {

    private val fmt = SimpleDateFormat("dd MMM yyyy · HH:mm", Locale.getDefault())

    inner class VH(val b: ItemSavedResultBinding) : RecyclerView.ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val b = ItemSavedResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(b)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val r = results[position]
        holder.b.tvDate.text = "📅 ${fmt.format(Date(r.date))}"
        holder.b.tvCount.text = "${r.schemeCount} scheme${if (r.schemeCount == 1) "" else "s"} matched"
        val names = r.eligibleSchemeNames.split(",").filter { it.isNotBlank() }
        holder.b.tvSchemes.text = when {
            names.isEmpty() -> "No matches"
            names.size <= 2 -> names.joinToString(", ")
            else -> "${names.take(2).joinToString(", ")} +${names.size - 2} more"
        }
        holder.b.btnDelete.setOnClickListener { onDelete(r.id) }
    }

    override fun getItemCount(): Int = results.size

    fun submit(list: List<SavedResultEntity>) {
        results = list
        notifyDataSetChanged()
    }
}
