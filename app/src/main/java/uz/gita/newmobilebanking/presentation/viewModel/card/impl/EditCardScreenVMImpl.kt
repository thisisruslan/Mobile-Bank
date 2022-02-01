package uz.gita.newmobilebanking.presentation.viewModel.card.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.newmobilebanking.data.remote.request.card.EditCardRequest
import uz.gita.newmobilebanking.domain.usecase.interfaces.card.EditCardScreenUC
import uz.gita.newmobilebanking.presentation.viewModel.card.interfaces.EditCardScreenVM
import uz.gita.newmobilebanking.utils.isConnected
import javax.inject.Inject

@HiltViewModel
class EditCardScreenVMImpl @Inject constructor(private val useCase: EditCardScreenUC) : ViewModel(),
    EditCardScreenVM {
    override val ableEditCardButtonLiveData = MutableLiveData<Boolean>()
    override val visibilityProgressLiveData = MutableLiveData<Boolean>()
    override val errorMessageLiveData = MutableLiveData<String>()
    override val successEditCardLiveData = MutableLiveData<String>()

    override fun userEditCard(request: EditCardRequest) {
        if (!isConnected()) {
            errorMessageLiveData.value = "Intenet is not available"
            return
        }
        ableEditCardButtonLiveData.value = false
        visibilityProgressLiveData.value = true
        useCase.userEditCard(request).onEach {
            ableEditCardButtonLiveData.value = true
            visibilityProgressLiveData.value = false
            it.onFailure { throwable ->
                errorMessageLiveData.value = throwable.message
            }
            it.onSuccess { message ->
                successEditCardLiveData.value = message
            }
        }.catch {
            ableEditCardButtonLiveData.value = true
            visibilityProgressLiveData.value = false
            errorMessageLiveData.value = "Error caught in ViewModel"
        }.launchIn(viewModelScope)
    }


}