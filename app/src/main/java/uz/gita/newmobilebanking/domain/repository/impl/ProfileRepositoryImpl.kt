package uz.gita.newmobilebanking.domain.repository.impl

import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.ResponseBody
import uz.gita.newmobilebanking.data.local.sharepref.SharePref
import uz.gita.newmobilebanking.data.remote.api.ProfileApi
import uz.gita.newmobilebanking.data.remote.request.profile.ProfileEditRequest
import uz.gita.newmobilebanking.data.remote.response.UniversalResponse
import uz.gita.newmobilebanking.data.remote.response.profile.ProfileInfoResponse
import uz.gita.newmobilebanking.domain.repository.interfaces.ProfileRepository
import uz.gita.newmobilebanking.utils.timber
import uz.gita.newmobilebanking.utils.toRequestData
import java.io.File
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val api: ProfileApi,
    private val gson: Gson,
    private val pref: SharePref
) : ProfileRepository {

    override fun userGetProfileInfo(): Flow<Result<ProfileInfoResponse>> = flow {
        val response = api.profileInfo(pref.accessToken)
        if (response.isSuccessful) {
            emit(Result.success(response.body()!!))
        } else {
            var st = "Error caught in repository"
            response.errorBody()?.let {
                st = gson.fromJson(it.string(), ProfileInfoResponse::class.java).data.toString()
            }
            emit(Result.failure<ProfileInfoResponse>(Throwable(st)))
        }
    }.flowOn(Dispatchers.IO)

    override fun userChangedInfo(request: ProfileEditRequest): Flow<Result<ProfileInfoResponse>> =
        flow {
            val response = api.profileEdit(pref.accessToken, request)
            if (response.isSuccessful) {
                emit(Result.success(response.body()!!))
            } else {
                var st = "Error caught in repository"
                response.errorBody()?.let {
                    st = gson.fromJson(it.string(), ProfileInfoResponse::class.java).data.toString()
                }
                emit(Result.failure<ProfileInfoResponse>(Throwable(st)))
            }
        }.flowOn(Dispatchers.IO)

    override fun uploadAvatar(file: File): Flow<Result<String>> = flow {
        timber("repo 1", "PPPPP")
        val response = api.uploadAvatar(pref.accessToken, file.toRequestData())
        if (response.isSuccessful) {
            timber("repo 2", "PPPPP")
            emit(Result.success(response.body()!!.message))
        } else {
            timber("repo 3","PPPPP")
            var st = "Error caught in repository"
            response.errorBody()?.let {
                st = gson.fromJson(it.string(), UniversalResponse::class.java).message
            }
            emit(Result.failure<String>(Throwable(st)))
        }
    }.flowOn(Dispatchers.IO)

    override fun downloadAvatar(): Flow<Result<ResponseBody>> = flow {
        val response = api.downloadAvatar(pref.accessToken)
        if (response.isSuccessful) {
            emit(Result.success(response.body()!!))
        } else {
            val st = "Error caught in repository"
            emit(Result.failure<ResponseBody>(Throwable(st)))
        }
    }.flowOn(Dispatchers.IO)

}