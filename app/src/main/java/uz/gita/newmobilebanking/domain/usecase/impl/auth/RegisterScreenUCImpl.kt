package uz.gita.newmobilebanking.domain.usecase.impl.auth

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.gita.newmobilebanking.data.remote.request.auth.RegisterRequest
import uz.gita.newmobilebanking.domain.repository.interfaces.AuthRepository
import uz.gita.newmobilebanking.domain.usecase.interfaces.auth.RegisterScreenUC
import javax.inject.Inject

class RegisterScreenUCImpl @Inject constructor(private val authRepository: AuthRepository) :
    RegisterScreenUC {
    val scope = CoroutineScope(Dispatchers.Default).coroutineContext

    override fun userRegister(request: RegisterRequest): Flow<Result<String>> = flow {
        request.firstName + "new name"
        emit(Result.success(request.firstName))
    }.flowOn(scope)


}

