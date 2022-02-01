package uz.gita.newmobilebanking.domain.usecase.impl.card

import kotlinx.coroutines.flow.Flow
import uz.gita.newmobilebanking.data.remote.request.card.DeleteCardRequest
import uz.gita.newmobilebanking.domain.repository.interfaces.CardRepository
import uz.gita.newmobilebanking.domain.usecase.interfaces.card.EditRemoveCardScreenUC
import javax.inject.Inject

class EditRemoveCardScreenUCImpl @Inject constructor(private val repository: CardRepository) :
    EditRemoveCardScreenUC {
    override fun userDeleteCard(request: DeleteCardRequest): Flow<Result<String>> =
        repository.userCardDelete(request)
}