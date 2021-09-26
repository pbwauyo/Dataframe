package com.example.dataframe.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dataframe.R
import com.example.dataframe.data.models.DashboardItem
import com.example.dataframe.utils.ItemClickListener
import com.google.android.material.textview.MaterialTextView

class DashboardAdapter(
    private val itemsList: List<DashboardItem>,
    private val itemClickListener: ItemClickListener<DashboardItem>
) : RecyclerView.Adapter<DashboardAdapter.DashboardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardViewHolder {
        return DashboardViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: DashboardViewHolder, position: Int) {
        val item = itemsList[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            itemClickListener.onClick(item)
        }
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    class DashboardViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val imageView = view.findViewById<ImageView>(R.id.image_view)
        private val titleTextView = view.findViewById<MaterialTextView>(R.id.title_text_view)

        fun bind(dashboardItem: DashboardItem) {
            Glide.with(view.context)
                .load(dashboardItem.drawable)
                .centerCrop()
                .into(imageView)
            titleTextView.text = dashboardItem.title
        }

        companion object {
            fun create(parent: ViewGroup): DashboardViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.dashboard_item_row, parent, false)
                return DashboardViewHolder(view)
            }
        }
    }
}