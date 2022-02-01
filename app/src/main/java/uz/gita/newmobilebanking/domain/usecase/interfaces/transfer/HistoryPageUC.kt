package uz.gita.newmobilebanking.domain.usecase.interfaces.transfer

import androidx.paging.PagingData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import uz.gita.newmobilebanking.data.remote.response.transfer.MoneyTransferResponse

interface HistoryPageUC {
    fun getHistoryPagingData(scope: CoroutineScope): Flow<PagingData<MoneyTransferResponse.HistoryData>>
}