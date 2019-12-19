package com.example.myfinances2020.ui.transactions.listTransactions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myfinances2020.repository.database.entities.Transaction
import com.example.myfinances2020.databinding.ListItemTransactionBinding

class TransactionsAdapter(private val clickListener: TransactionListener) : ListAdapter<Transaction,
        TransactionsAdapter.ViewHolder>(TransactionDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(clickListener, item)
    }

    class ViewHolder private constructor(private val binding: ListItemTransactionBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(clickListener: TransactionListener, item: Transaction) {
            binding.transaction = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemTransactionBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }
}

class TransactionDiffCallback : DiffUtil.ItemCallback<Transaction>() {
    override fun areItemsTheSame(oldItem: Transaction, newItem: Transaction) = oldItem._id == newItem._id

    override fun areContentsTheSame(oldItem: Transaction, newItem: Transaction) = oldItem == newItem
}

class TransactionListener(val clickListener: (transactionId: Long) -> Unit) {
    fun onClick(transaction: Transaction) = clickListener(transaction._id)
}