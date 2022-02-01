package uz.gita.newmobilebanking.domain.usecase.impl.transfer

import kotlinx.coroutines.flow.Flow
import uz.gita.newmobilebanking.data.remote.request.transfer.SendMoneyRequest
import uz.gita.newmobilebanking.data.remote.response.transfer.SendMoneyResponse
import uz.gita.newmobilebanking.domain.repository.interfaces.TransferRepository
import uz.gita.newmobilebanking.domain.usecase.interfaces.transfer.TransferPageUC
import javax.inject.Inject

class TransferPageUCImpl @Inject constructor(private val repository: TransferRepository) :
    TransferPageUC {

    override fun sendMoney(request: SendMoneyRequest): Flow<Result<SendMoneyResponse>> {
        return repository.sendMoney(request)
    }

}