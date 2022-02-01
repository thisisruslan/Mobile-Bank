package uz.gita.newmobilebanking.presentation.viewModel.auth.interfaces

import androidx.lifecycle.LiveData
import uz.gita.newmobilebanking.data.remote.request.auth.RegisterRequest

interface Register2VM {
    val ableRegisterButtonLiveData: LiveData<Boolean>
    val visibilityProgressLiveData: LiveData<Boolean>

    val errorMessageLiveData: LiveData<String>
    val openVerifyScreenLiveData: LiveData<String>
    fun userRegister(request: RegisterRequest)
}