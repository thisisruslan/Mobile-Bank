package uz.gita.newmobilebanking.domain.usecase.interfaces.nav_draw

import kotlinx.coroutines.flow.Flow

interface SecurityScreenUC {
    fun userLogout() : Flow<Result<String>>
}