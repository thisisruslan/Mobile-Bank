package uz.gita.newmobilebanking.domain.usecase.impl.card

import kotlinx.coroutines.flow.Flow
import uz.gita.newmobilebanking.data.remote.request.card.EditCardRequest
import uz.gita.newmobilebanking.domain.repository.interfaces.CardRepository
import uz.gita.newmobilebanking.domain.usecase.interfaces.card.EditCardScreenUC
import javax.inject.Inject

class EditCardScreenUCImpl @Inject constructor(private val repository: CardRepository) :
    EditCardScreenUC {
    override fun userEditCard(request: EditCardRequest): Flow<Result<String>> =
        repository.userCardEdit(request)
}