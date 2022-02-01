package uz.gita.newmobilebanking.presentation.ui.dialogs

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.newmobilebanking.R
import uz.gita.newmobilebanking.data.remote.response.transfer.DataSendMoney
import uz.gita.newmobilebanking.databinding.DialogTransferInfoBinding
import java.text.SimpleDateFormat
import java.util.*

class TransferInfoDialog : DialogFragment(R.layout.dialog_transfer_info) {

    private var listener: (() -> Unit)? = null
    private val viewBinding by viewBinding(DialogTransferInfoBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //make transparent background dialog
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        arguments?.let { it ->
            val data = it.getParcelable<DataSendMoney>("data")
            if (data != null) {
                viewBinding.amountValue.text = data.amount.toString()
                viewBinding.senderValue.text = data.sender.toString()
                viewBinding.receiverValue.text = data.receiver.toString()
                viewBinding.feeValue.text = data.fee.toString()
                viewBinding.timeValue.text = getDate(data.time, "dd/MM/yyyy hh:mm:ss")
            }
        }

        viewBinding.btnBackMenu.setOnClickListener {
            listener?.invoke()
        }

    }

    fun getDate(milliSeconds: Long, dateFormat: String): String {
        // Create a DateFormatter object for displaying date in specified format.
        val formatter = SimpleDateFormat(dateFormat)

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = milliSeconds
        return formatter.format(calendar.time)
    }

    override fun onResume() {
        super.onResume()
        val params: ViewGroup.LayoutParams = dialog!!.window!!.attributes
        params.width = ViewGroup.LayoutParams.MATCH_PARENT
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT
        dialog!!.window!!.attributes = params as WindowManager.LayoutParams
    }


    fun setDialogListener(f: () -> Unit) {
        listener = f
    }

}