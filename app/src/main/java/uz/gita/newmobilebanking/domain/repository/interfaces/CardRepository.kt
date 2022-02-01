package uz.gita.newmobilebanking.domain.repository.interfaces

import kotlinx.coroutines.flow.Flow
import uz.gita.newmobilebanking.data.remote.request.card.AddCardRequest
import uz.gita.newmobilebanking.data.remote.request.card.DeleteCardRequest
import uz.gita.newmobilebanking.data.remote.request.card.EditCardRequest
import uz.gita.newmobilebanking.data.remote.request.card.VerifyAddCardRequest
import uz.gita.newmobilebanking.data.remote.response.card.CardData
import uz.gita.newmobilebanking.data.remote.response.card.VerifyAddCardResponse

interface CardRepository {
    fun userAddCard(addCardRequest: AddCardRequest) : Flow<Result<String>>
    fun userVerifyAddCard(verifyAddCardRequest: VerifyAddCardRequest) : Flow<Result<VerifyAddCardResponse>>
    fun userCardEdit(request: EditCardRequest) : Flow<Result<String>>
    fun userCardDelete(request: DeleteCardRequest) : Flow<Result<String>>
    fun userGetAllCards() : Flow<Result<List<CardData>>>

//    fun ownerByPan(data: OwnerByPanRequest) : Flow<Result<OwnerByPanResponse>>
}