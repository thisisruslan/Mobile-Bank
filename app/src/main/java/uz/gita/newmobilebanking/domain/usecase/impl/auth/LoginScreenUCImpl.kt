package uz.gita.newmobilebanking.domain.usecase.impl.auth

import kotlinx.coroutines.flow.Flow
import uz.gita.newmobilebanking.data.remote.request.auth.LoginRequest
import uz.gita.newmobilebanking.domain.repository.interfaces.AuthRepository
import uz.gita.newmobilebanking.domain.usecase.interfaces.auth.LoginScreenUC
import javax.inject.Inject

class LoginScreenUCImpl @Inject constructor(private val authRepository: AuthRepository) : LoginScreenUC {

    override fun userLogin(request: LoginRequest): Flow<Result<String>> = authRepository.userLogin(request)
}

