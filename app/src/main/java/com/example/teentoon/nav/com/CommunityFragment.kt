package com.example.teentoon.nav.com

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teentoon.GroupAdapter
import com.example.teentoon.GroupModel
import com.example.teentoon.databinding.FragmentCommunityBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore

class CommunityFragment : Fragment() {

    private var _binding: FragmentCommunityBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: GroupAdapter
    private val firestore = FirebaseFirestore.getInstance()
    private val realtimeDB = FirebaseDatabase.getInstance().reference
    private val currentUserId = FirebaseAuth.getInstance().currentUser?.uid.orEmpty()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCommunityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = GroupAdapter(
            onGroupClick = { group ->
                val intent = Intent(requireContext(), ChatRoomActivity::class.java)
                intent.putExtra("communityId", group.id)
                startActivity(intent)
            },
            onJoinClick = { group ->
                joinGroup(group.id)
            }
        )

        binding.recyclerCom.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerCom.adapter = adapter

        binding.btnAddCommunity.setOnClickListener {
            startActivity(Intent(requireContext(), NewComActivity::class.java))
        }

        loadGroups()
    }

    private fun loadGroups() {
        val joinedGroupIds = mutableSetOf<String>()

        // Ambil group yang sudah diikuti user dari Realtime DB
        realtimeDB.child("Users").child(currentUserId).child("joinedGroups")
            .get()
            .addOnSuccessListener { snapshot ->
                joinedGroupIds.addAll(snapshot.children.mapNotNull { it.key })

                // Sekarang ambil semua community dari Firestore
                firestore.collection("community").get()
                    .addOnSuccessListener { query ->
                        val groupList = query.documents.mapNotNull { doc ->
                            val id = doc.id
                            val name = doc.getString("name") ?: return@mapNotNull null
                            val isJoined = joinedGroupIds.contains(id)
                            GroupModel(id = id, name = name, isJoined = isJoined)
                        }

                        adapter.submitList(groupList)
                    }
            }
    }

    private fun joinGroup(groupId: String) {
        // Tambahkan ke Firestore (field members)
        firestore.collection("community").document(groupId)
            .update("members", com.google.firebase.firestore.FieldValue.arrayUnion(currentUserId))

        // Tambahkan juga ke Realtime DB
        realtimeDB.child("Users").child(currentUserId).child("joinedGroups")
            .child(groupId).setValue(true)

        // Refresh tampilan
        loadGroups()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
