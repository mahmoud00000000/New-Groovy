package com.example.groovyshopping.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.groovyshopping.databinding.ViewpagerImageItemBinding

class ViewPager2Images :
    RecyclerView.Adapter<ViewPager2Images.ViewPager2ImagesViewHolder>() {

    inner class ViewPager2ImagesViewHolder(val binding: ViewpagerImageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(imageUrl: String) {
            // تحميل الصورة باستخدام Glide
            Glide.with(binding.imageProductDetails.context)
                .load(imageUrl)
                .diskCacheStrategy(com.bumptech.glide.load.engine.DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(binding.imageProductDetails)

            // Click listener لو عايز تستخدمه
            binding.imageProductDetails.setOnClickListener {
                onClick?.invoke(imageUrl)
            }
        }
    }

    private val diffCallback = object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPager2ImagesViewHolder {
        val binding = ViewpagerImageItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewPager2ImagesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewPager2ImagesViewHolder, position: Int) {
        val imageUrl = differ.currentList[position]
        holder.bind(imageUrl)
    }

    override fun getItemCount(): Int = differ.currentList.size

    var onClick: ((String) -> Unit)? = null
}