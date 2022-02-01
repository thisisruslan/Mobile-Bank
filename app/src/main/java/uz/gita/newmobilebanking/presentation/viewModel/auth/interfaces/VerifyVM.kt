package uz.gita.newmobilebanking.presentation.viewModel.auth.interfaces

import androidx.lifecycle.LiveData
import uz.gita.newmobilebanking.data.remote.request.auth.VerifyRequest

interface VerifyVM {
    val ableVerifyButtonLiveData: LiveData<Boolean>
    val visibilityProgressLiveData: LiveData<Boolean>

    val errorMessageLiveData: LiveData<String>
    val openSetPinScreenLiveData: LiveData<String>
    fun userVerify(request: VerifyRequest)

}