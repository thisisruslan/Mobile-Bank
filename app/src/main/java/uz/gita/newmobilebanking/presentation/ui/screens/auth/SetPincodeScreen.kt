package uz.gita.newmobilebanking.presentation.ui.screens.auth

import android.os.Bundle
import android.text.InputType
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.newmobilebanking.R
import uz.gita.newmobilebanking.databinding.ScreenSetpincodeBinding
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputConnection
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import timber.log.Timber
import uz.gita.newmobilebanking.presentation.viewModel.auth.impl.SetPincodeVMImpl
import uz.gita.newmobilebanking.presentation.viewModel.auth.interfaces.SetPincodeVM
import uz.gita.newmobilebanking.utils.showToast


@AndroidEntryPoint
class SetPincodeScreen : Fragment(R.layout.screen_setpincode) {
    private val binding by viewBinding(ScreenSetpincodeBinding::bind)
    private val viewModel : SetPincodeVM by viewModels<SetPincodeVMImpl>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.txtPinEntry.setRawInputType(InputType.TYPE_CLASS_TEXT)
        binding.txtPinEntry.setTextIsSelectable(true)

        val ic: InputConnection? = binding.txtPinEntry.onCreateInputConnection(EditorInfo())
        binding.keyboard.setInputConnection(ic)

        binding.txtPinEntry.setOnPinEnteredListener {
            viewModel.setPincode(it.toString())
        }

        viewModel.confirmPinCodeLiveData.observe(viewLifecycleOwner, confirmPinCodeObserver)
        viewModel.isCorrectPinCodeLiveData.observe(viewLifecycleOwner, isCorrectPinCodeObserver)

    }

    private val confirmPinCodeObserver = Observer<Unit> {
        binding.tvTitle.text = "Confirm PIN-CLICK"
        binding.txtPinEntry.text!!.clear()
    }

    private val isCorrectPinCodeObserver = Observer<Boolean > {
        if (it) findNavController().navigate(R.id.action_global_mainScreen)
        else showToast("Incorrect!")
    }

}