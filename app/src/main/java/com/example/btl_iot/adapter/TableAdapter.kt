package com.example.btl_iot.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.btl_iot.databinding.TableRowBinding

class TableAdapter(private val data: List<RowData>) :
    RecyclerView.Adapter<TableAdapter.ViewHolder>() {

    data class RowData(val name: String, val lastTurnedOn: String, val lastTurnedOff: String)

    inner class ViewHolder(private val binding: TableRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(row: RowData) {
            binding.tvName.text = row.name
            binding.tvLastTurnedOn.text = row.lastTurnedOn
            binding.tvLastTurnedOff.text = row.lastTurnedOff
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = TableRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size
}

