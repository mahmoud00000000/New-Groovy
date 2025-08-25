package com.example.groovyshopping.ui.adapter

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.groovyshopping.data.CartProduct
import com.example.groovyshopping.databinding.BillingProductsRvItemBinding

class BillingProductsAdapter :
    RecyclerView.Adapter<BillingProductsAdapter.BillingProductsViewHolder>() {

    inner class BillingProductsViewHolder(val binding: BillingProductsRvItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(cartProduct: CartProduct) {
            binding.cartProduct = cartProduct
            binding.executePendingBindings()

            // تحميل الصورة يدويًا (لأن Glide مش مرتبط بـ DataBinding هنا)
            Glide.with(itemView.context)
                .load(cartProduct.product.images.firstOrNull())
                .into(binding.imageCartProduct)

            binding.imageCartProductColor.setImageDrawable(
                ColorDrawable(cartProduct.selectedColor ?: Color.TRANSPARENT)
            )

            val sizeText = cartProduct.selectedSize ?: ""
            binding.tvCartProductSize.text = sizeText
            if (sizeText.isBlank()) {
                binding.imageCartProductSize.setImageDrawable(ColorDrawable(Color.TRANSPARENT))
            }
        }
    }

    private val diffUtil = object : DiffUtil.ItemCallback<CartProduct>() {
        override fun areItemsTheSame(oldItem: CartProduct, newItem: CartProduct): Boolean {
            return oldItem.product.id == newItem.product.id
        }

        override fun areContentsTheSame(oldItem: CartProduct, newItem: CartProduct): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BillingProductsViewHolder {
        val binding = BillingProductsRvItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return BillingProductsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BillingProductsViewHolder, position: Int) {
        val cartProduct = differ.currentList[position]
        holder.bind(cartProduct)
    }

    override fun getItemCount(): Int = differ.currentList.size
}