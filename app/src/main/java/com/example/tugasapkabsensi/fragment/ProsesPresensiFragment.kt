package com.example.tugasapkabsensi.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tugasapkabsensi.adapter.ProsesAbsensiAdapter
import com.example.tugasapkabsensi.databinding.FragmentProsesPresensiBinding
import com.example.tugasapkabsensi.mvvm.ProsesPresesntViewModel
import com.example.tugasapkabsensi.util.SharedPrefencSiswa
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class ProsesPresensiFragment : Fragment() {
    private var _binding: FragmentProsesPresensiBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProsesPresesntViewModel by viewModels()
    private lateinit var prosesAbsensiAdapter: ProsesAbsensiAdapter
    private lateinit var pref: SharedPrefencSiswa

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProsesPresensiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pref = SharedPrefencSiswa(requireContext())
        val token: String = pref.getToken ?: ""
        val idGuruMapel: Int = pref.getIdGuruMapel

        initiateRv()
        initiatePagingData(token, idGuruMapel)
    }

    fun initiateRv() {
        prosesAbsensiAdapter = ProsesAbsensiAdapter(requireContext())
        prosesAbsensiAdapter.addLoadStateListener { loadData ->
            if (loadData.append.endOfPaginationReached) {
                if (prosesAbsensiAdapter.itemCount < 1) {
                    Timber.d("item tidak ada")
                }
            }
            when (loadData.refresh) {
                is LoadState.Loading -> {
                }
            }

            with(binding.rvProsesDetailPresent) {
                adapter = prosesAbsensiAdapter
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            }
        }
    }

    fun initiatePagingData(token: String, idGuruMapel: Int) {
        lifecycleScope.launch {
            viewModel.getDataProsesAbsensiMvvm(token, idGuruMapel).collect { dataProsesAbsen ->
                prosesAbsensiAdapter.submitData(dataProsesAbsen)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}