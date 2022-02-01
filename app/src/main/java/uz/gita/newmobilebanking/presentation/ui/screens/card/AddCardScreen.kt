package uz.gita.newmobilebanking.presentation.ui.screens.card

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
import uz.gita.newmobilebanking.data.remote.request.card.AddCardRequest
import uz.gita.newmobilebanking.databinding.ScreenAddCardBinding
import uz.gita.newmobilebanking.presentation.viewModel.card.impl.AddCardScreenVMImpl
import uz.gita.newmobilebanking.presentation.viewModel.card.impl.TEstVMImpl
import uz.gita.newmobilebanking.presentation.viewModel.card.interfaces.AddCardScreenVM
import uz.gita.newmobilebanking.utils.showToast

@AndroidEntryPoint
class AddCardScreen : Fragment(R.layout.screen_add_card) {
    private val binding by viewBinding(ScreenAddCardBinding::bind)
    private val viewModel: AddCardScreenVM by viewModels<TEstVMImpl>()

    @SuppressLint("FragmentLiveDataObserve")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        combine(
            binding.etCardName.textChanges().map {
                it.isNotEmpty()
            },
            binding.etExpDate.textChanges().map {
                it.length == 5
            },
            binding.etCardNumber.textChanges().map {
                it.length == 19
            },
            transform = { etCardName, etExpDate, etCardNumber ->

                etCardName && etExpDate && etCardNumber
            }
        ).onEach { binding.btnAdd.isEnabled = it }
            .launchIn(lifecycleScope) // lifecycle-runtime-ktx


        binding.btnAdd.setOnClickListener {
            viewModel.userAddCard(
                AddCardRequest(
                    binding.etCardNumber.unmaskedText.toString().trim(),
                    binding.etExpDate.text.toString().trim(),
                    binding.etCardName.text.toString().trim()
                )
            )
        }

        viewModel.ableAddCardButtonLiveData.observe(viewLifecycleOwner,
            {
                binding.btnAdd.isEnabled = it
            })

        viewModel.visibilityProgressLiveData.observe(viewLifecycleOwner, visibilityProgressObserver)
        viewModel.successAddCardLiveData.observe(this, { successAddCardObserver })
        viewModel.errorMessageLiveData.observe(viewLifecycleOwner, errorMessageObserver)
    }

    private val successAddCardObserver = Observer<String> {
        showToast(it)
        findNavController().navigate(
            AddCardScreenDirections.actionAddCardScreenToVerifyAddCardScreen(
                binding.etCardNumber.unmaskedText.toString().trim()
            )
        )
    }

    private val errorMessageObserver = Observer<String> {
        showToast(it)
    }

    private val visibilityProgressObserver = Observer<Boolean> {
        binding.progress.visibility = if (it) View.VISIBLE else View.GONE
    }
}