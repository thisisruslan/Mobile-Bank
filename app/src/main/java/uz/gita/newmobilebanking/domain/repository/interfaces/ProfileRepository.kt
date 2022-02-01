package uz.gita.newmobilebanking.domain.repository.interfaces

import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody
import uz.gita.newmobilebanking.data.remote.request.profile.ProfileEditRequest
import uz.gita.newmobilebanking.data.remote.response.profile.ProfileInfoResponse
import java.io.File

interface ProfileRepository {
    fun userGetProfileInfo(): Flow<Result<ProfileInfoResponse>>
    fun userChangedInfo(request : ProfileEditRequest): Flow<Result<ProfileInfoResponse>>
    fun uploadAvatar(file: File) : Flow<Result<String>>
    fun downloadAvatar() : Flow<Result<ResponseBody>>


}