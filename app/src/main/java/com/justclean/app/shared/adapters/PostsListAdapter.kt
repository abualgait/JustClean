/*
 * Created by  Muhammad Sayed  on 1/9/21 9:37 AM
 * Copyright (c) JustClean. All rights reserved.
 */

package com.justclean.app.shared.adapters


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.justclean.app.databinding.ItemPostBinding
import com.justclean.app.shared.data.model.posts.PostsResponseItem
import com.justclean.app.shared.viewholders.PostViewHolder
import com.justclean.app.shared.viewholders.RecyclerViewHolder


class PostsListAdapter(
    val context: Context,
    val callback: (item: PostsResponseItem, view: View, position: Int) -> Unit
) : ListAdapter<PostsResponseItem, RecyclerViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {

        return PostViewHolder(
            context,
            ItemPostBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), callback
        )


    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) =
        holder.onBindView(getItem(position), position)


    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PostsResponseItem>() {
            override fun areItemsTheSame(
                oldItem: PostsResponseItem,
                newItem: PostsResponseItem
            ): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: PostsResponseItem,
                newItem: PostsResponseItem
            ): Boolean =
                oldItem == newItem

        }
    }
}
