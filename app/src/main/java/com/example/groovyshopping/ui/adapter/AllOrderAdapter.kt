package com.example.groovyshopping.ui.adapter

import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.groovyshopping.data.order.Order
import com.example.groovyshopping.data.order.OrderStatus
import com.example.groovyshopping.data.order.getOrderStatus
import com.example.groovyshopping.databinding.OrderItemBinding
import com.example.groovyshopping.R

class AllOrdersAdapter : RecyclerView.Adapter<AllOrdersAdapter.OrdersViewHolder>() {

    inner class OrdersViewHolder(val binding: OrderItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(order: Order) {
            binding.tvOrderId.text = order.orderId.toString()
            binding.tvOrderDate.text = order.date

            val colorDrawable = when (getOrderStatus(order.orderStatus)) {
                is OrderStatus.Ordered -> ColorDrawable(binding.root.context.getColor(R.color.g_orange_yellow))
                is OrderStatus.Confirmed,
                is OrderStatus.Delivered,
                is OrderStatus.Shipped -> ColorDrawable(binding.root.context.getColor(R.color.g_green))
                is OrderStatus.Canceled,
                is OrderStatus.Returned -> ColorDrawable(binding.root.context.getColor(R.color.g_red))
            }

            binding.imageOrderState.setImageDrawable(colorDrawable)

            // Optional: لتعامل مع الضغط على العنصر
            binding.root.setOnClickListener {
                onClick?.invoke(order)
            }
        }
    }

    private val diffUtil = object : DiffUtil.ItemCallback<Order>() {
        override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean {
            return oldItem.orderId == newItem.orderId
        }

        override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = OrderItemBinding.inflate(inflater, parent, false)
        return OrdersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrdersViewHolder, position: Int) {
        val currentOrder = differ.currentList[position]
        holder.bind(currentOrder)
    }

    override fun getItemCount(): Int = differ.currentList.size

    var onClick: ((Order) -> Unit)? = null
}