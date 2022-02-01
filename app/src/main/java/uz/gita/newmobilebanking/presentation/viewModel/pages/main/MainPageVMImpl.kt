package uz.gita.newmobilebanking.presentation.viewModel.pages.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.newmobilebanking.data.remote.response.card.CardData
import uz.gita.newmobilebanking.domain.usecase.interfaces.MainPageUC
import uz.gita.newmobilebanking.utils.isConnected
import javax.inject.Inject

@HiltViewModel
class MainPageVMImpl @Inject constructor(private val useCase: MainPageUC) : ViewModel(),
    MainPageVM {

    override val visibilityProgressLiveData = MutableLiveData<Boolean>()
    override val errorMessageLiveData = MutableLiveData<String>()
    override val successGetAllCardsLiveData = MutableLiveData<List<CardData>>()

    override fun userGetAllCards() {
        if (!isConnected()) {
            errorMessageLiveData.value = "Internet is not working"
            return
        }
        visibilityProgressLiveData.value = true
        useCase.userGetAllCards().onEach {
            visibilityProgressLiveData.value = false
            it.onSuccess { cardList ->
                successGetAllCardsLiveData.value = cardList
            }
            it.onFailure { throwable ->
                errorMessageLiveData.value = throwable.message
            }
        }.catch {
            visibilityProgressLiveData.value = false
            errorMessageLiveData.value = "Error caught in viewModel"
        }.launchIn(viewModelScope)
    }

}