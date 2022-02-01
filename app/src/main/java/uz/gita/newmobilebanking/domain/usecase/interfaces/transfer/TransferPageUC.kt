package uz.gita.newmobilebanking.domain.usecase.interfaces.transfer

import kotlinx.coroutines.flow.Flow
import uz.gita.newmobilebanking.data.remote.request.transfer.SendMoneyRequest
import uz.gita.newmobilebanking.data.remote.response.transfer.SendMoneyResponse

interface TransferPageUC {
    fun sendMoney(request: SendMoneyRequest) : Flow<Result<SendMoneyResponse>>
}