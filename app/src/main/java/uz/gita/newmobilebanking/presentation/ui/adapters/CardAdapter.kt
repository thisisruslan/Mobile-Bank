package uz.gita.newmobilebanking.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.newmobilebanking.R
import uz.gita.newmobilebanking.data.remote.response.card.CardData
import uz.gita.newmobilebanking.databinding.ItemCardBinding
import uz.gita.newmobilebanking.utils.scope

class CardAdapter : ListAdapter<CardData, CardAdapter.CardViewHolder>(DiffItem) {
    private var cardClickListener: ((CardData) -> Unit)? = null

    object DiffItem : DiffUtil.ItemCallback<CardData>() {
        override fun areItemsTheSame(oldItem: CardData, newItem: CardData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CardData, newItem: CardData): Boolean {
            return oldItem.status == newItem.status ||
                    oldItem.cardName == newItem.cardName ||
                    oldItem.exp == newItem.exp ||
                    oldItem.owner == newItem.owner ||
                    oldItem.balance == newItem.balance ||
                    oldItem.pan == newItem.pan
        }
    }

    inner class CardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding by viewBinding(ItemCardBinding::bind)

        fun bind() = binding.scope {
            itemView.setOnClickListener {
                cardClickListener?.invoke(getItem(absoluteAdapterPosition))
            }
            cardName.text = getItem(absoluteAdapterPosition).cardName
            exp.text = getItem(absoluteAdapterPosition).exp
            balance.text = getItem(absoluteAdapterPosition).balance.toString()
            owner.text = getItem(absoluteAdapterPosition).owner
            pan.text = getItem(absoluteAdapterPosition).pan
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder =
        CardViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false))

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind()
    }

    fun setCardListener(f: (CardData) -> Unit) {
        cardClickListener = f
    }
}