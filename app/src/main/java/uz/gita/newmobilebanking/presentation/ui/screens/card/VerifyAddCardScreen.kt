package uz.gita.newmobilebanking.presentation.ui.screens.card

import android.annotation.SuppressLint
import android.os.Bundle
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
import uz.gita.newmobilebanking.data.remote.request.card.VerifyAddCardRequest
import uz.gita.newmobilebanking.data.remote.response.card.VerifyAddCardResponse
import uz.gita.newmobilebanking.databinding.ScreenVerifyAddCardBinding
import uz.gita.newmobilebanking.presentation.viewModel.card.impl.VerifyAddCardScreenVMImpl
import uz.gita.newmobilebanking.presentation.viewModel.card.interfaces.VerifyAddCardScreenVM
import uz.gita.newmobilebanking.utils.showToast

@AndroidEntryPoint
class VerifyAddCardScreen : Fragment(R.layout.screen_verify_add_card) {
    private val viewModel: VerifyAddCardScreenVM by viewModels<VerifyAddCardScreenVMImpl>()
    private val binding by viewBinding(ScreenVerifyAddCardBinding::bind)
    private val args: VerifyAddCardScreenArgs by navArgs()

    @SuppressLint("FragmentLiveDataObserve")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.btnVerify.isEnabled = false
        binding.txtPinEntry.setOnPinEnteredListener { str ->
            if (str.length == 6) binding.btnVerify.isEnabled = true
        }

        binding.btnVerify.setOnClickListener {
            viewModel.userVerify(
                VerifyAddCardRequest(
                    args.cardNumber,
                    binding.txtPinEntry.text.toString()
                )
            )
        }

        viewModel.ableVerifyButtonLiveData.observe(viewLifecycleOwner, ableVerifyButtonObserver)
        viewModel.visibilityProgressLiveData.observe(viewLifecycleOwner, visibilityProgressObserver)
        viewModel.errorMessageLiveData.observe(viewLifecycleOwner, errorMessageObserver)
        viewModel.successVerifyLiveData.observe(this, successVerifyObserver)

    }

    private val errorMessageObserver = Observer<String> {
        showToast(it)
    }
    private val successVerifyObserver = Observer<VerifyAddCardResponse> {
        findNavController().popBackStack()

    }
    private val ableVerifyButtonObserver = Observer<Boolean> {
        binding.btnVerify.isEnabled = it
    }
    private val visibilityProgressObserver = Observer<Boolean> {
        if (it) binding.progress.show()
        else binding.progress.hide()
    }
}