package uz.gita.newmobilebanking.presentation.viewModel.auth.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.newmobilebanking.data.remote.request.auth.LoginRequest
import uz.gita.newmobilebanking.domain.usecase.interfaces.auth.LoginScreenUC
import uz.gita.newmobilebanking.presentation.viewModel.auth.interfaces.LoginVM
import uz.gita.newmobilebanking.utils.isConnected
import javax.inject.Inject

@HiltViewModel
class LoginVMImpl @Inject constructor(private val loginScreenUC: LoginScreenUC) : ViewModel(),
    LoginVM {

    override val ableButtonsLiveData = MutableLiveData<Boolean>()
    override val visibilityProgressLiveData = MutableLiveData<Boolean>()
    override val errorMessageLiveData = MutableLiveData<String>()
    override val openVerifyScreenLiveData = MutableLiveData<String>()
    override val openRegisterScreenLiveData =  MutableLiveData<Unit>()

    override fun openRegisterScreen() {
        openRegisterScreenLiveData.value = Unit
    }


    override fun userLogin(request: LoginRequest) {
        if (!isConnected()) {
            errorMessageLiveData.value = "Network is not working!"
            return
        }
        ableButtonsLiveData.value = false
        visibilityProgressLiveData.value = true
        loginScreenUC.userLogin(request).onEach { it ->
            ableButtonsLiveData.value = true
            visibilityProgressLiveData.value = false
            it.onSuccess { message ->
                openVerifyScreenLiveData.value = message
            }
            it.onFailure { throwable ->
                errorMessageLiveData.value = throwable.message
            }
        }.catch {
            ableButtonsLiveData.value = true
            visibilityProgressLiveData.value = false
            errorMessageLiveData.value = "Error caught in ViewModel"
        }.launchIn(viewModelScope)
    }



}