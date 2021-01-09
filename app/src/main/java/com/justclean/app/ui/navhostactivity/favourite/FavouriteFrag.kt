/*
 * Created by  Muhammad Sayed  on 1/9/21 9:37 AM
 * Copyright (c) JustClean. All rights reserved.
 */

package com.justclean.app.ui.navhostactivity.favourite

import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.justclean.app.R
import com.justclean.app.databinding.FragmentFavouriteBinding
import com.justclean.app.shared.adapters.FavouriteListAdapter
import com.justclean.app.shared.data.model.Status
import com.justclean.app.shared.data.model.favourite.FavouriteItem
import com.justclean.app.shared.ui.frag.BaseFrag
import org.koin.android.viewmodel.ext.android.viewModel

class FavouriteFrag : BaseFrag<FavouriteFragVm, FragmentFavouriteBinding>() {


    override val vm: FavouriteFragVm by viewModel()
    override var layoutId: Int = R.layout.fragment_favourite

    companion object {
        const val POST_ID = "postId"
        const val POST_TITLE = "postTitle"
        const val POST = "postItem"

    }

    private val favsListAdapter: FavouriteListAdapter by lazy {
        FavouriteListAdapter(activity()!!, ::onItemClick)
    }

    private fun onItemClick(item: FavouriteItem, view: View, position: Int, remove: Boolean) {
        if (remove) {
            vm.removeItem(item)
            return
        }
        val bundle = bundleOf(
            POST_ID to item.id,
            POST_TITLE to item.title,
            POST to FavouriteItem.map(item)
        )
        findNavController().navigate(
            R.id.action_postsScreen_to_postDetailsScreen,
            bundle
        )


    }

    override fun doOnViewCreated() {
        super.doOnViewCreated()

        binding.apply {
            viewmodel = vm
            adapter = favsListAdapter
        }
        vm.getFavItems()
        handleObservers()
    }

    private fun handleObservers() {

        vm.response.observe(viewLifecycleOwner, Observer { resource ->
            when (resource.status) {
                Status.COMPLETE -> hideLoading()
                Status.SUCCESS -> {

                    favsListAdapter.submitList(resource.data)
                }
                Status.ERROR -> {

                    showErrorMessage(resource.message)
                }
                Status.LOADING -> showLoading()
                Status.OFFLINE -> showOfflineMode()
            }
        })
    }


}


