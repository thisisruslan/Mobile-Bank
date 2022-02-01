package uz.gita.newmobilebanking.presentation.viewModel.pages.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import okhttp3.ResponseBody
import uz.gita.newmobilebanking.data.remote.request.profile.ProfileEditRequest
import uz.gita.newmobilebanking.data.remote.response.profile.ProfileInfoResponse
import uz.gita.newmobilebanking.domain.usecase.interfaces.profile.ProfilePageUC
import uz.gita.newmobilebanking.utils.isConnected
import uz.gita.newmobilebanking.utils.timber
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ProfilePageVMImpl @Inject constructor(private val useCase: ProfilePageUC) : ViewModel(),
    ProfilePageVM {
    override val visibilityProgressLiveData = MutableLiveData<Boolean>()
    override val errorMessageLiveData = MutableLiveData<String>()
    override val successUploadAvatarLiveData = MutableLiveData<String>()

    override fun userUploadAvatar(file: File) {
        if (!isConnected()) {
            errorMessageLiveData.value = "Internet is not available"
        }
        visibilityProgressLiveData.value = true
        timber("NOOO", "PPPPP")
        useCase.uploadAvatar(file).onEach {
            timber("YESSS", "PPPPP")
            visibilityProgressLiveData.value = false
            it.onSuccess { message ->
                successUploadAvatarLiveData.value = message
            }
            it.onFailure { th ->
                errorMessageLiveData.value = th.message
            }
        }.catch {
            errorMessageLiveData.value = "Error caught in viewModel"
            visibilityProgressLiveData.value = false
        }.launchIn(viewModelScope)
    }

    override val successDownloadAvatarLiveData = MutableLiveData<ResponseBody>()
    override fun userDownloadAvatar() {
        if (!isConnected()) {
            errorMessageLiveData.value = "Internet is not available"
        }
        visibilityProgressLiveData.value = true
        useCase.downloadAvatar().onEach {
            visibilityProgressLiveData.value = false
            it.onSuccess { responseBody ->
                successDownloadAvatarLiveData.value = responseBody
                responseBody.close()
            }
            it.onFailure { throwable ->
                errorMessageLiveData.value = throwable.message
            }
        }.catch {
            errorMessageLiveData.value = "Error caugth in ViewNModel"
            visibilityProgressLiveData.value = false
        }.launchIn(viewModelScope)

    }

    override val successGetInfoLiveData = MutableLiveData<ProfileInfoResponse>()
    override fun userGetProfileInfo() {
        if (!isConnected()) {
            errorMessageLiveData.value = "Internet is not available"
        }
        visibilityProgressLiveData.value = true
        useCase.getProfileInfo().onEach {
            visibilityProgressLiveData.value = false
            it.onSuccess { data ->
                successGetInfoLiveData.value = data
            }
            it.onFailure { th ->
                errorMessageLiveData.value = th.message
            }
        }.catch {
            errorMessageLiveData.value = "Error caught in viewModel"
            visibilityProgressLiveData.value = false
        }.launchIn(viewModelScope)
    }

    override val successChangeInfoLiveData = MutableLiveData<ProfileInfoResponse>()
    override fun userChangeProfileInfo(data: ProfileEditRequest) {
        if (!isConnected()) {
            errorMessageLiveData.value = "Internet is not available"
        }
        visibilityProgressLiveData.value = true
        useCase.userChangedInfo(data).onEach {
            visibilityProgressLiveData.value = false
            it.onSuccess { data ->
                successChangeInfoLiveData.value = data
            }
            it.onFailure { th ->
                errorMessageLiveData.value = th.message
            }
        }.catch {
            errorMessageLiveData.value = "Error caught in viewModel"
            visibilityProgressLiveData.value = false
        }.launchIn(viewModelScope)
    }
}