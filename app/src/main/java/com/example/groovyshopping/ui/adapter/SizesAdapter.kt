package com.example.groovyshopping.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.groovyshopping.databinding.SizeRvItemBinding

class SizesAdapter : RecyclerView.Adapter<SizesAdapter.SizesViewHolder>() {

    private var selectedPosition = -1

    inner class SizesViewHolder(val binding: SizeRvItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(size: String, position: Int) {
            binding.tvSize.text = size

            if (position == selectedPosition) {
                binding.imageShadow.visibility = View.VISIBLE
            } else {
                binding.imageShadow.visibility = View.INVISIBLE
            }

            binding.root.setOnClickListener {
                if (selectedPosition >= 0) notifyItemChanged(selectedPosition)
                selectedPosition = adapterPosition
                notifyItemChanged(selectedPosition)
                onItemClick?.invoke(size)
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SizesViewHolder {
        val binding = SizeRvItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return SizesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SizesViewHolder, position: Int) {
        val size = differ.currentList[position]
        holder.bind(size, position)
    }

    override fun getItemCount(): Int = differ.currentList.size

    var onItemClick: ((String) -> Unit)? = null

    // لو حبيت ترجع المقاس اللي المستخدم اختاره
    fun getSelectedSize(): String? {
        return if (selectedPosition in 0 until differ.currentList.size) {
            differ.currentList[selectedPosition]
        } else null
    }
}