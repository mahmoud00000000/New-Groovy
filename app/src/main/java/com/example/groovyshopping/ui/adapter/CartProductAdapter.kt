package com.example.groovyshopping.ui.adapter

import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.graphics.Color
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.groovyshopping.R
import com.example.groovyshopping.data.CartProduct
import com.example.groovyshopping.databinding.CartProductItemBinding
import com.example.groovyshopping.helper.getProductPrice

class CartProductAdapter : RecyclerView.Adapter<CartProductAdapter.CartProductsViewHolder>() {

    inner class CartProductsViewHolder(val binding: CartProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(cartProduct: CartProduct) {
            binding.apply {
                // صورة المنتج (لو فاضية نعرض Placeholder)
                val imageUrl = cartProduct.product.images.firstOrNull()
                Glide.with(itemView)
                    .load(imageUrl)
                    .placeholder(R.drawable.ic_placeholder) // حط أي placeholder موجود عندك
                    .error(R.drawable.ic_placeholder)
                    .into(imageCartProduct)

                tvProductCartName.text = cartProduct.product.name
                tvCartProductQuantity.text = cartProduct.quantity.toString()

                // سعر المنتج بعد الخصم
                val priceAfterPercentage = cartProduct.product.offerPercentage
                    .getProductPrice(cartProduct.product.price)
                tvProductCartPrice.text = "$ ${String.format("%.2f", priceAfterPercentage)}"

                // لون المنتج (لو مش موجود نخليه شفاف)
                val color = cartProduct.selectedColor ?: Color.TRANSPARENT
                imageCartProductColor.setImageDrawable(ColorDrawable(color))

                // مقاس المنتج (لو مش موجود نخليه فاضي ونشيل الخلفية)
                val size = cartProduct.selectedSize ?: ""
                tvCartProductSize.text = size
                if (size.isEmpty()) {
                    imageCartProductSize.setImageDrawable(ColorDrawable(Color.TRANSPARENT))
                }
            }
        }
    }

    private val diffCallback = object : DiffUtil.ItemCallback<CartProduct>() {
        override fun areItemsTheSame(oldItem: CartProduct, newItem: CartProduct): Boolean {
            return oldItem.product.id == newItem.product.id
        }

        override fun areContentsTheSame(oldItem: CartProduct, newItem: CartProduct): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartProductsViewHolder {
        return CartProductsViewHolder(
            CartProductItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: CartProductsViewHolder, position: Int) {
        val cartProduct = differ.currentList.getOrNull(position) ?: return
        holder.bind(cartProduct)

        holder.itemView.setOnClickListener {
            onProductClick?.invoke(cartProduct)
        }

        holder.binding.imagePlus.setOnClickListener {
            onPlusClick?.invoke(cartProduct)
        }

        holder.binding.imageMinus.setOnClickListener {
            onMinusClick?.invoke(cartProduct)
        }
    }

    override fun getItemCount(): Int = differ.currentList.size

    var onProductClick: ((CartProduct) -> Unit)? = null
    var onPlusClick: ((CartProduct) -> Unit)? = null
    var onMinusClick: ((CartProduct) -> Unit)? = null
}