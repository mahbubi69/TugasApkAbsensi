package com.example.tugasapkabsensi.fragment

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tugasapkabsensi.adapter.ProsesAbsensiAdapter
import com.example.tugasapkabsensi.databinding.FragmentProsesPresensiBinding
import com.example.tugasapkabsensi.handler.UpdateProsesAbsensiOnclik
import com.example.tugasapkabsensi.mvvm.ProsesPresesntViewModel
import com.example.tugasapkabsensi.restApi.model.UpdateProsesAbsenSubmit
import com.example.tugasapkabsensi.restApi.response.ApiResponseSiswa
import com.example.tugasapkabsensi.util.SharedPrefencSiswa
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class ProsesPresensiFragment : Fragment(), UpdateProsesAbsensiOnclik {
    private var _binding: FragmentProsesPresensiBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProsesPresesntViewModel by viewModels()
    private lateinit var prosesAbsensiAdapter: ProsesAbsensiAdapter
    private lateinit var pref: SharedPrefencSiswa

    lateinit var token: String
    var idGuruMapel: Int? = null
    var idSiswa: Int? = null

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
        token = pref.getToken ?: ""
        idGuruMapel = pref.getIdGuruMapel
        idSiswa = pref.getIdSiswa

        initiateRv()
        initiatePagingData(token, idGuruMapel!!)
    }

    fun initiateRv() {
        prosesAbsensiAdapter = ProsesAbsensiAdapter(requireContext(), this)
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

    override fun updateProsesAbsen(hasilabsen: String) {
        val prosesAbsenSubmit = UpdateProsesAbsenSubmit(hasilabsen)
        viewModel.updateProsesAbsen(token, idGuruMapel!!, idSiswa!!, prosesAbsenSubmit).observe(
            viewLifecycleOwner, Observer { updateAbsen ->
                when (updateAbsen) {
                    is ApiResponseSiswa.Succes -> {
                        Timber.d("succes update ${updateAbsen.data}")
                    }
                    is ApiResponseSiswa.Error -> {
                        showErrorDialog(updateAbsen.errorMessage)
                    }
                    else -> {
                        Timber.d("Unknow Error")
                    }
                }
            }
        )
    }

    private fun showErrorDialog(message: String) {
        AlertDialog.Builder(requireContext()).apply {
            setTitle("ada kesalahan saat update absen")
            setMessage(message)
            setPositiveButton("OK") { p0, _ ->
                p0.dismiss()
            }
        }.create().show()
    }

}