package com.example.teentoon

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.teentoon.databinding.ItemKomikBinding

class ComicAdapter(
    private val onItemClick: (String) -> Unit
) : RecyclerView.Adapter<ComicAdapter.KomikViewHolder>() {

    private val komikList = mutableListOf<Pair<String, Comic>>()

    fun updateData(newData: List<Pair<String, Comic>>) {
        komikList.clear()
        komikList.addAll(newData)
        notifyDataSetChanged()
    }

    inner class KomikViewHolder(val binding: ItemKomikBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KomikViewHolder {
        val binding = ItemKomikBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return KomikViewHolder(binding)
    }

    override fun onBindViewHolder(holder: KomikViewHolder, position: Int) {
        val (comicId, Comic) = komikList[position]
        holder.binding.tvtitle.text = Comic.Title

        Glide.with(holder.itemView.context)
            .load(Comic.Coverimg)
            .placeholder(R.drawable.ic_launcher_background) // optional
            .into(holder.binding.imageButtonKomik)

        holder.binding.imageButtonKomik.setOnClickListener {
            onItemClick(comicId)
        }
    }

    override fun getItemCount(): Int = komikList.size
}
