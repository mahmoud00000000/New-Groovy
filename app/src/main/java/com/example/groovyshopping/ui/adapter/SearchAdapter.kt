package com.example.groovyshopping.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.groovyshopping.R
import com.example.groovyshopping.data.Product
import com.example.groovyshopping.databinding.SearchProductItemBinding

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    inner class SearchViewHolder(val binding: SearchProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            // تحميل الصورة مع التحقق من وجود صور
            if (product.images.isNotEmpty()) {
                Glide.with(binding.root)
                    .load(product.images.first())
                    .into(binding.imageProduct)
            } else {
                // صورة افتراضية في حالة عدم وجود صور
                Glide.with(binding.root)
                    .load(android.R.drawable.ic_menu_gallery) // تأكد إن الصورة دي موجودة في drawable
                    .into(binding.imageProduct)
            }

            // ضبط البيانات
            binding.tvProductName.text = product.name
            binding.tvProductPrice.text = "${product.price} EGP"

            // عند الضغط على المنتج
            binding.root.setOnClickListener {
                onItemClick?.invoke(product)
            }
        }
    }

    private val diffCallback = object : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding = SearchProductItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val product = differ.currentList[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int = differ.currentList.size

    var onItemClick: ((Product) -> Unit)? = null
}