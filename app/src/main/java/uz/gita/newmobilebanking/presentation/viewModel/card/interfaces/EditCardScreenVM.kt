package uz.gita.newmobilebanking.presentation.viewModel.card.interfaces

import androidx.lifecycle.LiveData
import uz.gita.newmobilebanking.data.remote.request.card.EditCardRequest

interface EditCardScreenVM {
    val ableEditCardButtonLiveData: LiveData<Boolean>
    val visibilityProgressLiveData: LiveData<Boolean>

    val errorMessageLiveData: LiveData<String>
    val successEditCardLiveData: LiveData<String>

    fun userEditCard(request: EditCardRequest)
}