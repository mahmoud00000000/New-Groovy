package com.example.groovyshopping.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.groovyshopping.R
import com.example.groovyshopping.data.Address
import com.example.groovyshopping.databinding.AddressRvItemBinding

class AddressAdapter : RecyclerView.Adapter<AddressAdapter.AddressViewHolder>() {

    private var selectedAddress = -1

    inner class AddressViewHolder(val binding: AddressRvItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(address: Address, isSelected: Boolean) {
            binding.apply {
                buttonAddress.text = address.addressTitle

                val color = if (isSelected) R.color.g_blue else R.color.g_white
                buttonAddress.setBackgroundColor(
                    itemView.context.getColor(color)
                )

                // Click listener
                buttonAddress.setOnClickListener {
                    if (selectedAddress >= 0) {
                        notifyItemChanged(selectedAddress)
                    }
                    selectedAddress = bindingAdapterPosition
                    notifyItemChanged(selectedAddress)
                    onClick?.invoke(address)
                }
            }
        }
    }

    private val diffUtil = object : DiffUtil.ItemCallback<Address>() {
        override fun areItemsTheSame(oldItem: Address, newItem: Address): Boolean {
            return oldItem.addressTitle == newItem.addressTitle &&
                    oldItem.fullName == newItem.fullName
        }

        override fun areContentsTheSame(oldItem: Address, newItem: Address): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressViewHolder {
        val binding = AddressRvItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return AddressViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AddressViewHolder, position: Int) {
        val address = differ.currentList[position]
        holder.bind(address, position == selectedAddress)
    }

    override fun getItemCount(): Int = differ.currentList.size

    var onClick: ((Address) -> Unit)? = null
}