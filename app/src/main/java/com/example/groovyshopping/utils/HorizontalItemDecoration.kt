package com.example.groovyshopping.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class HorizontalItemDecoration(private val horizontalSpaceWidth: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.right = horizontalSpaceWidth

        // ممكن تضيف left للمسافة من أول عنصر
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.left = horizontalSpaceWidth
        }
    }
}