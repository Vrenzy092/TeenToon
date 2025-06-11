package com.example.teentoon.nav.prof

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.teentoon.SignIn
import com.example.teentoon.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth
    private lateinit var db: DatabaseReference
    private lateinit var database: FirebaseDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        db = database.getReference("Users")

        val user = auth.currentUser
        if (user != null) {
            val uid = user.uid

            // Ambil username dari Realtime Database
            db.child(uid).child("username")
                .get().addOnSuccessListener { snapshot ->
                    val username = snapshot.value?.toString() ?: "Unknown"
                    binding.txtusername.text = username

                }
                .addOnFailureListener {
                    binding.txtusername.text = "GUEST"
                }
        } else {
            binding.txtusername.text = "GUEST"
        }

        // Contoh tombol logout (opsional)
        binding.btnLogout.setOnClickListener {
            auth.signOut()

            val intent = Intent(requireContext(), SignIn::class.java)
            startActivity(intent)
            requireActivity().finish()
            // Arahkan ke halaman login jika diperlukan
        }

        binding.btnBacksound.setOnClickListener {
            val intent = Intent(requireContext(), BacksoundActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}