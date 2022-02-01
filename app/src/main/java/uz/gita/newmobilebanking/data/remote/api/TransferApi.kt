package uz.gita.newmobilebanking.data.remote.api

import retrofit2.Response
import retrofit2.http.*
import uz.gita.newmobilebanking.data.remote.request.transfer.SendMoneyRequest
import uz.gita.newmobilebanking.data.remote.response.transfer.MoneyTransferResponse
import uz.gita.newmobilebanking.data.remote.response.transfer.SendMoneyResponse

interface TransferApi {
    @POST("money-transfer/send-money")
    suspend fun sendMoney(@Header("token") token: String, @Body data: SendMoneyRequest) : Response<SendMoneyResponse>

    @GET("money-transfer/history")
    suspend fun getMoneyTransferHistory(
        @Header("token") token: String,
        @Query("page_number") pageNumber: Int,
        @Query("page_size") pageSize: Int
    ): Response<MoneyTransferResponse.TransferResponse>
}