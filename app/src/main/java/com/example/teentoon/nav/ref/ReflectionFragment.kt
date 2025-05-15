package com.example.teentoon.nav.ref

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.teentoon.databinding.FragmentReflectionBinding

class ReflectionFragment : Fragment() {

    private var _binding: FragmentReflectionBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val reflectionViewModel =
            ViewModelProvider(this).get(ReflectionViewModel::class.java)

        _binding = FragmentReflectionBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textReflection
        reflectionViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
            return root
    }

    override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
    }
}