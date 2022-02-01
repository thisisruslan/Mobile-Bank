package uz.gita.newmobilebanking.presentation.ui.pages

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.github.dhaval2404.imagepicker.ImagePicker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import okhttp3.ResponseBody
import ru.ldralighieri.corbind.widget.textChanges
import uz.gita.newmobilebanking.R
import uz.gita.newmobilebanking.data.remote.request.profile.ProfileEditRequest
import uz.gita.newmobilebanking.databinding.PageProfileBinding
import uz.gita.newmobilebanking.presentation.viewModel.pages.profile.ProfilePageVM
import uz.gita.newmobilebanking.presentation.viewModel.pages.profile.ProfilePageVMImpl
import uz.gita.newmobilebanking.utils.FileUtils
import uz.gita.newmobilebanking.utils.showToast
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

@AndroidEntryPoint
class ProfilePage : Fragment(R.layout.page_profile) {
    private val binding by viewBinding(PageProfileBinding::bind)
    private val viewModel: ProfilePageVM by viewModels<ProfilePageVMImpl>()
    private var isInfoChanged = false

    override fun onResume() {
        super.onResume()
        viewModel.userDownloadAvatar()
        viewModel.userGetProfileInfo()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setEditTextState(false)
        viewModel.userDownloadAvatar()
        viewModel.userGetProfileInfo()

        binding.firstName.addTextChangedListener { isInfoChanged = true }
        binding.secondName.addTextChangedListener { isInfoChanged = true }
        binding.password.addTextChangedListener { isInfoChanged = true }

        binding.btnEditProfile.setOnClickListener {
            binding.btnEditProfile.visibility = View.GONE
            binding.btnApplyChanges.visibility = View.VISIBLE
            binding.btnApplyChanges.isEnabled = false
            setEditTextState(true)

            if (isInfoChanged) {
                combine(
                    binding.firstName.textChanges().map {
                        it.isNotEmpty()
                    },
                    binding.secondName.textChanges().map {
                        it.isNotEmpty()
                    },
                    binding.password.textChanges().map {
                        it.isNotEmpty()
                    },
                    transform = { firstName, secondName, password ->
                        firstName && secondName && password
                    }
                ).onEach { binding.btnApplyChanges.isEnabled = it }
                    .launchIn(lifecycleScope) // lifecycle-runtime-ktx
            }
        }

        binding.btnApplyChanges.setOnClickListener {
            binding.btnEditProfile.visibility = View.VISIBLE
            binding.btnApplyChanges.visibility = View.GONE
            setEditTextState(false)
            viewModel.userChangeProfileInfo(
                ProfileEditRequest(
                    binding.firstName.text.toString(),
                    binding.secondName.text.toString(),
                    binding.password.text.toString()
                )
            )
            clearFocus()
        }

        binding.avatar.setOnClickListener {
            ImagePicker.with(requireActivity())
                .compress(1024)
                .maxResultSize(512, 512)
                .saveDir(
                    File(
                        requireContext().getExternalFilesDir(null)?.absolutePath,
                        "MobileBankingAvatar"
                    )
                )
                .createIntent { startForProfileImageResult.launch(it) }
        }
        viewModel.apply {
            errorMessageLiveData.observe(viewLifecycleOwner, { showToast(it) })
            successGetInfoLiveData.observe(viewLifecycleOwner, {
                binding.firstName.setText(it.data.firstName)
                binding.secondName.setText(it.data.lastName)
                binding.phoneNumber.setText(it.data.phone)
                binding.password.setText(it.data.password)
            })
            visibilityProgressLiveData.observe(viewLifecycleOwner, {
                binding.progress.visibility = if (it) View.VISIBLE else View.GONE
            })
            successChangeInfoLiveData.observe(viewLifecycleOwner,{ showToast("Successfully changed!") })
            successUploadAvatarLiveData.observe(viewLifecycleOwner, {
                showToast(it)

            })
            successDownloadAvatarLiveData.observe(this, {
                downloadImage(it)
            })
        }


    }

    //to pick image from gallery, etc. and upload it to server
    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            when (resultCode) {
                Activity.RESULT_OK -> {
                    val fileUri = data?.data!!
                    binding.avatar.setImageURI(fileUri)
                    viewModel.userUploadAvatar(File(FileUtils.getPath(requireContext(), fileUri)))
                }
                ImagePicker.RESULT_ERROR -> {
                    Toast.makeText(requireContext(), ImagePicker.getError(data), Toast.LENGTH_SHORT)
                        .show()
                }
                else -> {
                    Toast.makeText(requireContext(), "Task Cancelled", Toast.LENGTH_SHORT).show()
                }
            }
        }

    private fun setEditTextState(isEnabled: Boolean) {
        binding.firstName.isEnabled = isEnabled
        binding.secondName.isEnabled = isEnabled
        binding.password.isEnabled = isEnabled
    }

    private fun clearFocus() {
        binding.firstName.clearFocus()
        binding.secondName.clearFocus()
        binding.password.clearFocus()
    }


    private fun downloadImage(body: ResponseBody): Boolean {
        return try {
            var `in`: InputStream? = null
            var out: FileOutputStream? = null
            try {
                `in` = body.byteStream()
                out = FileOutputStream(
                    requireActivity().getExternalFilesDir(null)
                        .toString() + File.separator + "Android.jpg"
                )
                var c: Int
                while (`in`.read().also { c = it } != -1) {
                    out?.write(c)
                }
            } catch (e: IOException) {
                return false
            } finally {
                `in`?.close()
                out?.close()
            }
            val width: Int
            val height: Int
            val bMap = BitmapFactory.decodeFile(
                requireActivity().getExternalFilesDir(null)
                    .toString() + File.separator + "Android.jpg"
            )
            width = 2 * bMap.width
            height = 3 * bMap.height
            val bMap2 = Bitmap.createScaledBitmap(bMap, width, height, false)
            binding.avatar.setImageBitmap(bMap2)
            true
        } catch (e: IOException) {
            false
        }
    }


}