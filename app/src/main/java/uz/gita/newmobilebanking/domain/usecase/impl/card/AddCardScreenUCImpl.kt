package uz.gita.newmobilebanking.domain.usecase.impl.card

import kotlinx.coroutines.flow.Flow
import uz.gita.newmobilebanking.data.remote.request.card.AddCardRequest
import uz.gita.newmobilebanking.domain.repository.interfaces.CardRepository
import uz.gita.newmobilebanking.domain.usecase.interfaces.card.AddCardScreenUC
import javax.inject.Inject

class AddCardScreenUCImpl @Inject constructor(private val repository: CardRepository) :
    AddCardScreenUC {
    override fun userAddCard(request: AddCardRequest): Flow<Result<String>> =
        repository.userAddCard(request)


}