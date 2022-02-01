package uz.gita.newmobilebanking.domain.usecase.interfaces.card

import kotlinx.coroutines.flow.Flow
import uz.gita.newmobilebanking.data.remote.request.card.DeleteCardRequest

interface EditRemoveCardScreenUC {
    fun userDeleteCard(request: DeleteCardRequest) : Flow<Result<String>>
}