package uz.gita.newmobilebanking.presentation.viewModel.card.interfaces

import androidx.lifecycle.LiveData
import uz.gita.newmobilebanking.data.remote.request.card.AddCardRequest

interface AddCardScreenVM {
    val ableAddCardButtonLiveData: LiveData<Boolean>
    val visibilityProgressLiveData: LiveData<Boolean>

    val errorMessageLiveData: LiveData<String>
    val successAddCardLiveData: LiveData<String>
    fun userAddCard(request: AddCardRequest)
}