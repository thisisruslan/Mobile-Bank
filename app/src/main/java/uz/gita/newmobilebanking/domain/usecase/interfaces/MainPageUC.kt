package uz.gita.newmobilebanking.domain.usecase.interfaces

import kotlinx.coroutines.flow.Flow
import uz.gita.newmobilebanking.data.remote.response.card.CardData

interface MainPageUC {
    fun userGetAllCards() : Flow<Result<List<CardData>>>
}