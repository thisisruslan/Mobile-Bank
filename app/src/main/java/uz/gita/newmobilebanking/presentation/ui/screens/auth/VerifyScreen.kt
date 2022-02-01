package uz.gita.newmobilebanking.presentation.ui.screens.auth

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.newmobilebanking.R
import uz.gita.newmobilebanking.data.remote.request.auth.VerifyRequest
import uz.gita.newmobilebanking.databinding.ScreenVerifyBinding
import uz.gita.newmobilebanking.presentation.viewModel.auth.impl.VerifyVMImpl
import uz.gita.newmobilebanking.presentation.viewModel.auth.interfaces.VerifyVM
import uz.gita.newmobilebanking.utils.showToast


@AndroidEntryPoint
class VerifyScreen : Fragment(R.layout.screen_verify) {
    private val binding by viewBinding(ScreenVerifyBinding::bind)
    private val viewModel: VerifyVM by viewModels<VerifyVMImpl>()
    private val args: VerifyScreenArgs by navArgs()

    @SuppressLint("FragmentLiveDataObserve")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnVerify.isEnabled = false

        binding.btnVerify.setOnClickListener {
            viewModel.userVerify(
                VerifyRequest(
                    "+998" + args.phoneNumber,
                    binding.txtPinEntry.text.toString()
                )
            )
        }

        binding.txtPinEntry.setOnPinEnteredListener { str ->
            if (str.length == 6) binding.btnVerify.isEnabled = true
//            if (str.toString() == "1234") {
//                Toast.makeText(requireContext(), "SUCCESS", Toast.LENGTH_SHORT).show()
//            } else {
//                Toast.makeText(requireContext(), "FAIL", Toast.LENGTH_SHORT).show()
//                binding.txtPinEntry.text = null
//            }
        }

        viewModel.ableVerifyButtonLiveData.observe(viewLifecycleOwner, ableVerifyButtonObserver)
        viewModel.visibilityProgressLiveData.observe(viewLifecycleOwner, visibilityProgressObserver)
        viewModel.errorMessageLiveData.observe(viewLifecycleOwner, errorMessageObserver)
        viewModel.openSetPinScreenLiveData.observe(this, openSetPinScreenObserver)

    }

    private val errorMessageObserver = Observer<String> {
        showToast(it)
    }
    private val openSetPinScreenObserver = Observer<String> {
        showToast(it)
        findNavController().navigate(R.id.action_verifyScreen_to_setPincodeScreen)

    }
    private val ableVerifyButtonObserver = Observer<Boolean> {
        binding.btnVerify.isEnabled = it
    }
    private val visibilityProgressObserver = Observer<Boolean> {
        if (it) binding.progress.show()
        else binding.progress.hide()
    }
}