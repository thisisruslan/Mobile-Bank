package uz.gita.newmobilebanking.presentation.viewModel.pages.main

import androidx.lifecycle.LiveData
import uz.gita.newmobilebanking.data.remote.api.CardApi
import uz.gita.newmobilebanking.data.remote.response.card.CardData
import uz.gita.newmobilebanking.data.remote.response.transfer.SendMoneyResponse

interface MainPageVM {
    val visibilityProgressLiveData: LiveData<Boolean>
    val errorMessageLiveData: LiveData<String>
    val successGetAllCardsLiveData: LiveData<List<CardData>>
    fun userGetAllCards()
}