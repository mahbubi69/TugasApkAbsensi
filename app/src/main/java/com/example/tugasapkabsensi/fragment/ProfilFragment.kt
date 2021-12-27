package com.example.tugasapkabsensi.fragment


import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.tugasapkabsensi.R
import com.example.tugasapkabsensi.activity.MainActivity
import com.example.tugasapkabsensi.databinding.FragmentProfilBinding
import com.example.tugasapkabsensi.mvvm.ProfileSiswaViewModel
import com.example.tugasapkabsensi.mvvm.UpdateImageViewModel
import com.example.tugasapkabsensi.restApi.model.SiswaProfilModel
import com.example.tugasapkabsensi.restApi.model.UpdateImagesubmit
import com.example.tugasapkabsensi.restApi.response.ApiResponseSiswa
import com.example.tugasapkabsensi.util.SharedPrefencSiswa
import com.example.tugasapkabsensi.value.Value
import com.github.dhaval2404.imagepicker.ImagePicker
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.lang.Exception


@AndroidEntryPoint
class ProfilFragment : Fragment() {
    private var _binding: FragmentProfilBinding? = null
    private val binding get() = _binding!!

    private val viewModelSiswa: ProfileSiswaViewModel by viewModels()
    private val viewModelImage: UpdateImageViewModel by viewModels()

    lateinit var pref: SharedPrefencSiswa

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentProfilBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pref = SharedPrefencSiswa(requireContext())
        val token = pref.getToken ?: ""
        val idSiswa = pref.getIdSiswa
        Timber.d("token is $token")

        initiateGetUserSiswa(token, idSiswa)
        popMenuUserSiswa()
        popMenuImg()

        binding.imgSiswa.setOnClickListener {
            findNavController().navigate(R.id.action_profilFragment2_to_detailImgProfileFragment)
        }

    }

    //memulai get profile
    fun initiateGetUserSiswa(token: String, idSiswa: Int) {
        Timber.d("token $token")
        Timber.d("idSiswa $idSiswa")

        viewModelSiswa.getProfileSiswa(token, idSiswa)
            .observe(viewLifecycleOwner, Observer { response ->
                when (response) {
                    is ApiResponseSiswa.Loading -> {
                        println("loading")
                    }

                    is ApiResponseSiswa.Succes -> {
                        initiateViewUserSiswa(response.data.data)
                    }

                    is ApiResponseSiswa.Error -> {
                        println("error")
                    }
                    else -> {
                        print("not found mas")
                    }
                }
            })
    }

    fun initiateViewUserSiswa(siswa: SiswaProfilModel) {
        binding.tvNamaSiswa.text = siswa.namaSiswa
        binding.tvNisn.text = siswa.nisn
        binding.tvTtl.text = siswa.tglLahir
        binding.tvAlamat.text = siswa.alamat

        Glide.with(binding.root)
            .load(Value.BASE_URL + siswa.imageSiswa)
            .placeholder(R.drawable.bg_user_siswa)
            .into(binding.imgSiswa)

        //detail img
        binding.imgSiswa.setOnClickListener {
            val img = siswa.imageSiswa
            val nextFragmentProfil =
                ProfilFragmentDirections.actionProfilFragment2ToDetailImgProfileFragment(
                    img
                )
            view?.findNavController()?.navigate(nextFragmentProfil)
        }
    }


    //edt/logout
    private fun popMenuUserSiswa() {
        binding.imgListUserSiswa.setOnClickListener {
            val popMenu: PopupMenu = PopupMenu(requireContext(), binding.imgListUserSiswa)
            popMenu.menuInflater.inflate(R.menu.nav_profil_user, popMenu.menu)
            popMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.edt_biodate -> {
                        findNavController().navigate(R.id.action_profilFragment2_to_editProfileSiswaFragment)
                        Timber.d("you clikked biodate")
                    }

                    R.id.log_out -> {
                        messageCustomDialogLogout()
//                        prosesMessageLogout()
                        Timber.d("you clikked logout")
                    }
                }
                true
            })
            popMenu.show()
        }
    }

//    private fun prosesMessageLogout() {
//        AlertDialog.Builder(requireContext()).apply {
//            setTitle("Logout")
//            setMessage("Apakah Anda Yakin Akan Keluar?")
//            setNegativeButton("Tidak") { p0, _ ->
//                p0.dismiss()
//            }
//            setPositiveButton("Iya") { _, _ ->
//                try {
//                    pref.clearTokenSiswa(Value.KEY_BASE_TOKEN)
//                    pref.clearIdDataGuruMapel(Value.KEY_BASE_ID_GURU_MAPEL)
//                    pref.clearIdSiswa(Value.KEY_BASE_ID_SISWA)
//                    pref.clearIdJurusanKelas(Value.KEY_BASE_ID_JURUSAN_KELAS)
//
//                    Timber.d("succes clear ${pref.clearIdDataGuruMapel("idGuruMapel")}")
//                } catch (e: Exception) {
//                    e.printStackTrace()
//                } finally {
//                    val inten = Intent(requireContext(), MainActivity::class.java)
//                    startActivity(inten)
//                    activity?.finish()
//                }
//            }
//        }.create().show()
//    }

    private fun messageCustomDialogLogout() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.custom_logout_dialog_lottie)

        val btnIya = dialog.findViewById<Button>(R.id.btn_logout_yes)
        val btnTidak = dialog.findViewById<Button>(R.id.btn_logout_no)

        btnIya.setOnClickListener {
            try {
                pref.clearTokenSiswa(Value.KEY_BASE_TOKEN)
                pref.clearIdDataGuruMapel(Value.KEY_BASE_ID_GURU_MAPEL)
                pref.clearIdSiswa(Value.KEY_BASE_ID_SISWA)
                pref.clearIdJurusanKelas(Value.KEY_BASE_ID_JURUSAN_KELAS)

                Timber.d("succes clear ${pref.clearIdDataGuruMapel("idGuruMapel")}")
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                val inten = Intent(requireContext(), MainActivity::class.java)
                startActivity(inten)
                activity?.finish()
            }
        }
        btnTidak.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }


    fun initiateUpdateImage(
        token: String,
        idSiswa: Int,
        submitImage: UpdateImagesubmit,
    ) {
        viewModelImage.updateImageSiswaVm(token, idSiswa, submitImage)
            .observe(viewLifecycleOwner,
                Observer { updateImage ->
                    when (updateImage) {
                        is ApiResponseSiswa.Succes -> {
                            view?.findNavController()?.popBackStack()
                        }
                        is ApiResponseSiswa.Error -> {
                            showErrorDialog(updateImage.errorMessage)
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
            setTitle("ada kesalahan pada saat upload image tolong periksa lagi")
            setMessage(message)
            setPositiveButton("OK") { p0, _ ->
                p0.dismiss()
            }
        }.create().show()
    }

    private fun popMenuImg() {
        binding.ftbEdtImg.setOnClickListener {
            val popupMenu: PopupMenu = PopupMenu(requireContext(), binding.imgSiswa)
            popupMenu.menuInflater.inflate(R.menu.nav_menu_img, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.profil_camera -> {
                        initiateToCamera()
                    }
                    R.id.profil_image -> {
                        initiateToGalery()
                    }
                }
                true
            })
            popupMenu.show()
        }
    }

    private fun initiateToCamera() {
        ImagePicker.with(requireActivity() as Activity)
            .cameraOnly()
            .crop()
            .maxResultSize(1080, 1080)
            .start()
        Timber.d("you clikked img camera")
    }

    private fun initiateToGalery() {
        ImagePicker.with(requireActivity() as Activity)
            .galleryOnly()
            .galleryMimeTypes(arrayOf("image/*"))
            .crop()
            .maxResultSize(1080, 1080)
            .start()
        Timber.d("you clikked img galery")
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == ImagePicker.REQUEST_CODE) {
            binding.imgSiswa.setImageURI(data?.data)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}

