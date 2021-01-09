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
import com.justclean.app.databinding.ItemCommentBinding
import com.justclean.app.shared.data.model.posts.comments.CommentsResponseItem
import com.justclean.app.shared.viewholders.CommentViewHolder
import com.justclean.app.shared.viewholders.RecyclerViewHolder


class CommentsListAdapter(
    val context: Context,
    val callback: (item: CommentsResponseItem, view: View, position: Int) -> Unit
) : ListAdapter<CommentsResponseItem, RecyclerViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {

        return CommentViewHolder(
            context,
            ItemCommentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), callback
        )


    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) =
        holder.onBindView(getItem(position), position)


    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CommentsResponseItem>() {
            override fun areItemsTheSame(
                oldItem: CommentsResponseItem,
                newItem: CommentsResponseItem
            ): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: CommentsResponseItem,
                newItem: CommentsResponseItem
            ): Boolean =
                oldItem == newItem

        }
    }
}
