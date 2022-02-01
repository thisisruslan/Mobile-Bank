package uz.gita.newmobilebanking.presentation.viewModel.card.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import uz.gita.newmobilebanking.data.remote.request.card.AddCardRequest
import uz.gita.newmobilebanking.presentation.viewModel.card.interfaces.AddCardScreenVM

class TEstVMImpl: ViewModel() , AddCardScreenVM {
    override val ableAddCardButtonLiveData: LiveData<Boolean>
        get() = TODO("Not yet implemented")
    override val visibilityProgressLiveData: LiveData<Boolean>
        get() = TODO("Not yet implemented")
    override val errorMessageLiveData: LiveData<String>
        get() = TODO("Not yet implemented")
    override val successAddCardLiveData: LiveData<String>
        get() = TODO("Not yet implemented")

    override fun userAddCard(request: AddCardRequest) {
        if (request.cardName = "Alisher") {
            successAddCardLiveData.value = "Awa duris keldi"
        }


    }
}