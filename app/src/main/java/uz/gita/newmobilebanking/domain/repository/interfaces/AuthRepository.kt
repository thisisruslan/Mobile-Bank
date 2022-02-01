package uz.gita.newmobilebanking.domain.repository.interfaces

import kotlinx.coroutines.flow.Flow
import uz.gita.newmobilebanking.data.remote.request.auth.LoginRequest
import uz.gita.newmobilebanking.data.remote.request.auth.RegisterRequest
import uz.gita.newmobilebanking.data.remote.request.auth.ResetRequest
import uz.gita.newmobilebanking.data.remote.request.auth.VerifyRequest

interface AuthRepository {
    fun userLogin(loginRequest: LoginRequest): Flow<Result<String>>
    fun userRegister(registerRequest: RegisterRequest): Flow<Result<String>>
    fun userVerify(verifyRequest: VerifyRequest): Flow<Result<String>>
    fun userReset(resetRequest: ResetRequest): Flow<Result<String>>
    fun userLogout(): Flow<Result<String>>
}