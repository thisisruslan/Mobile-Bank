package uz.gita.newmobilebanking.presentation.ui.screens.card

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.newmobilebanking.R
import uz.gita.newmobilebanking.data.remote.request.card.EditCardRequest
import uz.gita.newmobilebanking.databinding.ScreenEditCardBinding
import uz.gita.newmobilebanking.presentation.viewModel.card.impl.EditCardScreenVMImpl
import uz.gita.newmobilebanking.presentation.viewModel.card.interfaces.EditCardScreenVM
import uz.gita.newmobilebanking.utils.showToast

@AndroidEntryPoint
class EditCardScreen : Fragment(R.layout.screen_edit_card) {
    private val binding by viewBinding(ScreenEditCardBinding::bind)
    private val viewModel: EditCardScreenVM by viewModels<EditCardScreenVMImpl>()
    private val args by navArgs<EditCardScreenArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnEdit.isEnabled = false

        binding.newName.addTextChangedListener {
            Log.v("EEEE", it.toString().isNotEmpty().toString())
            binding.btnEdit.isEnabled = it.toString().isNotEmpty()
        }

        binding.btnEdit.setOnClickListener {
            viewModel.userEditCard(
                EditCardRequest(
                    args.cardNumber,
                    binding.newName.text.toString().trim()
                )
            )
        }

        viewModel.ableEditCardButtonLiveData.observe(viewLifecycleOwner, ableEditCardButtonObserver)
        viewModel.errorMessageLiveData.observe(viewLifecycleOwner, errorMessageObserver)
        viewModel.visibilityProgressLiveData.observe(viewLifecycleOwner, visibilityProgressObserver)
        viewModel.successEditCardLiveData.observe(viewLifecycleOwner, successEditCardObserver)
    }

    private val successEditCardObserver = Observer<String> {
        showToast(it)
        findNavController().popBackStack()
    }

    private val ableEditCardButtonObserver = Observer<Boolean> {
        binding.btnEdit.isEnabled = it
    }

    private val errorMessageObserver = Observer<String> {
        showToast(it)
    }

    private val visibilityProgressObserver = Observer<Boolean> {
        binding.progress.visibility = if (it) View.VISIBLE else View.GONE
    }
}