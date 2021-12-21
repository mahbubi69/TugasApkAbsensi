package com.example.tugasapkabsensi.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.tugasapkabsensi.R
import com.example.tugasapkabsensi.databinding.ItemPosesPresentBinding
import com.example.tugasapkabsensi.restApi.model.ProsesAbsensiModel

class ProsesAbsensiAdapter(
    val c: Context,
) :
    PagingDataAdapter<ProsesAbsensiModel, ProsesAbsensiAdapter.ProsesAbsensiHolder>(
        DIFF_CALLBACK
    ) {

    inner class ProsesAbsensiHolder(private val binding: ItemPosesPresentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindData(dataProsesPresent: ProsesAbsensiModel) {
            binding.tvProsesStartJamAbnsesi.text = dataProsesPresent.startTime
            binding.tvProsesEndJamAbnsesi.text = dataProsesPresent.endTime
            binding.tvInfoPresent.text = dataProsesPresent.hasilAbsen
            binding.tvTanggalProsesAbsensi.text = dataProsesPresent.tglAbsen
            binding.tvInfoPresent.setOnClickListener { item ->
                popMenus(item)
            }
        }

        private fun popMenus(v: View) {
            val popupMenu = PopupMenu(c, v)
            popupMenu.menuInflater.inflate(R.menu.nav_proses_present_user, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.edt_presensi -> {

                    }
                    R.id.edt_ijin -> {

                    }

                }
                true
            }
            popupMenu.show()
        }
    }

    override fun onBindViewHolder(holder: ProsesAbsensiHolder, position: Int) {
        getItem(position)?.let { data ->
            holder.bindData(data)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProsesAbsensiHolder {
        val binding =
            ItemPosesPresentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProsesAbsensiHolder(binding)
    }

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<ProsesAbsensiModel> =
            object : DiffUtil.ItemCallback<ProsesAbsensiModel>() {
                override fun areItemsTheSame(
                    oldItem: ProsesAbsensiModel,
                    newItem: ProsesAbsensiModel,
                ): Boolean {
                    return oldItem.idAbsen == newItem.idAbsen && oldItem.idAbsen == newItem.idAbsen
                }

                override fun areContentsTheSame(
                    oldItem: ProsesAbsensiModel,
                    newItem: ProsesAbsensiModel,
                ): Boolean {
                    return oldItem == newItem
                }

            }
    }

}