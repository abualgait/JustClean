/*
 * Created by  Muhammad Sayed  on 1/9/21 9:37 AM
 * Copyright (c) JustClean. All rights reserved.
 */

package com.justclean.app.shared.viewholders

import android.content.Context
import android.view.View
import com.justclean.app.databinding.ItemPostBinding
import com.justclean.app.shared.data.model.posts.PostsResponseItem

import com.justclean.app.shared.ext.onClicked


class PostViewHolder(
    val context: Context,
    private val binding: ItemPostBinding,
    val callback: (item: PostsResponseItem, view: View, position: Int) -> Unit
) : RecyclerViewHolder(binding.root) {

    override fun onBindView(`object`: Any, position: Int) {
        val data = `object` as PostsResponseItem
        binding.item = data
        binding.root.onClicked {
            callback(data, binding.root, position)
        }
    }


}