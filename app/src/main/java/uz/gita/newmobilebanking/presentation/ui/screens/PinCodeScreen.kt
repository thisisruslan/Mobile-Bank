package uz.gita.newmobilebanking.presentation.ui.screens

import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputConnection
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import uz.gita.newmobilebanking.R
import uz.gita.newmobilebanking.databinding.ScreenPincodeBinding
import uz.gita.newmobilebanking.presentation.viewModel.auth.impl.PinCodeVMImpl
import uz.gita.newmobilebanking.presentation.viewModel.auth.interfaces.PinCodeVM
import uz.gita.newmobilebanking.utils.showToast

@AndroidEntryPoint
class PinCodeScreen : Fragment(R.layout.screen_pincode) {
    private val binding by viewBinding(ScreenPincodeBinding::bind)
    private val viewModel : PinCodeVM by viewModels<PinCodeVMImpl>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.txtPinEntry.setRawInputType(InputType.TYPE_CLASS_TEXT)
        binding.txtPinEntry.setTextIsSelectable(true)

        val ic: InputConnection? = binding.txtPinEntry.onCreateInputConnection(EditorInfo())
        binding.keyboard.setInputConnection(ic)

        binding.txtPinEntry.setOnPinEnteredListener {
            viewModel.checkPinCode(it.toString())
            Timber.tag("RRR").v("pin code-> " + it.toString())
        }

        viewModel.isCorrectPinCodeLiveData.observe(viewLifecycleOwner, isCorrectPinCodeObserver)
    }

    private val isCorrectPinCodeObserver = Observer<Boolean> {
        if (it) findNavController().navigate(R.id.action_pinCodeScreen_to_mainScreen)
        else showToast("CLICK-PIN is incorrect!")
    }

}