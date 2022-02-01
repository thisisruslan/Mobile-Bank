package uz.gita.newmobilebanking.domain.repository.interfaces

import androidx.paging.PagingData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import uz.gita.newmobilebanking.data.remote.request.transfer.SendMoneyRequest
import uz.gita.newmobilebanking.data.remote.response.transfer.MoneyTransferResponse
import uz.gita.newmobilebanking.data.remote.response.transfer.SendMoneyResponse

interface TransferRepository {
    fun sendMoney(request: SendMoneyRequest) : Flow<Result<SendMoneyResponse>>
    fun getHistoryPagingData(scope: CoroutineScope): Flow<PagingData<MoneyTransferResponse.HistoryData>>
}