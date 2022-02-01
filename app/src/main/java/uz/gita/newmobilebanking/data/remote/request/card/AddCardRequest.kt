package uz.gita.newmobilebanking.data.remote.request.card

data class AddCardRequest(
    val pan : String,
    val exp : String,
    val cardName: String
)
