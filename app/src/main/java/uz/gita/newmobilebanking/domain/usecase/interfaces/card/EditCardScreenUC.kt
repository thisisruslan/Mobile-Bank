package uz.gita.newmobilebanking.domain.usecase.interfaces.card

import kotlinx.coroutines.flow.Flow
import uz.gita.newmobilebanking.data.remote.request.card.EditCardRequest

interface EditCardScreenUC {
    fun userEditCard(request: EditCardRequest): Flow<Result<String>>
}