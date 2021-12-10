package com.example.tugasapkabsensi.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tugasapkabsensi.databinding.FragmentHomeBinding
import com.example.tugasapkabsensi.value.DataSliderItemHome


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dataFotoMan = DataSliderItemHome().ListFotoHomeMan()
        binding.imageSlider.setImageList(dataFotoMan)

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}