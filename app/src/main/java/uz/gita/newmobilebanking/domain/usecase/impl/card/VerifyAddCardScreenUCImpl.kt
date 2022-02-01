package uz.gita.newmobilebanking.domain.usecase.impl.card

import kotlinx.coroutines.flow.Flow
import uz.gita.newmobilebanking.data.remote.request.card.VerifyAddCardRequest
import uz.gita.newmobilebanking.data.remote.response.card.VerifyAddCardResponse
import uz.gita.newmobilebanking.domain.repository.interfaces.CardRepository
import uz.gita.newmobilebanking.domain.usecase.interfaces.card.VerifyAddCardScreenUC
import javax.inject.Inject

class VerifyAddCardScreenUCImpl @Inject constructor(private val repository: CardRepository) :
    VerifyAddCardScreenUC {
    override fun userVerifyAddCardScreen(request: VerifyAddCardRequest): Flow<Result<VerifyAddCardResponse>> =
        repository.userVerifyAddCard(request)
}