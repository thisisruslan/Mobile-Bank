package uz.gita.newmobilebanking.data.remote.response.transfer

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class SendMoneyResponse(
    val data: DataSendMoney
)

@Parcelize
data class DataSendMoney(
    val id: Int,
    val sender: Int,
    val receiver: Int,
    val amount: Float,
    val time: Long,
    val fee: Float,
    val status: Int
) : Parcelable


