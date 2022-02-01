package uz.gita.newmobilebanking.presentation.viewModel.card.interfaces

import androidx.lifecycle.LiveData
import uz.gita.newmobilebanking.data.remote.request.card.DeleteCardRequest

interface EditRemoveCardScreenVM {
    val ableEditCardButtonLiveData: LiveData<Boolean>
    val visibilityProgressLiveData: LiveData<Boolean>

    val errorDeleteCardMessageLiveData: LiveData<String>
    val successDeleteCardLiveData: LiveData<String>
    fun userDeleteCard(request: DeleteCardRequest)

    val gotoEditCardScreenLiveData: LiveData<String>
    fun gotoEditCardScreen(cardNumber: String)

}