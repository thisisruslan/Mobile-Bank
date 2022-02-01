package uz.gita.newmobilebanking.domain.repository.impl

import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.gita.newmobilebanking.data.local.sharepref.SharePref
import uz.gita.newmobilebanking.data.remote.api.CardApi
import uz.gita.newmobilebanking.data.remote.request.card.AddCardRequest
import uz.gita.newmobilebanking.data.remote.request.card.DeleteCardRequest
import uz.gita.newmobilebanking.data.remote.request.card.EditCardRequest
import uz.gita.newmobilebanking.data.remote.request.card.VerifyAddCardRequest
import uz.gita.newmobilebanking.data.remote.response.UniversalResponse
import uz.gita.newmobilebanking.data.remote.response.card.AllCardResponse
import uz.gita.newmobilebanking.data.remote.response.card.CardData
import uz.gita.newmobilebanking.data.remote.response.card.VerifyAddCardResponse
import uz.gita.newmobilebanking.domain.repository.interfaces.CardRepository
import javax.inject.Inject

class CardRepositoryImpl @Inject constructor(
    private val pref: SharePref,
    private val api: CardApi,
    private val gson: Gson
) : CardRepository {

    override fun userAddCard(addCardRequest: AddCardRequest): Flow<Result<String>> = flow {
        val response = api.addCard(pref.accessToken, addCardRequest)
        if (response.isSuccessful) {
            emit(Result.success(response.body()!!.message))
        } else {
            var st = "Error caught in Repository"
            response.errorBody()?.let {
                st = gson.fromJson(it.string(), UniversalResponse::class.java).message
            }
            emit(Result.failure<String>(Throwable(st)))
        }
    }.flowOn(IO)

    override fun userGetAllCards(): Flow<Result<List<CardData>>> = flow {
        val response = api.getAllCard(pref.accessToken)
        if (response.isSuccessful) {
            emit(Result.success(response.body()?.data!!.data))
        } else {
            var st = "Error caught in Repository"
            response.errorBody()?.let {
                st = gson.fromJson(it.string(), AllCardResponse::class.java).toString()
            }
            emit(Result.failure<List<CardData>>(Throwable(st)))
        }
    }.flowOn(IO)


    override fun userVerifyAddCard(verifyAddCardRequest: VerifyAddCardRequest): Flow<Result<VerifyAddCardResponse>> =
        flow {
            val response = api.verifyCard(pref.accessToken, verifyAddCardRequest)
            if (response.isSuccessful) {
                emit(Result.success(response.body()!!))
            } else {
                var st = "Error caught in repository"
                response.errorBody()?.let {
                    st = gson.fromJson(it.string(), VerifyAddCardResponse::class.java).toString()
                }
                emit(Result.failure<VerifyAddCardResponse>(Throwable(st)))
            }
        }.flowOn(IO)


    override fun userCardEdit(request: EditCardRequest): Flow<Result<String>>  = flow {
        val response = api.edit(pref.accessToken, request)
        if (response.isSuccessful) {
            emit(Result.success(response.body()!!.message))
        } else {
            var st = "Error caught in repository"
            response.errorBody()?.let {
                st = gson.fromJson(it.string(), UniversalResponse::class.java).message
            }
            emit(Result.failure<String>(Throwable(st)))
        }
    }.flowOn(IO)

    override fun userCardDelete(request: DeleteCardRequest): Flow<Result<String>> = flow {
        val response = api.delete(pref.accessToken, request)
        if (response.isSuccessful) {
            emit(Result.success(response.body()!!.message))
        } else {
            var st = "Error caught in repository"
            response.errorBody()?.let {
                st = gson.fromJson(it.string(), UniversalResponse::class.java).message
            }
            emit(Result.failure<String>(Throwable(st)))
        }
    }.flowOn(IO)


}