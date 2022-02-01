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
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import ru.ldralighieri.corbind.widget.textChanges
import uz.gita.newmobilebanking.R
import uz.gita.newmobilebanking.data.remote.request.auth.LoginRequest
import uz.gita.newmobilebanking.databinding.ScreenLoginBinding
import uz.gita.newmobilebanking.presentation.viewModel.auth.impl.LoginVMImpl
import uz.gita.newmobilebanking.presentation.viewModel.auth.interfaces.LoginVM
import uz.gita.newmobilebanking.utils.showToast

@AndroidEntryPoint
class LoginScreen : Fragment(R.layout.screen_login) {
    private val binding by viewBinding(ScreenLoginBinding::bind)
    private val viewModel: LoginVM by viewModels<LoginVMImpl>()

    @SuppressLint("FragmentLiveDataObserve")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.isEnabled = false
        binding.btnRegister.isEnabled = true

        combine(
            binding.etPhoneNumber.textChanges().map {
                Log.v("TTT", it.endsWith(" ").toString())
                it.endsWith(" ")
            },
            binding.etPassword.textChanges().map {
//                if ( it.endsWith(" ")) {
//                    it.trim()
//                    binding.etPassword.setText(it)
//                    true
//                } else
                it.length > 6
            },
            transform = { phoneNumber, password -> !phoneNumber && password }
        ).onEach { binding.btnLogin.isEnabled = it }
            .launchIn(lifecycleScope) // lifecycle-runtime-ktx


        binding.btnLogin.setOnClickListener {
            viewModel.userLogin(
                LoginRequest(
                    "+998" + binding.etPhoneNumber.unmaskedText.toString(),
                    binding.etPassword.text.toString().trim()
                )
            )
            Log.v("TTT", "----> " + binding.etPhoneNumber.unmaskedText.toString())
        }

        binding.btnRegister.setOnClickListener {
            viewModel.openRegisterScreen()
        }

        viewModel.ableButtonsLiveData.observe(viewLifecycleOwner, ableLoginButtonObserver)
        viewModel.visibilityProgressLiveData.observe(viewLifecycleOwner, visibilityProgressObserver)
        viewModel.errorMessageLiveData.observe(viewLifecycleOwner, errorMessageObserver)
        viewModel.openVerifyScreenLiveData.observe(this, openVerifyScreenObserver)
        viewModel.openRegisterScreenLiveData.observe(this, openRegisterScreenObserver)
    }

    private val openRegisterScreenObserver = Observer<Unit> {
        findNavController().navigate(R.id.action_loginScreen_to_registerScreen)
    }

    private val errorMessageObserver = Observer<String> {
        showToast(it)
    }

    private val openVerifyScreenObserver = Observer<String> {
        showToast(it)
        if (!it.startsWith("Bunday") && !it.startsWith("Parol"))
            findNavController().navigate(
                LoginScreenDirections.actionLoginScreenToVerifyScreen(
                    binding.etPhoneNumber.unmaskedText.toString()
                )
            )
    }

    private val ableLoginButtonObserver = Observer<Boolean> {
        binding.btnLogin.isEnabled = it
        binding.btnRegister.isEnabled = it
    }

    private val visibilityProgressObserver = Observer<Boolean> {
        if (it) binding.progress.show()
        else binding.progress.hide()
    }

}