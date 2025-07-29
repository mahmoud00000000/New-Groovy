package com.example.groovyshopping.ui.adapter

import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.groovyshopping.databinding.ColorRvItemBinding

class ColorsAdapter : RecyclerView.Adapter<ColorsAdapter.ColorsViewHolder>() {

    private var selectedPosition = -1

    inner class ColorsViewHolder(val binding: ColorRvItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(color: Int, position: Int) {
            // عيّن اللون في الصورة
            val colorDrawable = ColorDrawable(color)
            binding.imageColor.setImageDrawable(colorDrawable)

            // أظهر/اخفي المؤشرات بناءً على التحديد
            if (position == selectedPosition) {
                binding.imageShadow.visibility = View.VISIBLE
                binding.imagePicked.visibility = View.VISIBLE
            } else {
                binding.imageShadow.visibility = View.INVISIBLE
                binding.imagePicked.visibility = View.INVISIBLE
            }

            // اضغط على العنصر
            binding.root.setOnClickListener {
                if (selectedPosition >= 0) notifyItemChanged(selectedPosition)
                selectedPosition = adapterPosition
                notifyItemChanged(selectedPosition)
                onItemClick?.invoke(color)
            }
        }
    }

    private val diffCallback = object : DiffUtil.ItemCallback<Int>() {
        override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorsViewHolder {
        val binding = ColorRvItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ColorsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ColorsViewHolder, position: Int) {
        val color = differ.currentList[position]
        holder.bind(color, position)
    }

    override fun getItemCount(): Int = differ.currentList.size

    var onItemClick: ((Int) -> Unit)? = null

    // لو حبيت تجيب اللون المحدد
    fun getSelectedColor(): Int? {
        return if (selectedPosition in 0 until differ.currentList.size) {
            differ.currentList[selectedPosition]
        } else null
    }
}