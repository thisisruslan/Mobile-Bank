package uz.gita.newmobilebanking.domain.usecase.interfaces.auth

import kotlinx.coroutines.flow.Flow
import uz.gita.newmobilebanking.data.remote.request.auth.LoginRequest

interface LoginScreenUC {
    fun userLogin(request: LoginRequest): Flow<Result<String>>
}