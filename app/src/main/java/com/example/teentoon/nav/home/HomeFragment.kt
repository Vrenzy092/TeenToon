package com.example.teentoon.nav.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.teentoon.databinding.FragmentHomeBinding
import androidx.recyclerview.widget.GridLayoutManager
import com.example.teentoon.Comic
import com.example.teentoon.ComicAdapter
import com.example.teentoon.komik
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        // Ambil user ID dari Firebase Auth

        return root
    }

    private lateinit var comicAdapter: ComicAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Ganti LinearLayoutManager dengan GridLayoutManager (misal 2 kolom)
        comicAdapter = ComicAdapter { comicId ->
            Detail(comicId)
        }

        binding.recyclerview.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerview.adapter = comicAdapter

        val database = FirebaseDatabase.getInstance().getReference("comic")
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val newList = mutableListOf<Pair<String, Comic>>() // Pair<ComicId, Komik>
                for (comicSnapshot in snapshot.children) {
                    val comicId = comicSnapshot.key ?: continue
                    val komik = comicSnapshot.getValue(Comic::class.java)
                    if (komik != null) {
                        newList.add(comicId to komik)
                    }
                }
                comicAdapter.updateData(newList) // Update data ke adapter
            }

            override fun onCancelled(error: DatabaseError) {
                // Log error
            }
        })
    }

    private fun Detail(comicId: String) {
        val intent = Intent(requireContext(), komik::class.java)
        intent.putExtra("comic_id", comicId)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}