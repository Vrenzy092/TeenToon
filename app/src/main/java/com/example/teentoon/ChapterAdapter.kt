package com.example.teentoon

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.teentoon.Chapters
import com.example.teentoon.databinding.ItemChapterBinding

class ChapterAdapter(
    private val chapterList: List<Pair<String, Chapters>>,
    private val onClick: (String) -> Unit
) : RecyclerView.Adapter<ChapterAdapter.ChapterViewHolder>() {

    inner class ChapterViewHolder(val binding: ItemChapterBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChapterViewHolder {
        val binding = ItemChapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChapterViewHolder, position: Int) {
        val (chapterId, Chapters) = chapterList[position]
        val cleanChapterId = chapterId.replace("\"", "")
        holder.binding.txtchapters.text = "Chapter $cleanChapterId"

        Glide.with(holder.itemView.context)
            .load(Chapters.Img)
            .placeholder(R.drawable.ic_launcher_background) // optional
            .into(holder.binding.imageChapter)

        holder.binding.imageChapter.setOnClickListener {
            onClick(Chapters.Url)
        }
    }

    override fun getItemCount(): Int = chapterList.size
}