package com.example.tugasapkabsensi.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.tugasapkabsensi.R
import com.example.tugasapkabsensi.databinding.FragmentEditProfileSiswaBinding


class EditProfileSiswaFragment : Fragment() {

    private var _binding: FragmentEditProfileSiswaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentEditProfileSiswaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.edtIcBack.setOnClickListener {
            findNavController().navigate(R.id.action_editProfileSiswaFragment_to_profilFragment2)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}