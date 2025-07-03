package com.example.teentoon.nav.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teentoon.Comic
import com.example.teentoon.ComicAdapter
import com.example.teentoon.GenreAdapter
import com.example.teentoon.databinding.FragmentHomeBinding
import com.example.teentoon.komik
import com.google.firebase.database.*

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var comicAdapter: ComicAdapter
    private lateinit var genreAdapter: GenreAdapter

    private var fullComicList = listOf<Pair<String, Comic>>() // Semua komik
    private val genreList = listOf("All", "Slice of Life", "Action", "Comedy", "Drama", "Romance")
    private var currentSearchQuery: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        ViewModelProvider(this).get(HomeViewModel::class.java) // opsional kalau pakai ViewModel
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup Adapter Komik
        comicAdapter = ComicAdapter { comicId ->
            val intent = Intent(requireContext(), komik::class.java)
            intent.putExtra("comic_id", comicId)
            startActivity(intent)
        }

        binding.recyclerview.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerview.adapter = comicAdapter

        // Setup Adapter Genre
        genreAdapter = GenreAdapter(genreList) { selectedGenre ->
            filterByGenre(selectedGenre)
        }

        binding.recyclerGenre.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerGenre.adapter = genreAdapter

        // Ambil data dari Firebase
        FirebaseDatabase.getInstance().getReference("comic")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val newList = mutableListOf<Pair<String, Comic>>()
                    for (comicSnapshot in snapshot.children) {
                        val comicId = comicSnapshot.key ?: continue
                        val komik = comicSnapshot.getValue(Comic::class.java)
                        if (komik != null) {
                            newList.add(comicId to komik)
                        }
                    }
                    applySearchFilter()
                    fullComicList = newList
                    comicAdapter.updateData(newList)
                }

                override fun onCancelled(error: DatabaseError) {
                    // Log error jika perlu
                }
            })
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false
            override fun onQueryTextChange(newText: String?): Boolean {
                currentSearchQuery = newText ?: ""
                applySearchFilter()
                return true
            }
        })
    }

    private fun applySearchFilter() {
        val filteredList = fullComicList.filter { (_, Comic) ->
            Comic.Title.contains(currentSearchQuery, ignoreCase = true)
        }
        comicAdapter.updateData(filteredList)
    }

    private fun filterByGenre(genre: String) {
        val filteredList = if (genre == "All") {
            fullComicList
        } else {
            fullComicList.filter {
                it.second.Genre.contains(genre, ignoreCase = true)
            }
        }
        comicAdapter.updateData(filteredList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}