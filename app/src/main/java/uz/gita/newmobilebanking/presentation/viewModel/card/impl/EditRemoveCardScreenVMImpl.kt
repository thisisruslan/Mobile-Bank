package uz.gita.newmobilebanking.presentation.viewModel.card.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.newmobilebanking.data.remote.request.card.DeleteCardRequest
import uz.gita.newmobilebanking.domain.usecase.interfaces.card.EditRemoveCardScreenUC
import uz.gita.newmobilebanking.presentation.viewModel.card.interfaces.EditRemoveCardScreenVM
import uz.gita.newmobilebanking.utils.isConnected
import javax.inject.Inject

@HiltViewModel
class EditRemoveCardScreenVMImpl @Inject constructor(private val useCase: EditRemoveCardScreenUC) :
    ViewModel(), EditRemoveCardScreenVM {

    override val ableEditCardButtonLiveData = MutableLiveData<Boolean>()
    override val visibilityProgressLiveData = MutableLiveData<Boolean>()
    override val errorDeleteCardMessageLiveData = MutableLiveData<String>()
    override val successDeleteCardLiveData = MutableLiveData<String>()

    override fun userDeleteCard(request: DeleteCardRequest) {
        if (!isConnected()) {
            errorDeleteCardMessageLiveData.value = "Internet is not working!"
            return
        }
        ableEditCardButtonLiveData.value = false
        visibilityProgressLiveData.value = true
        useCase.userDeleteCard(request).onEach {
            ableEditCardButtonLiveData.value = true
            visibilityProgressLiveData.value = false
            it.onSuccess { message ->
                successDeleteCardLiveData.value = message
            }
            it.onFailure { throwable ->
                errorDeleteCardMessageLiveData.value = throwable.message
            }
        }.catch {
            ableEditCardButtonLiveData.value = true
            visibilityProgressLiveData.value = false
            errorDeleteCardMessageLiveData.value = "Error caught in View Model"
        }.launchIn(viewModelScope)
    }


    override val gotoEditCardScreenLiveData = MutableLiveData<String>()
    override fun gotoEditCardScreen(cardNumber: String) {
        gotoEditCardScreenLiveData.value = cardNumber
    }

}