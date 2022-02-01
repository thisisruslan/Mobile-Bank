package uz.gita.newmobilebanking.presentation.viewModel.pages.history

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import uz.gita.newmobilebanking.data.remote.response.transfer.MoneyTransferResponse

interface HistoryPageVM {
    fun getHistoryPagingData(): Flow<PagingData<MoneyTransferResponse.HistoryData>>
}