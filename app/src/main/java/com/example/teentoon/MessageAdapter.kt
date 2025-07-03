package com.example.teentoon

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MessageAdapter(private val messages: List<Message>) :
    RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    inner class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgProfile: ImageView = itemView.findViewById(R.id.imgProfile)
        val tvSender: TextView = itemView.findViewById(R.id.tvSender)
        val tvText: TextView = itemView.findViewById(R.id.tvText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_message, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = messages[position]
        holder.tvSender.text = message.senderName
        holder.tvText.text = message.text

        if (message.photoUrl.isNotEmpty()) {
            Glide.with(holder.itemView.context)
                .load(message.photoUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .circleCrop()
                .into(holder.imgProfile)
        } else {
            holder.imgProfile.setImageResource(R.drawable.ic_launcher_background)
        }
    }

    override fun getItemCount() = messages.size
}