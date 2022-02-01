package uz.gita.newmobilebanking.domain.usecase.impl.nav_draw

import kotlinx.coroutines.flow.Flow
import uz.gita.newmobilebanking.domain.repository.interfaces.AuthRepository
import uz.gita.newmobilebanking.domain.usecase.interfaces.nav_draw.SecurityScreenUC
import javax.inject.Inject

class SecurityScreenUCImpl @Inject constructor(private val repository: AuthRepository) :
    SecurityScreenUC {
    override fun userLogout(): Flow<Result<String>> = repository.userLogout()
}