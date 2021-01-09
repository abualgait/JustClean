/*
 * Created by  Muhammad Sayed  on 1/9/21 9:37 AM
 * Copyright (c) JustClean. All rights reserved.
 */

package com.justclean.app.shared.viewholders

import android.content.Context
import android.view.View
import com.justclean.app.databinding.ItemFavBinding
import com.justclean.app.databinding.ItemPostBinding
import com.justclean.app.shared.data.model.favourite.FavouriteItem
import com.justclean.app.shared.data.model.posts.PostsResponseItem

import com.justclean.app.shared.ext.onClicked


class FavouriteViewHolder(
    val context: Context,
    private val binding: ItemFavBinding,
    val callback: (item: FavouriteItem, view: View, position: Int,remove:Boolean) -> Unit
) : RecyclerViewHolder(binding.root) {

    override fun onBindView(`object`: Any, position: Int) {
        val data = `object` as FavouriteItem
        binding.item = data
        binding.root.onClicked {
            callback(data, binding.root, position,false)
        }
        binding.buttonFavorite.onClicked {
            callback(data, binding.root, position,true)
        }
    }


}