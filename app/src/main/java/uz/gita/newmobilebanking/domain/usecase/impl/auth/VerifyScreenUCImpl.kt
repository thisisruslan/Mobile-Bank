package uz.gita.newmobilebanking.domain.usecase.impl.auth

import kotlinx.coroutines.flow.Flow
import uz.gita.newmobilebanking.data.remote.request.auth.VerifyRequest
import uz.gita.newmobilebanking.domain.repository.interfaces.AuthRepository
import uz.gita.newmobilebanking.domain.usecase.interfaces.auth.VerifyScreenUC
import javax.inject.Inject

class VerifyScreenUCImpl @Inject constructor(private val authRepository: AuthRepository) :
    VerifyScreenUC {

    override fun userVerify(request: VerifyRequest): Flow<Result<String>> =
        authRepository.userVerify(request)

}