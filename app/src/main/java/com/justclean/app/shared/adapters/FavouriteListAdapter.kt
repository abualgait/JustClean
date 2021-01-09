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
import com.justclean.app.databinding.ItemFavBinding
import com.justclean.app.databinding.ItemPostBinding
import com.justclean.app.shared.data.model.favourite.FavouriteItem
import com.justclean.app.shared.data.model.posts.PostsResponseItem
import com.justclean.app.shared.viewholders.FavouriteViewHolder
import com.justclean.app.shared.viewholders.PostViewHolder
import com.justclean.app.shared.viewholders.RecyclerViewHolder


class FavouriteListAdapter(
    val context: Context,
    val callback: (item: FavouriteItem, view: View, position: Int,remove:Boolean) -> Unit
) : ListAdapter<FavouriteItem, RecyclerViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {

        return FavouriteViewHolder(
            context,
            ItemFavBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), callback
        )


    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) =
        holder.onBindView(getItem(position), position)


    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FavouriteItem>() {
            override fun areItemsTheSame(
                oldItem: FavouriteItem,
                newItem: FavouriteItem
            ): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: FavouriteItem,
                newItem: FavouriteItem
            ): Boolean =
                oldItem == newItem

        }
    }
}
