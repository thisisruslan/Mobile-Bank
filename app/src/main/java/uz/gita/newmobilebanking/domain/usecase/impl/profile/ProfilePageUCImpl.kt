package uz.gita.newmobilebanking.domain.usecase.impl.profile

import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody
import uz.gita.newmobilebanking.data.remote.request.profile.ProfileEditRequest
import uz.gita.newmobilebanking.data.remote.response.profile.ProfileInfoResponse
import uz.gita.newmobilebanking.domain.repository.interfaces.ProfileRepository
import uz.gita.newmobilebanking.domain.usecase.interfaces.profile.ProfilePageUC
import java.io.File
import javax.inject.Inject

class ProfilePageUCImpl @Inject constructor(private val repository: ProfileRepository) :
    ProfilePageUC {
    override fun getProfileInfo(): Flow<Result<ProfileInfoResponse>> =
        repository.userGetProfileInfo()

    override fun userChangedInfo(request: ProfileEditRequest): Flow<Result<ProfileInfoResponse>> =
        repository.userChangedInfo(request)

    override fun uploadAvatar(file: File): Flow<Result<String>> = repository.uploadAvatar(file)

    override fun downloadAvatar(): Flow<Result<ResponseBody>> = repository.downloadAvatar()


}