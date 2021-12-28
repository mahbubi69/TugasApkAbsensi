package com.example.tugasapkabsensi.adapter

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.PopupMenu
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.tugasapkabsensi.R
import com.example.tugasapkabsensi.databinding.ItemPosesPresentBinding
import com.example.tugasapkabsensi.handler.UpdateProsesAbsensiOnclik
import com.example.tugasapkabsensi.restApi.model.ProsesAbsensiModel
import java.text.SimpleDateFormat
import java.util.*

class ProsesAbsensiAdapter(
    val c: Context,
    private val clikProsesPresent: UpdateProsesAbsensiOnclik,
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
            //parse date
            val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ROOT)
            val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.ROOT)
            val formattedDate = formatter.format(parser.parse(dataProsesPresent.tglAbsen) ?: Date())
            binding.tvTanggalProsesAbsensi.text = formattedDate
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
                        clikProsesPresent.updateProsesAbsen(hasilAbsen = "presensi")
                        binding.tvInfoPresent.text = "presensi"
//                        messageUpdateProsesAbsen(v)
                    }
                    R.id.edt_ijin -> {
                        clikProsesPresent.updateProsesAbsen(hasilAbsen = "ijin")
                        binding.tvInfoPresent.text = "ijin"
//                        messageUpdateProsesAbsen(v)
                    }
                }
                true
            }
            popupMenu.show()
        }
    }

//    private fun messageUpdateProsesAbsen(v: View) {
//        val dialog = Dialog(c)
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        dialog.setContentView(R.layout.custom_dialog_proses_present)
//
//        val btnConfirm = dialog.findViewById<Button>(R.id.confirm_button)
//        btnConfirm.setOnClickListener {
//            dialog.dismiss()
//        }
//        dialog.show()
//    }

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