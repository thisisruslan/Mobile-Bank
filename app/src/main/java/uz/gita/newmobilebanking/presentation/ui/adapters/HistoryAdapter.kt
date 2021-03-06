package uz.gita.newmobilebanking.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import uz.gita.newmobilebanking.R
import uz.gita.newmobilebanking.data.remote.response.transfer.MoneyTransferResponse
import uz.gita.newmobilebanking.utils.timber

class HistoryAdapter :
    PagingDataAdapter<MoneyTransferResponse.HistoryData, HistoryAdapter.HistoryViewHolder>(
        MyDiffUtil
    ) {

    object MyDiffUtil : DiffUtil.ItemCallback<MoneyTransferResponse.HistoryData>() {
        override fun areItemsTheSame(
            oldItem: MoneyTransferResponse.HistoryData,
            newItem: MoneyTransferResponse.HistoryData
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: MoneyTransferResponse.HistoryData,
            newItem: MoneyTransferResponse.HistoryData
        ): Boolean {
            return oldItem == newItem
        }
    }

    inner class HistoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val textMoney = view.findViewById<TextView>(R.id.textMoney)
        private val textFee = view.findViewById<TextView>(R.id.textFee)

        //status 0 -> pul tushdi, else -> pul yechib olindi
        fun bind() {
            getItem(absoluteAdapterPosition)?.let {
                timber(it.amount.toString() + " adapter", "NNNN")
                if (it.status == 0) {
                    textMoney.setTextColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.colorGreen
                        )
                    )
                    textMoney.text = "+${it.amount.toString()}"
                    textFee.text = it.fee.toString()
                } else {
                    textMoney.setTextColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.colorRed
                        )
                    )
                    textMoney.text = "-${it.amount.toString()}"
                    textFee.text = it.fee.toString()
                }
            }
        }
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        HistoryViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)
        )

    override fun getItemCount(): Int {
        timber("PPPP"+super.getItemCount().toString(), "NNNN")
        return super.getItemCount()
    }
}
