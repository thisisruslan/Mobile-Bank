package uz.gita.newmobilebanking.domain.usecase.interfaces.card

import kotlinx.coroutines.flow.Flow
import uz.gita.newmobilebanking.data.remote.request.card.AddCardRequest

interface AddCardScreenUC {
    fun userAddCard(request: AddCardRequest) : Flow<Result<String>>
}