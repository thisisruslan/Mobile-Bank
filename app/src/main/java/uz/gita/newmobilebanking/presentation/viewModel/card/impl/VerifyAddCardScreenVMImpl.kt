package uz.gita.newmobilebanking.presentation.viewModel.card.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.newmobilebanking.data.remote.request.card.VerifyAddCardRequest
import uz.gita.newmobilebanking.data.remote.response.card.VerifyAddCardResponse
import uz.gita.newmobilebanking.domain.usecase.interfaces.card.VerifyAddCardScreenUC
import uz.gita.newmobilebanking.presentation.viewModel.card.interfaces.VerifyAddCardScreenVM
import uz.gita.newmobilebanking.utils.isConnected
import javax.inject.Inject

@HiltViewModel
class VerifyAddCardScreenVMImpl @Inject constructor(private val useCase: VerifyAddCardScreenUC) :
    ViewModel(),
    VerifyAddCardScreenVM {
    override val ableVerifyButtonLiveData = MutableLiveData<Boolean>()
    override val visibilityProgressLiveData = MutableLiveData<Boolean>()
    override val errorMessageLiveData = MutableLiveData<String>()
    override val successVerifyLiveData = MutableLiveData<VerifyAddCardResponse>()

    override fun userVerify(request: VerifyAddCardRequest) {
        if (!isConnected()) {
            errorMessageLiveData.value = "Internet is not available!"
            return
        }

        ableVerifyButtonLiveData.value = false
        visibilityProgressLiveData.value = true
        useCase.userVerifyAddCardScreen(request).onEach {
            ableVerifyButtonLiveData.value = true
            visibilityProgressLiveData.value = false
            it.onFailure { throwable ->
                errorMessageLiveData.value = throwable.message
            }
            it.onSuccess { cardData ->
                successVerifyLiveData.value = cardData
            }
        }.catch {
            ableVerifyButtonLiveData.value = true
            visibilityProgressLiveData.value = false
            errorMessageLiveData.value = "Error caught in viewModel"
        }.launchIn(viewModelScope)
    }


}