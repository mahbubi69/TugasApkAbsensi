package com.example.tugasapkabsensi.fragment


import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.tugasapkabsensi.R
import com.example.tugasapkabsensi.activity.MainActivity
import com.example.tugasapkabsensi.databinding.FragmentProfilBinding
import com.example.tugasapkabsensi.handler.getFilePathFromUri
import com.example.tugasapkabsensi.handler.getImageUri
import com.example.tugasapkabsensi.mvvm.DeletImageViewModel
import com.example.tugasapkabsensi.mvvm.ProfileSiswaViewModel
import com.example.tugasapkabsensi.mvvm.UpdateImageViewModel
import com.example.tugasapkabsensi.restApi.model.DeletImageSiswaSubmit
import com.example.tugasapkabsensi.restApi.model.SiswaProfilModel
import com.example.tugasapkabsensi.restApi.model.UpdateProfileSubmit
import com.example.tugasapkabsensi.restApi.response.ApiResponseSiswa
import com.example.tugasapkabsensi.util.SharedPrefencSiswa
import com.example.tugasapkabsensi.value.Value
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
class ProfilFragment : Fragment() {
    private var _binding: FragmentProfilBinding? = null
    private val binding get() = _binding!!

    private val viewModelSiswa: ProfileSiswaViewModel by viewModels()
    private val viewModelImage: UpdateImageViewModel by viewModels()
    private val viewModelDeletImage: DeletImageViewModel by viewModels()

    lateinit var pref: SharedPrefencSiswa

    var token: String = ""
    var idSiswa: Int? = null

    private var imagePath: String? = null

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
        token = pref.getToken ?: ""
        idSiswa = pref.getIdSiswa
        Timber.d("token is $token")

        initiatePermission(requireContext())

        initiateGetUserSiswa(token, idSiswa!!)
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

                    is ApiResponseSiswa.Succes -> initiateViewUserSiswa(response.data.data)

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
        binding.tvAlamat.text = siswa.alamat

        //parse date
        val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ROOT)
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.ROOT)
        val formattedDate = formatter.format(parser.parse(siswa.tglLahir) ?: Date())
        binding.tvTtl.text = formattedDate

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
                        Timber.d("you clikked logout")
                    }
                }
                true
            })
            popMenu.show()
        }
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
                        takePictureOnCamera.launch()
                        Timber.d("upload camera Succes $takePictureOnCamera")
                    }
                    R.id.profil_image -> {
                        pickFileImage.launch("image/*")
                        Timber.d("upload camera Succes $pickFileImage")
                    }
                    R.id.save_foto -> {
                        initiateUpdateImage(token, idSiswa!!)
                    }

                    R.id.hapus_foto -> {
                        submitDeletImage()
                        Toast.makeText(requireContext(), "Berhasil Hapus Foto", Toast.LENGTH_SHORT)
                            .show()
                        Glide.with(binding.root)
                            .load(R.drawable.bg_user_siswa)
                            .into(binding.imgSiswa)
                    }
                }
                true
            })
            popupMenu.show()
        }
    }


    private val takePictureOnCamera =
        registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
            if (bitmap != null) {
                val uri = requireActivity().getImageUri(bitmap)
                imagePath = requireActivity().getFilePathFromUri(uri)
                binding.imgSiswa.setImageBitmap(bitmap)
            }
        }

    private val pickFileImage =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                imagePath = requireActivity().getFilePathFromUri(uri)
                binding.imgSiswa.setImageURI(uri)
            }
        }


    fun initiateUpdateImage(
        token: String,
        idSiswa: Int,
    ) {
        if (imagePath.isNullOrEmpty()) {
            showErrorDialog("tolong isi image terlebih dahulu")
            return
        }
        viewModelImage.editImageProfile(
            requireContext(),
            viewLifecycleOwner,
            token,
            idSiswa,
            imagePath ?: ""
        )
    }

    private fun initiatePermission(context: Context) {
        Dexter.withContext(context)
            .withPermissions(
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.CAMERA
            ).withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(p0: MultiplePermissionsReport?) {}
                override fun onPermissionRationaleShouldBeShown(
                    p0: MutableList<PermissionRequest>?,
                    p1: PermissionToken?,
                ) {
                    p1?.continuePermissionRequest()
                }
            }).withErrorListener {
            }.onSameThread()
            .check()
    }

    fun submitDeletImage() {
        val keyDeletImage = "delet"
        val deletImage = DeletImageSiswaSubmit(
            images = keyDeletImage
        )
        initiateDeletImageSiswa(token, idSiswa!!, deletImage)
        Timber.d("token $token")
        Timber.d("idSiswa $idSiswa")
    }


    //delet image
    fun initiateDeletImageSiswa(
        token: String,
        idSiswa: Int,
        submitDeletImageSiswa: DeletImageSiswaSubmit,
    ) {
        viewModelDeletImage.deletImageSiswa(token, idSiswa, submitDeletImageSiswa)
            .observe(viewLifecycleOwner,
                Observer { update ->
                    when (update) {
                        is ApiResponseSiswa.Succes -> {
                            Timber.d("succes update ${update.data}")
                        }
                        is ApiResponseSiswa.Error -> {
                            showErrorDialogDeletImg(update.errorMessage)
                        }
                        else -> {
                            Timber.d("Unknow Error")
                        }
                    }
                })
    }


    private fun showErrorDialogDeletImg(message: String) {
        AlertDialog.Builder(requireContext()).apply {
            setTitle("ada kasalahan delet img")
            setMessage(message)
            setPositiveButton("OK") { p0, _ ->
                p0.dismiss()
            }
        }.create().show()
    }

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


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}

