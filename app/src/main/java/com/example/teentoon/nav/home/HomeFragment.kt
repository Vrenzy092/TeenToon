package com.example.teentoon.nav.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.teentoon.databinding.FragmentHomeBinding
import com.example.teentoon.komik

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


        binding.btn.setOnClickListener {
            Detailkomik("Comic1")
        }


        /*binding.searchIcon.setOnClickListener {
            binding.root.findNavController().navigate(com.google.firebase.database.R.id.action_homeFragment_to_searchFragment)
        }*/



        return root
    }

    private fun Detailkomik(comicId: String) {
        val intent = Intent(requireContext(), komik::class.java)
        intent.putExtra("comic_id", comicId)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}