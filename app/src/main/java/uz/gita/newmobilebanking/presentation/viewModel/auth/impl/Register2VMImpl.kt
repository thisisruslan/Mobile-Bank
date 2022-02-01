package uz.gita.newmobilebanking.presentation.viewModel.auth.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.newmobilebanking.data.remote.request.auth.RegisterRequest
import uz.gita.newmobilebanking.domain.usecase.interfaces.auth.RegisterScreenUC
import uz.gita.newmobilebanking.presentation.viewModel.auth.interfaces.Register2VM
import uz.gita.newmobilebanking.utils.isConnected
import javax.inject.Inject

@HiltViewModel
class Register2VMImpl @Inject constructor(private val registerScreenUC: RegisterScreenUC) :
    ViewModel(), Register2VM {

    override val ableRegisterButtonLiveData = MutableLiveData<Boolean>()
    override val visibilityProgressLiveData = MutableLiveData<Boolean>()
    override val errorMessageLiveData = MutableLiveData<String>()
    override val openVerifyScreenLiveData = MutableLiveData<String>()

    override fun userRegister(request: RegisterRequest) {
        if (!isConnected()) {
            errorMessageLiveData.value = "Network is not working!"
            return
        }
        ableRegisterButtonLiveData.value = false
        visibilityProgressLiveData.value = true
        registerScreenUC.userRegister(request).onEach { it ->
            ableRegisterButtonLiveData.value = true
            visibilityProgressLiveData.value = false
            it.onSuccess { message ->
                openVerifyScreenLiveData.value = message
            }
            it.onFailure { throwable ->
                errorMessageLiveData.value = throwable.message
            }
        }.catch {
            ableRegisterButtonLiveData.value = true
            visibilityProgressLiveData.value = false
            errorMessageLiveData.value = "Error caught in ViewModel"
        }
    }
}