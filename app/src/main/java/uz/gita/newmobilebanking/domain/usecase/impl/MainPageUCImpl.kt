package uz.gita.newmobilebanking.domain.usecase.impl

import kotlinx.coroutines.flow.Flow
import uz.gita.newmobilebanking.data.remote.response.card.CardData
import uz.gita.newmobilebanking.domain.repository.interfaces.CardRepository
import uz.gita.newmobilebanking.domain.usecase.interfaces.MainPageUC
import javax.inject.Inject

class MainPageUCImpl @Inject constructor(private val repository: CardRepository) : MainPageUC {
    override fun userGetAllCards() : Flow<Result<List<CardData>>> = repository.userGetAllCards()
}