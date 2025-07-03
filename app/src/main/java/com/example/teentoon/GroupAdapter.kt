package com.example.teentoon

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GroupAdapter(
    private val onGroupClick: (GroupModel) -> Unit,
    private val onJoinClick: (GroupModel) -> Unit
) : RecyclerView.Adapter<GroupAdapter.GroupViewHolder>() {

    private val items = mutableListOf<GroupModel>()

    fun submitList(data: List<GroupModel>) {
        items.clear()
        items.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_group, parent, false)
        return GroupViewHolder(view)
    }

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class GroupViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvName: TextView = view.findViewById(R.id.groupName)
        private val btnJoin: Button = view.findViewById(R.id.btnJoinGroup)

        fun bind(group: GroupModel) {
            tvName.text = group.name

            if (group.isJoined) {
                btnJoin.visibility = View.GONE
                itemView.setOnClickListener { onGroupClick(group) }
            } else {
                btnJoin.visibility = View.VISIBLE
                btnJoin.setOnClickListener { onJoinClick(group) }
                itemView.setOnClickListener {
                    // Optional: Tampilkan pesan "Gabung dulu"
                }
            }
        }
    }
}
