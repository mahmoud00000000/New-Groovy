package com.homecookapp.user.utils

import android.util.Log
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView

 class NestedPaginationListener {



    private var paginatedRV: RecyclerView? = null
     fun enablePagination(nestedScrollView: NestedScrollView, recyclerView: RecyclerView, nextPageFunction: () -> Unit) {
        //Disable nested recyclerview from scrolling
        ViewCompat.setNestedScrollingEnabled(recyclerView, false)
        //Attach scroll listener to nested scrollview
        nestedScrollView.viewTreeObserver?.addOnScrollChangedListener {
            //If the paginated rv is not calculated already
            if (paginatedRV == null) {
                //Get the parent holder
                val holder = nestedScrollView.getChildAt(0) as ViewGroup
                //Loop through all children of parent holder
                for (i in 0 until holder.childCount) {
                    //Pull the pagination recyclerview child
                    if (holder.getChildAt(i).id == recyclerView.id) {
                        paginatedRV = holder.getChildAt(i) as RecyclerView
                        break
                    }
                }
            }
            paginatedRV?.let {
                //Identify if recyclerview is scrolled to bottom
                Log.v("NestedPagination","${it.bottom - (nestedScrollView.height + nestedScrollView.scrollY)}")
                if (it.bottom - (nestedScrollView.height + nestedScrollView.scrollY) < 3500)
                    nextPageFunction()
            }
        }
    }
}