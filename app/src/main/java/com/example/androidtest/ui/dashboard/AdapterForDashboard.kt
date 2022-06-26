package com.example.androidtest.ui.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.androidtest.R
import com.example.androidtest.repository.retrofit.model.Item

class AdapterForDashboard(
    private var data: ArrayList<Item>,
    private val itemClickListener: OnClick
) :
    RecyclerView.Adapter<AdapterForDashboard.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tv_name)
        val tvDose: TextView = itemView.findViewById(R.id.tv_dose)
        val tvStrength: TextView = itemView.findViewById(R.id.tv_strength)
        val rootLayout: ConstraintLayout = itemView.findViewById(R.id.layout_root)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvName.text = data[holder.adapterPosition].name
        holder.tvDose.text = data[holder.adapterPosition].dose
        holder.tvStrength.text = data[holder.adapterPosition].strength
        holder.rootLayout.setOnClickListener { itemClickListener.onItemClicked(data[holder.adapterPosition]) }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun updateDataList(data: ArrayList<Item>) {
        if (!this.data.isNullOrEmpty()) {
            this.data.clear()
        } else {
            this.data = ArrayList<Item>()
        }
        this.data.addAll(data)
    }

    interface OnClick {
        fun onItemClicked(item: Item)
    }
}