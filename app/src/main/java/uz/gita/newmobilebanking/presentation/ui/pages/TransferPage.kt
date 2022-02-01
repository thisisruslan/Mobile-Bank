package uz.gita.newmobilebanking.presentation.ui.pages

import android.os.Bundle
import android.view.View
import androidx.core.text.isDigitsOnly
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import ru.ldralighieri.corbind.widget.textChanges
import uz.gita.newmobilebanking.R
import uz.gita.newmobilebanking.data.remote.request.transfer.SendMoneyRequest
import uz.gita.newmobilebanking.data.remote.response.transfer.SendMoneyResponse
import uz.gita.newmobilebanking.databinding.PageTransferBinding
import uz.gita.newmobilebanking.presentation.ui.dialogs.TransferInfoDialog
import uz.gita.newmobilebanking.presentation.viewModel.pages.transfer.TransferPageVM
import uz.gita.newmobilebanking.presentation.viewModel.pages.transfer.TransferPageVMImpl
import uz.gita.newmobilebanking.utils.showToast
import javax.inject.Inject

@AndroidEntryPoint
class TransferPage @Inject constructor() : Fragment(R.layout.page_transfer) {
    private val viewModel: TransferPageVM by viewModels<TransferPageVMImpl>()
    private val binding by viewBinding(PageTransferBinding::bind)
    private val infoDialog = TransferInfoDialog()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSend.isEnabled = false

        combine(
            binding.senderId.textChanges().map {
                it.isDigitsOnly()
            },
            binding.receiverPan.textChanges().map {
//                if (it.startsWith(" ") || it.endsWith(" ")) {
//                    it.trim()
//                    binding.confirmPassword.setText(it)
//                    false
//                } else
                it.isDigitsOnly() && it.length == 16
            },
            binding.transferAmount.textChanges().map {
                it.isDigitsOnly() && it.isNotEmpty()
            },
            transform = { senderId, receiverPan, transferAmount ->
                senderId && receiverPan && transferAmount
            }
        ).onEach { binding.btnSend.isEnabled = it }
            .launchIn(lifecycleScope) // lifecycle-runtime-ktx

        binding.btnSend.setOnClickListener {
            viewModel.userSendMoney(
                SendMoneyRequest(
                    binding.senderId.text.toString().trim().toInt(),
                    binding.receiverPan.text.toString().trim(),
                    binding.transferAmount.text.toString().trim().toLong()

                )
            )
        }
        infoDialog.setDialogListener {
            binding.senderId.text?.clear()
            binding.receiverPan.text?.clear()
            binding.transferAmount.text?.clear()
            infoDialog.dismiss()
        }

        viewModel.ableSendButtonLiveData.observe(viewLifecycleOwner, ableSendButtonObserver)
        viewModel.errorMessageLiveData.observe(viewLifecycleOwner, errorMessageObserver)
        viewModel.visibilityProgressLiveData.observe(viewLifecycleOwner, visibilityProgressObserver)
        viewModel.openInfoTransferDialogLiveData.observe(
            viewLifecycleOwner,
            openInfoTransferDialogObserver
        )
    }

    private val errorMessageObserver = Observer<String> {
        showToast(it)
    }

    private val openInfoTransferDialogObserver = Observer<SendMoneyResponse> {
        val bundle = Bundle()
        bundle.putParcelable("data", it.data)
        infoDialog.arguments = bundle
        infoDialog.isCancelable = false
        infoDialog.show(childFragmentManager, "edit")

    }


    private val ableSendButtonObserver = Observer<Boolean> {
        binding.btnSend.isEnabled = it
    }


    private val visibilityProgressObserver = Observer<Boolean> {
        if (it) binding.progress.show()
        else binding.progress.hide()
    }
}