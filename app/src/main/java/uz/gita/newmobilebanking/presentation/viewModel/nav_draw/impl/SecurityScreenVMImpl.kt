package uz.gita.newmobilebanking.presentation.viewModel.nav_draw.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.newmobilebanking.domain.usecase.interfaces.nav_draw.SecurityScreenUC
import uz.gita.newmobilebanking.presentation.viewModel.nav_draw.interfaces.SecurityScreenVM
import uz.gita.newmobilebanking.utils.isConnected
import javax.inject.Inject

@HiltViewModel
class SecurityScreenVMImpl @Inject constructor(private val useCase: SecurityScreenUC) : ViewModel(),
    SecurityScreenVM {
    override val visibilityProgressLiveData = MutableLiveData<Boolean>()
    override val errorLiveData = MutableLiveData<String>()
    override val clickableLogoutBtnLiveData = MutableLiveData<Boolean>()
    override val logoutSuccessLiveData = MutableLiveData<String>()

    override fun userLogout() {
        if (!isConnected()) {
            errorLiveData.value = "Internet is not available"
            return
        }
        clickableLogoutBtnLiveData.value = false
        visibilityProgressLiveData.value = true
        useCase.userLogout().onEach {
            clickableLogoutBtnLiveData.value = true
            visibilityProgressLiveData.value = false
            it.onSuccess { message ->
                logoutSuccessLiveData.value = message
            }
            it.onFailure { throwable ->
                errorLiveData.value = throwable.message
            }
        }.catch {
            visibilityProgressLiveData.value = false
            errorLiveData.value = "Error caught in viewModel"
        }.launchIn(viewModelScope)
    }
}