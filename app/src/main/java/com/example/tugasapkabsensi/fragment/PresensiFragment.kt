package com.example.tugasapkabsensi.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tugasapkabsensi.adapter.GuruMapelAdapter
import com.example.tugasapkabsensi.databinding.FragmentPresensiBinding
import com.example.tugasapkabsensi.mvvm.GuruMapelViewModel
import com.example.tugasapkabsensi.util.SharedPrefencSiswa
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class PresensiFragment : Fragment() {
    private var _binding: FragmentPresensiBinding? = null
    private val binding get() = _binding!!

    private val viewModel: GuruMapelViewModel by viewModels()
    private lateinit var guruMapelAdapter: GuruMapelAdapter
    private lateinit var pref: SharedPrefencSiswa

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentPresensiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pref = SharedPrefencSiswa(requireContext())
        val token: String = pref.getToken ?: ""

        initiateRv()
        initiatePagingData(token)
    }

    fun initiateRv() {
        guruMapelAdapter = GuruMapelAdapter()
        guruMapelAdapter.addLoadStateListener { loadData ->
            if (loadData.append.endOfPaginationReached) {
                if (guruMapelAdapter.itemCount < 1) {
                    Timber.d("item tidak ada")
                }
            }
            when (loadData.refresh) {
                is LoadState.Loading -> {
                }
            }

            with(binding.rvListDaftarPresesni) {
                adapter = guruMapelAdapter
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            }
        }
    }

    fun initiatePagingData(token: String) {
        lifecycleScope.launch {
            viewModel.getDataGuruMapelMvvm(token).collect { dataGuruMapel ->
                guruMapelAdapter.submitData(dataGuruMapel)
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}