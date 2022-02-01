package uz.gita.newmobilebanking.domain.repository.impl

import androidx.paging.PagingData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import uz.gita.newmobilebanking.data.remote.response.transfer.MoneyTransferResponse
import uz.gita.newmobilebanking.domain.repository.interfaces.TransferRepository
import uz.gita.newmobilebanking.domain.usecase.interfaces.transfer.HistoryPageUC
import javax.inject.Inject

class HistoryPageUCImpl @Inject constructor(private val repository: TransferRepository) : HistoryPageUC {
    override fun getHistoryPagingData(scope: CoroutineScope): Flow<PagingData<MoneyTransferResponse.HistoryData>> =
        repository.getHistoryPagingData(scope)
}