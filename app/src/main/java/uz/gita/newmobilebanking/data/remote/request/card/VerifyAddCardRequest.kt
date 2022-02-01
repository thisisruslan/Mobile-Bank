package uz.gita.newmobilebanking.data.remote.request.card

data class VerifyAddCardRequest(
    val pan: String,
    val code: String
)
