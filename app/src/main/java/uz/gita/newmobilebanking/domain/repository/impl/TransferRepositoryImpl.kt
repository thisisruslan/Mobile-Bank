package uz.gita.newmobilebanking.domain.repository.impl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.gita.newmobilebanking.data.datasource.HistoryDataSource
import uz.gita.newmobilebanking.data.local.sharepref.SharePref
import uz.gita.newmobilebanking.data.remote.api.TransferApi
import uz.gita.newmobilebanking.data.remote.request.transfer.SendMoneyRequest
import uz.gita.newmobilebanking.data.remote.response.transfer.MoneyTransferResponse
import uz.gita.newmobilebanking.data.remote.response.transfer.SendMoneyResponse
import uz.gita.newmobilebanking.domain.repository.interfaces.TransferRepository
import javax.inject.Inject

class TransferRepositoryImpl @Inject constructor(
    private val pref: SharePref,
    private val api : TransferApi,
    private val gson: Gson
) : TransferRepository {

    override fun sendMoney(request: SendMoneyRequest): Flow<Result<SendMoneyResponse>> = flow {
        val response = api.sendMoney(pref.accessToken, request)
        if (response.isSuccessful) {
            emit(Result.success(response.body()!!))
        } else {
            var st = "Error caught in Repository"
            response.errorBody()?.let {
                st = gson.fromJson(it.string(), SendMoneyResponse::class.java).data.toString()
            }
            emit(Result.failure<SendMoneyResponse>(Throwable(st)))
        }
    }.flowOn(IO)


    private val config = PagingConfig(10)
    override fun getHistoryPagingData(scope: CoroutineScope): Flow<PagingData<MoneyTransferResponse.HistoryData>> =
        Pager(config) {
            HistoryDataSource(api, pref)
        }.flow.cachedIn(scope)
}