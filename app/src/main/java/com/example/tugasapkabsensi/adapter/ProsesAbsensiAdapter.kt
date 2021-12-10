package com.example.tugasapkabsensi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.tugasapkabsensi.databinding.ItemPosesPresentBinding
import com.example.tugasapkabsensi.restApi.model.ProsesAbsensiModel

class ProsesAbsensiAdapter() :
    PagingDataAdapter<ProsesAbsensiModel, ProsesAbsensiAdapter.ProsesAbsensiHolder>(
        DIFF_CALLBACK
    ) {

    inner class ProsesAbsensiHolder(private val binding: ItemPosesPresentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindData(dataProsesPresent: ProsesAbsensiModel) {
            binding.tvProsesStartJamAbnsesi.text = dataProsesPresent.startTime
            binding.tvProsesEndJamAbnsesi.text = dataProsesPresent.endTime
            binding.tvInfoPresent.text = dataProsesPresent.hasilAbsen
        }
    }

    override fun onBindViewHolder(holder: ProsesAbsensiHolder, position: Int) {
        getItem(position)?.let { data ->
            holder.bindData(data)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProsesAbsensiHolder {
        val binding = ItemPosesPresentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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