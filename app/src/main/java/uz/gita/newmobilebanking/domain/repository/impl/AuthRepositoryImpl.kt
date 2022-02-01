package uz.gita.newmobilebanking.domain.repository.impl

import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.gita.newmobilebanking.data.local.enums.StartAppEnum
import uz.gita.newmobilebanking.data.local.sharepref.SharePref
import uz.gita.newmobilebanking.data.remote.api.AuthApi
import uz.gita.newmobilebanking.data.remote.request.auth.LoginRequest
import uz.gita.newmobilebanking.data.remote.request.auth.RegisterRequest
import uz.gita.newmobilebanking.data.remote.request.auth.ResetRequest
import uz.gita.newmobilebanking.data.remote.request.auth.VerifyRequest
import uz.gita.newmobilebanking.data.remote.response.UniversalResponse
import uz.gita.newmobilebanking.domain.repository.interfaces.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val pref: SharePref,
    private val api: AuthApi,
    private val gson: Gson
) : AuthRepository {

    //Login
    override fun userLogin(loginRequest: LoginRequest): Flow<Result<String>> = flow {
        val response = api.login(loginRequest)
        if (response.isSuccessful) {
            emit(Result.success(response.body()!!.message))
        } else {
            var str = "Repositorydan error qaydi"
            response.errorBody()?.let {
                str = gson.fromJson(it.string(), UniversalResponse::class.java).message
            }
            emit(Result.failure<String>(Throwable(str)))
        }
    }.flowOn(IO)

    //Register
    override fun userRegister(registerRequest: RegisterRequest): Flow<Result<String>> = flow {
        val response = api.register(registerRequest)
        if (response.isSuccessful) {
            emit(Result.success(response.body()!!.message))
        } else  {
            var str = "Repositorydan error qaydi"
            response.errorBody()?.let {
                str = gson.fromJson(it.string(), UniversalResponse::class.java).message
            }
            emit(Result.failure<String>(Throwable(str)))
        }
    }.flowOn(IO)

    //Verify
    override fun userVerify(verifyRequest: VerifyRequest): Flow<Result<String>> = flow {
        val response = api.verify(verifyRequest)
        if (response.isSuccessful) {
            response.body()?.let {
                pref.accessToken = it.data.accessToken
                pref.refreshToken = it.data.refreshToken
                pref.phoneNumber = verifyRequest.phone
                pref.startScreen = StartAppEnum.MAIN
            }
            emit(Result.success("Successfully entered"))
        } else {
            emit(Result.failure(Throwable("The code is not correct!")))
        }
    }.flowOn(IO)

    //ResetPassword
    override fun userReset(resetRequest: ResetRequest): Flow<Result<String>> {
        TODO("Not yet implemented")
    }

    //Logout
    override fun userLogout(): Flow<Result<String>> = flow {
        val response = api.logout(pref.accessToken)
        if (response.isSuccessful) {
            pref.apply {
                startScreen = StartAppEnum.LOGIN
                isPinCodeConfirmed = "false"
                pinCode = "No pin code"
            }
            emit(Result.success(response.body()!!.message))
        } else {
            var st = "Error caught in Repository"
            response.errorBody()?.let {
                st = gson.fromJson(it.string(), UniversalResponse::class.java).message
            }
            emit(Result.failure<String>(Throwable(st)))
        }
    }.flowOn(IO)


}