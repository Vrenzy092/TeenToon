package com.example.teentoon.nav.prof

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.teentoon.*
import com.example.teentoon.databinding.FragmentProfileBinding
import com.example.teentoon.nav.com.ChatRoomActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth
    private lateinit var db: DatabaseReference
    private lateinit var database: FirebaseDatabase
    private val firestore = FirebaseFirestore.getInstance()

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
        val uid = user?.uid.orEmpty()

        // Load username
        if (uid.isNotEmpty()) {
            db.child(uid).child("username").get()
                .addOnSuccessListener { snapshot ->
                    val username = snapshot.value?.toString() ?: "Unknown"
                    binding.txtusername.text = username
                }
                .addOnFailureListener {
                    binding.txtusername.text = "GUEST"
                }

            // Load komunitas yang user ikuti
            loadUserCommunities(uid)
        } else {
            binding.txtusername.text = "GUEST"
        }

        // Tombol logout
        binding.btnLogout.setOnClickListener {
            auth.signOut()
            val intent = Intent(requireContext(), SignIn::class.java)
            startActivity(intent)
            requireActivity().finish()
        }

        // Backsound activity
        binding.btnBacksound.setOnClickListener {
            val intent = Intent(requireContext(), BacksoundActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }

    private fun loadUserCommunities(userId: String) {
        val recyclerView = binding.recyclermycom
        recyclerView.layoutManager = GridLayoutManager(requireContext(),2)

        db.child(userId).child("joinedGroups").get()
            .addOnSuccessListener { snapshot ->
                val communityIds = snapshot.children.mapNotNull { it.key }
                val communityList = mutableListOf< GroupModel>()

                if (communityIds.isEmpty()) {
                    recyclerView.adapter = MyComAdapter(emptyList()) {}
                    return@addOnSuccessListener
                }

                var loadedCount = 0
                for (id in communityIds) {
                    firestore.collection("community").document(id).get()
                        .addOnSuccessListener { doc ->
                            val name = doc.getString("name") ?: "Unnamed"
                            val image = doc.getString("imageUrl") ?: ""

                            communityList.add(GroupModel(id, name, image))
                            loadedCount++

                            if (loadedCount == communityIds.size) {
                                recyclerView.adapter = MyComAdapter(communityList) { selected ->
                                    val intent = Intent(requireContext(), ChatRoomActivity::class.java)
                                    intent.putExtra("communityId", selected.id)
                                    startActivity(intent)
                                }
                            }
                        }
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
