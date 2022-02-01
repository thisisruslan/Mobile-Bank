package uz.gita.newmobilebanking.presentation.viewModel.auth.interfaces

import androidx.lifecycle.LiveData
import uz.gita.newmobilebanking.data.remote.request.auth.LoginRequest

interface LoginVM {
    val ableButtonsLiveData : LiveData<Boolean>
    val visibilityProgressLiveData : LiveData<Boolean>

    val errorMessageLiveData : LiveData<String>
    val openVerifyScreenLiveData : LiveData<String>
    fun userLogin(request: LoginRequest)

    val openRegisterScreenLiveData : LiveData<Unit>
    fun openRegisterScreen()
}