package uz.gita.newmobilebanking.presentation.viewModel.pages.transfer

import androidx.lifecycle.LiveData
import uz.gita.newmobilebanking.data.remote.request.transfer.SendMoneyRequest
import uz.gita.newmobilebanking.data.remote.response.transfer.SendMoneyResponse

interface TransferPageVM {
    val ableSendButtonLiveData: LiveData<Boolean>
    val visibilityProgressLiveData: LiveData<Boolean>

    val errorMessageLiveData: LiveData<String>
    val openInfoTransferDialogLiveData: LiveData<SendMoneyResponse>

    fun userSendMoney(request: SendMoneyRequest)
}