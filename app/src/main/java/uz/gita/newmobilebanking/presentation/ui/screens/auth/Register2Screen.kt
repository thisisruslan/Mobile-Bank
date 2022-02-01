package uz.gita.newmobilebanking.presentation.ui.screens.auth

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import ru.ldralighieri.corbind.widget.textChanges
import uz.gita.newmobilebanking.R
import uz.gita.newmobilebanking.data.remote.request.auth.RegisterRequest
import uz.gita.newmobilebanking.databinding.ScreenRegister2Binding
import uz.gita.newmobilebanking.presentation.viewModel.auth.impl.Register2VMImpl
import uz.gita.newmobilebanking.presentation.viewModel.auth.interfaces.Register2VM
import uz.gita.newmobilebanking.utils.showToast


@AndroidEntryPoint
class Register2Screen : Fragment(R.layout.screen_register2) {
    private val binding by viewBinding(ScreenRegister2Binding::bind)
    private val viewModel: Register2VM by viewModels<Register2VMImpl>()
    private val args: Register2ScreenArgs by navArgs()

    @SuppressLint("FragmentLiveDataObserve")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRegister.isEnabled = false

        combine(
            binding.etPassword.textChanges().map {
                it.length > 6
            },

            binding.confirmPassword.textChanges().map {
                binding.etPassword.text.toString() == it.toString()
            },

            transform = { etPassword, confirmPassword ->
                etPassword && confirmPassword
            }
        ).onEach { binding.btnRegister.isEnabled = it }
            .launchIn(lifecycleScope) // lifecycle-runtime-ktx

        binding.btnRegister.setOnClickListener {
            viewModel.userRegister(
                RegisterRequest(
                    args.firstName,
                    args.secondName,
                    "+998" + args.phoneNumber,
                    binding.confirmPassword.text.toString().trim()
                )
            )
        }

        viewModel.ableRegisterButtonLiveData.observe(viewLifecycleOwner, ableLoginButtonObserver)
        viewModel.visibilityProgressLiveData.observe(viewLifecycleOwner, visibilityProgressObserver)
        viewModel.errorMessageLiveData.observe(viewLifecycleOwner, errorMessageObserver)
        viewModel.openVerifyScreenLiveData.observe(this, openVerifyScreenObserver)
    }

    private val errorMessageObserver = Observer<String> {
        showToast(it)
    }

    private val openVerifyScreenObserver = Observer<String> {
        showToast(it)
        if (!it.startsWith("Bunday"))
            findNavController().navigate(
                Register2ScreenDirections.actionRegister2ScreenToVerifyScreen(args.phoneNumber)
            )
    }

    private val ableLoginButtonObserver = Observer<Boolean> {
        binding.btnRegister.isEnabled = it
    }

    private val visibilityProgressObserver = Observer<Boolean> {
        if (it) binding.progress.show()
        else binding.progress.hide()
    }
}