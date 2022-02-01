package uz.gita.newmobilebanking.presentation.viewModel.pages.profile

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import okhttp3.ResponseBody
import uz.gita.newmobilebanking.data.remote.request.profile.ProfileEditRequest
import uz.gita.newmobilebanking.data.remote.response.profile.ProfileInfoResponse
import java.io.File

interface ProfilePageVM : LifecycleOwner {

    val visibilityProgressLiveData: LiveData<Boolean>
    val errorMessageLiveData: LiveData<String>

    //upload avatar
    val successUploadAvatarLiveData: LiveData<String>
    fun userUploadAvatar(file: File)

    //download avatar
    val successDownloadAvatarLiveData: LiveData<ResponseBody>
    fun userDownloadAvatar()

    //get info
    val successGetInfoLiveData: LiveData<ProfileInfoResponse>
    fun userGetProfileInfo()

    //apply changes
    val successChangeInfoLiveData: LiveData<ProfileInfoResponse>
    fun userChangeProfileInfo(data: ProfileEditRequest)


}