package uz.gita.newmobilebanking.data.remote.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import uz.gita.newmobilebanking.data.remote.request.auth.LoginRequest
import uz.gita.newmobilebanking.data.remote.request.auth.RegisterRequest
import uz.gita.newmobilebanking.data.remote.request.auth.VerifyRequest
import uz.gita.newmobilebanking.data.remote.response.UniversalResponse
import uz.gita.newmobilebanking.data.remote.response.auth.VerifyResponse

interface AuthApi {
    @POST("auth/login")
    suspend fun login(@Body data: LoginRequest): Response<UniversalResponse>

    @POST("auth/register")
    suspend fun register(@Body data: RegisterRequest): Response<UniversalResponse>

    @POST("auth/verify")
    suspend fun verify(@Body data: VerifyRequest): Response<VerifyResponse>

     @POST("auth/logout")
    suspend fun logout(@Header("token") data: String): Response<UniversalResponse>



//    @POST("auth/reset")
//    suspend fun resetPassword(@Body data: ResetRequest): Response<UniversalResponse>
}