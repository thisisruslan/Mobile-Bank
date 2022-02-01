package uz.gita.newmobilebanking.domain.usecase.interfaces.card

import kotlinx.coroutines.flow.Flow
import uz.gita.newmobilebanking.data.remote.request.card.VerifyAddCardRequest
import uz.gita.newmobilebanking.data.remote.response.card.VerifyAddCardResponse

interface VerifyAddCardScreenUC {
    fun userVerifyAddCardScreen(request: VerifyAddCardRequest): Flow<Result<VerifyAddCardResponse>>
}