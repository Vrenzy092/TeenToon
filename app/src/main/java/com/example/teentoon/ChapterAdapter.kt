package com.example.teentoon

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.teentoon.databinding.ItemChapterBinding

class ChapterAdapter(
    private val chapterList: List<Pair<String, String>>,
    private val onClick: (String) -> Unit
) : RecyclerView.Adapter<ChapterAdapter.ChapterViewHolder>() {

    inner class ChapterViewHolder(val binding: ItemChapterBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChapterViewHolder {
        val binding = ItemChapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChapterViewHolder, position: Int) {
        val (chapterId, url) = chapterList[position]
        holder.binding.txtchapters.text = "Chapter $chapterId"
        holder.binding.imageChapter.setOnClickListener {
            onClick(url)
        }
    }

    override fun getItemCount(): Int = chapterList.size
}