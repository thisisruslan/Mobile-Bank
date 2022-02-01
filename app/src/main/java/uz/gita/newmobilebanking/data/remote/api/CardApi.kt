package uz.gita.newmobilebanking.data.remote.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import uz.gita.newmobilebanking.data.remote.request.card.AddCardRequest
import uz.gita.newmobilebanking.data.remote.request.card.DeleteCardRequest
import uz.gita.newmobilebanking.data.remote.request.card.EditCardRequest
import uz.gita.newmobilebanking.data.remote.request.card.VerifyAddCardRequest
import uz.gita.newmobilebanking.data.remote.response.UniversalResponse
import uz.gita.newmobilebanking.data.remote.response.card.AllCardResponse
import uz.gita.newmobilebanking.data.remote.response.card.VerifyAddCardResponse

interface CardApi {

    @GET("card/all")
    suspend fun getAllCard(@Header("token") token: String) : Response<AllCardResponse>

    @POST("card/add-card")
    suspend fun addCard(@Header("token") token: String, @Body data: AddCardRequest) : Response<UniversalResponse>

    @POST("card/verify")
    suspend fun verifyCard(@Header("token") token: String, @Body data: VerifyAddCardRequest) : Response<VerifyAddCardResponse>

    @POST("card/edit-card")
    suspend fun edit(@Header("token") token: String, @Body request: EditCardRequest) : Response<UniversalResponse>

    @POST("card/delete-card")
    suspend fun delete(@Header("token") token: String, @Body data: DeleteCardRequest) : Response<UniversalResponse>

//    @GET("v1/card/owner-by-pan")
//    suspend fun getOwnerByPan(@Header("token") token: String, @Body pan: OwnerByPanRequest) : Response<OwnerByPanResponse>

}