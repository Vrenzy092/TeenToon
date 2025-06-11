package com.example.teentoon

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.teentoon.databinding.ItemGenreBinding

class GenreAdapter(
    private val genres: List<String>,
    private val onGenreClick: (String) -> Unit
) : RecyclerView.Adapter<GenreAdapter.GenreViewHolder>() {

    inner class GenreViewHolder(val binding: ItemGenreBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val binding = ItemGenreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GenreViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        val genre = genres[position]
        holder.binding.btnGenre.text = genre

        holder.binding.btnGenre.setOnClickListener {
            onGenreClick(genre)
        }
    }

    override fun getItemCount(): Int = genres.size
}