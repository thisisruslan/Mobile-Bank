package uz.gita.newmobilebanking.presentation.viewModel.auth.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.newmobilebanking.data.remote.request.auth.VerifyRequest
import uz.gita.newmobilebanking.domain.usecase.interfaces.auth.VerifyScreenUC
import uz.gita.newmobilebanking.presentation.viewModel.auth.interfaces.VerifyVM
import uz.gita.newmobilebanking.utils.isConnected
import javax.inject.Inject

@HiltViewModel
class VerifyVMImpl @Inject constructor(private val verifyScreenUC: VerifyScreenUC) :
    ViewModel(), VerifyVM {

    override val ableVerifyButtonLiveData = MutableLiveData<Boolean>()
    override val visibilityProgressLiveData = MutableLiveData<Boolean>()
    override val errorMessageLiveData = MutableLiveData<String>()
    override val openSetPinScreenLiveData = MutableLiveData<String>()

    override fun userVerify(request: VerifyRequest) {
        if (!isConnected()) {
            errorMessageLiveData.value = "Network is not working!"
            return
        }
        ableVerifyButtonLiveData.value = false
        visibilityProgressLiveData.value = true 
        verifyScreenUC.userVerify(request).onEach { it ->
            ableVerifyButtonLiveData.value = true
            visibilityProgressLiveData.value = false
            it.onSuccess { message ->
                openSetPinScreenLiveData.value = message
            }
            it.onFailure { throwable ->
                errorMessageLiveData.value = throwable.message
            }
        }.catch {
            ableVerifyButtonLiveData.value = true
            visibilityProgressLiveData.value = false
            errorMessageLiveData.value = "Error caught in ViewModel"
        }.launchIn(viewModelScope)
    }

}