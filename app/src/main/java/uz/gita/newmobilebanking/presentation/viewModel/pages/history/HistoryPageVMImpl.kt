package uz.gita.newmobilebanking.presentation.viewModel.pages.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import uz.gita.newmobilebanking.data.remote.response.transfer.MoneyTransferResponse
import uz.gita.newmobilebanking.domain.usecase.interfaces.transfer.HistoryPageUC
import uz.gita.newmobilebanking.utils.timber
import javax.inject.Inject

@HiltViewModel
class HistoryPageVMImpl @Inject constructor(
    private val useCase: HistoryPageUC
) : ViewModel(), HistoryPageVM {

    override fun getHistoryPagingData(): Flow<PagingData<MoneyTransferResponse.HistoryData>> {
        timber("viewModel entered", "NNNN")
        return useCase.getHistoryPagingData(viewModelScope)
    }
}