package uz.gita.newmobilebanking.data.remote.request.auth

data class VerifyRequest(
    val phone: String,
    val code: String
)
