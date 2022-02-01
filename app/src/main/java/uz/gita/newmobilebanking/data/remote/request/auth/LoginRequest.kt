package uz.gita.newmobilebanking.data.remote.request.auth

data class LoginRequest(
    val phone: String,
    val password: String
)
