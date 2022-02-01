package uz.gita.newmobilebanking.domain.usecase.interfaces.auth

import kotlinx.coroutines.flow.Flow
import uz.gita.newmobilebanking.data.remote.request.auth.RegisterRequest

interface RegisterScreenUC {
    fun userRegister(request: RegisterRequest): Flow<Result<String>>
}