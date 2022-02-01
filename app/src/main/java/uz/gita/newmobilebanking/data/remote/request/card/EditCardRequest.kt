package uz.gita.newmobilebanking.data.remote.request.card

data class EditCardRequest(
    val cardNumber : String,
    val newName: String
)