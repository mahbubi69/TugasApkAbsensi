package com.example.tugasapkabsensi.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.tugasapkabsensi.databinding.ItemRvPresensiBinding
import com.example.tugasapkabsensi.handler.DataGuruMapelOnclik
import com.example.tugasapkabsensi.restApi.model.GuruMapelModel

class GuruMapelAdapter(
    private val onClik: DataGuruMapelOnclik,
    ) :
    PagingDataAdapter<GuruMapelModel, GuruMapelAdapter.GuruMapelHolder>(
        DIFF_CALLBACK
    ) {
    override fun onBindViewHolder(holder: GuruMapelHolder, position: Int) {
        getItem(position)?.let { data ->
            holder.bind(data)

            holder.itemView.setOnClickListener {
                onClik.onClikItem(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuruMapelHolder {
        val binding =
            ItemRvPresensiBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GuruMapelHolder(binding)
    }

    inner class GuruMapelHolder(private val binding: ItemRvPresensiBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(dataGuruMapel: GuruMapelModel) {
            binding.tvNamaJadwalPresent.text = dataGuruMapel.idMapel
        }
    }

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<GuruMapelModel> =
            object : DiffUtil.ItemCallback<GuruMapelModel>() {
                override fun areItemsTheSame(
                    oldItem: GuruMapelModel,
                    newItem: GuruMapelModel,
                ): Boolean {
                    return oldItem.idGuruMapel == newItem.idGuruMapel && oldItem.idGuruMapel == newItem.idGuruMapel
                }

                override fun areContentsTheSame(
                    oldItem: GuruMapelModel,
                    newItem: GuruMapelModel,
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}