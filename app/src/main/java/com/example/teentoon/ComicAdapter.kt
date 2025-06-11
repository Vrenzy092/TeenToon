package com.example.teentoon

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.teentoon.databinding.ItemKomikBinding

class ComicAdapter(
    private val onItemClick: (String) -> Unit
) : RecyclerView.Adapter<ComicAdapter.KomikViewHolder>() {

    private val fullList = mutableListOf<Pair<String, Comic>>()
    private val currentList = mutableListOf<Pair<String, Comic>>()

    fun updateData(newData: List<Pair<String, Comic>>) {
        fullList.clear()
        fullList.addAll(newData)
        currentList.clear()
        currentList.addAll(newData)
        notifyDataSetChanged()
    }

    fun filterByGenre(genre: String) {
        currentList.clear()
        if (genre == "All") {
            currentList.addAll(fullList)
        } else {
            val filtered = fullList.filter {
                it.second.Genre.equals(genre, ignoreCase = true)
            }
            currentList.addAll(filtered)
        }
        notifyDataSetChanged()
    }

    inner class KomikViewHolder(val binding: ItemKomikBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KomikViewHolder {
        val binding = ItemKomikBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return KomikViewHolder(binding)
    }

    override fun onBindViewHolder(holder: KomikViewHolder, position: Int) {
        val (comicId, Comic) = currentList[position]
        holder.binding.tvtitle.text = Comic.Title

        Glide.with(holder.itemView.context)
            .load(Comic.Coverimg)
            .placeholder(R.drawable.ic_launcher_background)
            .into(holder.binding.imageButtonKomik)

        holder.binding.imageButtonKomik.setOnClickListener {
            onItemClick(comicId)
        }
    }

    override fun getItemCount(): Int = currentList.size
}
