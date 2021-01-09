/*
 * Created by  Muhammad Sayed  on 1/9/21 9:37 AM
 * Copyright (c) JustClean. All rights reserved.
 */

package com.justclean.app.shared.bindingadapters

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager



@BindingAdapter(
    "setAdapter",
    "isHorizontal",
    "setHasFixedSize",
    "isGrid",
    "isStaggered",
    requireAll = false
)
fun RecyclerView.bindRecyclerViewAdapter(
    adapter: RecyclerView.Adapter<*>,
    isHorizontal: Boolean = false,
    setHasFixedSize: Boolean = true,
    isGrid: Boolean = false,
    isStaggered: Boolean = false

) {
    this.run {
        this.setHasFixedSize(setHasFixedSize)
        this.adapter = adapter
        this.layoutManager = when {
            isHorizontal -> LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL, false
            )
            isGrid -> {
                GridLayoutManager(
                    context,
                    2
                )
            }
            isStaggered -> {
                StaggeredGridLayoutManager(
                    2, LinearLayoutManager.VERTICAL

                )
            }
            else ->
                LinearLayoutManager(context)

        }
    }
}




