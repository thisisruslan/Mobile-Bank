package uz.gita.newmobilebanking.domain.usecase.interfaces.auth

import kotlinx.coroutines.flow.Flow
import uz.gita.newmobilebanking.data.remote.request.auth.VerifyRequest

interface VerifyScreenUC {
    fun userVerify(request: VerifyRequest) : Flow<Result<String>>
}