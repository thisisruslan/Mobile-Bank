package uz.gita.newmobilebanking.presentation.viewModel.card.interfaces

import androidx.lifecycle.LiveData
import uz.gita.newmobilebanking.data.remote.request.card.VerifyAddCardRequest
import uz.gita.newmobilebanking.data.remote.response.card.VerifyAddCardResponse

interface VerifyAddCardScreenVM {
    val ableVerifyButtonLiveData: LiveData<Boolean>
    val visibilityProgressLiveData: LiveData<Boolean>

    val errorMessageLiveData: LiveData<String>
    val successVerifyLiveData: LiveData<VerifyAddCardResponse>
    fun userVerify(request: VerifyAddCardRequest)


}