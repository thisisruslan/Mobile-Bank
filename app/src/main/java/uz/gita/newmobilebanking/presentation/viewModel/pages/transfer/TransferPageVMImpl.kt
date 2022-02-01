package uz.gita.newmobilebanking.presentation.viewModel.pages.transfer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.onEach
import uz.gita.newmobilebanking.data.remote.request.transfer.SendMoneyRequest
import uz.gita.newmobilebanking.data.remote.response.transfer.SendMoneyResponse
import uz.gita.newmobilebanking.domain.usecase.interfaces.transfer.TransferPageUC
import uz.gita.newmobilebanking.utils.isConnected
import javax.inject.Inject

@HiltViewModel
class TransferPageVMImpl @Inject constructor(private val useCase: TransferPageUC) :
    TransferPageVM, ViewModel() {
    override val ableSendButtonLiveData = MutableLiveData<Boolean>()
    override val visibilityProgressLiveData = MutableLiveData<Boolean>()
    override val errorMessageLiveData = MutableLiveData<String>()
    override val openInfoTransferDialogLiveData = MutableLiveData<SendMoneyResponse>()

    override fun userSendMoney(request: SendMoneyRequest) {
        if (!isConnected()) {
            errorMessageLiveData.value = "Internet is not working!"
            return
        }
        ableSendButtonLiveData.value = false
        visibilityProgressLiveData.value = true
        useCase.sendMoney(request).onEach {
            ableSendButtonLiveData.value = true
            visibilityProgressLiveData.value = false
            it.onSuccess { data ->
                openInfoTransferDialogLiveData.value = data
            }
            it.onFailure { throwable ->
                errorMessageLiveData.value = throwable.message
            }
        }.catch {
            ableSendButtonLiveData.value = true
            visibilityProgressLiveData.value = false
            errorMessageLiveData.value = "Error caught in View Model"
        }.launchIn(viewModelScope)
    }

}