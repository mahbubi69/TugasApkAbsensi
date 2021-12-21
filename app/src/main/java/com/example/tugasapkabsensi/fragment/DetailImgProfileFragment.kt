package com.example.tugasapkabsensi.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.tugasapkabsensi.R
import com.example.tugasapkabsensi.databinding.FragmentDetailImgProfileBinding
import com.example.tugasapkabsensi.value.Value
import java.lang.Exception


class DetailImgProfileFragment : Fragment() {

    private var _binding: FragmentDetailImgProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailImgProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getDataArgumentProfile()
    }

    fun getDataArgumentProfile() {
        try {
            val args = arguments?.let { DetailImgProfileFragmentArgs.fromBundle(it) }
            val img = args?.img

            Glide.with(this)
                .load(Value.BASE_URL + img)
                .into(binding.imgDetailProfile)
        } catch (e: Exception) {
            print(e)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
