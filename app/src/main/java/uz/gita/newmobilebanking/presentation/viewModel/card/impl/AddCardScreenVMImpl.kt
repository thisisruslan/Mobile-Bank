package uz.gita.newmobilebanking.presentation.viewModel.card.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.newmobilebanking.data.remote.request.card.AddCardRequest
import uz.gita.newmobilebanking.domain.usecase.interfaces.card.AddCardScreenUC
import uz.gita.newmobilebanking.presentation.viewModel.card.interfaces.AddCardScreenVM
import uz.gita.newmobilebanking.utils.isConnected
import javax.inject.Inject

@HiltViewModel
class AddCardScreenVMImpl @Inject constructor(private val useCase: AddCardScreenUC) : ViewModel(),
    AddCardScreenVM {

    override val ableAddCardButtonLiveData = MutableLiveData<Boolean>()
    override val visibilityProgressLiveData = MutableLiveData<Boolean>()
    override val errorMessageLiveData = MutableLiveData<String>()
    override val successAddCardLiveData = MutableLiveData<String>()

    override fun userAddCard(request: AddCardRequest) {
        if (!isConnected()) {
            errorMessageLiveData.value = "Internet is not working!"
            return
        }
        ableAddCardButtonLiveData.value = false
        visibilityProgressLiveData.value = true
        useCase.userAddCard(request).onEach {
            ableAddCardButtonLiveData.value = true
            visibilityProgressLiveData.value = false
            it.onSuccess { message ->
                successAddCardLiveData.value = message
            }
            it.onFailure { throwable ->
                errorMessageLiveData.value = throwable.message
            }
        }.catch {
            ableAddCardButtonLiveData.value = true
            visibilityProgressLiveData.value = false
            errorMessageLiveData.value = "Error caught in View Model"
        }
    }
}