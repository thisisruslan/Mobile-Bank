package uz.gita.newmobilebanking.presentation.ui.screens.auth

import android.annotation.SuppressLint
import android.os.Bundle
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
import uz.gita.newmobilebanking.databinding.ScreenRegisterBinding
import uz.gita.newmobilebanking.presentation.viewModel.auth.impl.RegisterVMImpl
import uz.gita.newmobilebanking.presentation.viewModel.auth.interfaces.RegisterVM

@AndroidEntryPoint
class RegisterScreen : Fragment(R.layout.screen_register) {
    private val binding by viewBinding(ScreenRegisterBinding::bind)
    private val viewModel: RegisterVM by viewModels<RegisterVMImpl>()

    @SuppressLint("FragmentLiveDataObserve")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnNext.isEnabled = false
        combine(
            binding.firstName.textChanges().map {
//                if (it.startsWith(" ") || it.endsWith(" ")) {
//                    it.trim()
//                    binding.firstName.setText(it)
//                    false
//                } else
                    it.isNotEmpty()
            },
            binding.secondName.textChanges().map {
//                if (it.startsWith(" ") || it.endsWith(" ")) {
//                    it.trim()
//                    binding.secondName.setText(it)
//                    false
//                } else
                    it.isNotEmpty()
            },
            binding.etPhoneNumber.textChanges().map {
                it.endsWith(" ")
            },
            transform = { firstName, secondName, phoneNumber -> !phoneNumber && firstName && secondName }
        ).onEach { binding.btnNext.isEnabled = it }
            .launchIn(lifecycleScope) // lifecycle-runtime-ktx

        binding.btnNext.setOnClickListener {
            viewModel.openRegister2Screen()
        }

        viewModel.openRegister2ScreenLiveData.observe(this, openRegister2ScreenObserver)

    }

    private val openRegister2ScreenObserver = Observer<Unit> {
        findNavController().navigate(
            RegisterScreenDirections.actionRegisterScreenToRegister2Screen(
                binding.etPhoneNumber.unmaskedText.toString(),
                binding.firstName.text.toString().trim(),
                binding.secondName.text.toString().trim()
            )
        )
    }


}