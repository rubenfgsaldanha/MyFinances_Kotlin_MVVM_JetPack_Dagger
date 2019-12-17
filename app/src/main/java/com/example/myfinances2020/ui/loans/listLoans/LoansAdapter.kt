package com.example.myfinances2020.ui.loans.listLoans

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myfinances2020.databinding.ListItemLoanBinding
import com.example.myfinances2020.repository.database.entities.Loan

class LoansAdapter(private val clickListener: LoanListener) : ListAdapter<Loan, LoansAdapter.ViewHolder>(LoanDiffCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(clickListener, item)
    }

    class ViewHolder private constructor(private val binding: ListItemLoanBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(clickListener: LoanListener, item: Loan){
            binding.loan = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup) : ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemLoanBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }
}

class LoanDiffCallback : DiffUtil.ItemCallback<Loan>(){
    override fun areItemsTheSame(oldItem: Loan, newItem: Loan) = oldItem._id == newItem._id

    override fun areContentsTheSame(oldItem: Loan, newItem: Loan) = oldItem == newItem
}

class LoanListener(val clickListener: (loadId: Long) -> Unit){
    fun onClick(loan: Loan) = clickListener(loan._id)
}