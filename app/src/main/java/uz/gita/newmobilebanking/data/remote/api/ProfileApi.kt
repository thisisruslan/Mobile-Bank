package uz.gita.newmobilebanking.data.remote.api

import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*
import uz.gita.newmobilebanking.data.remote.request.profile.ProfileEditRequest
import uz.gita.newmobilebanking.data.remote.response.UniversalResponse
import uz.gita.newmobilebanking.data.remote.response.profile.ProfileInfoResponse

interface ProfileApi {

    @GET("profile/info")
    suspend fun profileInfo(@Header("token") token:String) : Response<ProfileInfoResponse>

    @PUT("profile")
    suspend fun profileEdit(@Header("token") token: String, @Body data: ProfileEditRequest): Response<ProfileInfoResponse>

    @POST("profile/avatar")
    suspend fun uploadAvatar(@Header("token") token: String,@Part avatar: MultipartBody.Part): Response<UniversalResponse>

    @GET("profile/avatar")
    suspend fun downloadAvatar(@Header("token") token: String): Response<ResponseBody>


    /*
    @Multipart
    @POST("v1/profile/avatar")
    suspend fun uploadAvatar(@Header("token") token: String,@Part avatar: MultipartBody.Part): Response<UploadAvatarResponse>

    @GET("v1/profile/avatar")
    suspend fun downloadAvatar(@Header("token") token: String): Response<ResponseBody>

    @GET("v1/profile/info")
    suspend fun profileInfo(@Header("token") token: String): Response<ProfileInfoResponse>

    @PUT("v1/profile")
    suspend fun profileEdit(@Header("token") token: String, @Body data: ProfileEditRequest): Response<ProfileInfoResponse>

     */
}