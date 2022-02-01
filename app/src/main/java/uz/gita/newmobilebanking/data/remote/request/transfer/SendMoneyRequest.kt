package uz.gita.newmobilebanking.data.remote.request.transfer

data class SendMoneyRequest(
    val sender: Int,
    val receiverPan: String,
    val amount: Long
)

