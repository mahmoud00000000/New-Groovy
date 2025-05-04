package com.homecookapp.user.utils

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class GridPaginationListener
    (private val layoutManager: GridLayoutManager, var pageSize:Int) : RecyclerView.OnScrollListener() {

    abstract val isLastPage: Boolean

    abstract val isLoading: Boolean

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

        if (!isLoading && !isLastPage) {
            if (visibleItemCount + firstVisibleItemPosition >= totalItemCount
                && firstVisibleItemPosition >= 0
                && totalItemCount >= pageSize
            ) {
                loadMoreItems()
            }
        }
    }

    protected abstract fun loadMoreItems()

    companion object {
        val PAGE_START = 1
//        private val PAGE_SIZE = 25
    }
}