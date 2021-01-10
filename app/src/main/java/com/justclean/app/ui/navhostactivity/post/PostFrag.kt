/*
 * Created by  Muhammad Sayed  on 1/9/21 9:37 AM
 * Copyright (c) JustClean. All rights reserved.
 */

package com.justclean.app.ui.navhostactivity.post

import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.justclean.app.R
import com.justclean.app.databinding.FragmentPostsBinding
import com.justclean.app.shared.adapters.PostsListAdapter
import com.justclean.app.shared.data.model.Status
import com.justclean.app.shared.data.model.posts.PostsResponseItem
import com.justclean.app.shared.ui.frag.BaseFrag
import com.justclean.app.shared.util.ThreadUtil
import kotlinx.android.synthetic.main.activity_nav_host.*
import kotlinx.android.synthetic.main.activity_nav_host.view.*
import org.koin.android.viewmodel.ext.android.viewModel

class PostFrag : BaseFrag<PostFragVm, FragmentPostsBinding>() {


    override val vm: PostFragVm by viewModel()
    override var layoutId: Int = R.layout.fragment_posts

    companion object {
        const val POST_ID = "postId"
        const val POST_TITLE = "postTitle"
        const val POST = "postItem"
    }

    private val postsListAdapter: PostsListAdapter by lazy {
        PostsListAdapter(activity()!!, ::onItemClick)
    }

    private fun onItemClick(item: PostsResponseItem, view: View, position: Int) {
        val bundle = bundleOf(
            POST_ID to item.id,
            POST_TITLE to item.title,
            POST to item
        )

        findNavController().navigate(
            R.id.action_postsScreen_to_postDetailsScreen,
            bundle
        )
    }

    override fun doOnViewCreated() {
        super.doOnViewCreated()
        hasSwipeRefresh = true
        binding.apply {
            viewmodel = vm
            adapter = postsListAdapter
        }
        vm.getPosts()
        handleObservers()
    }

    private fun handleObservers() {
        view.showLoading()

        vm.response.observe(viewLifecycleOwner, Observer { resource ->
            when (resource.status) {
                Status.COMPLETE -> hideLoading()
                Status.SUCCESS -> {
                    view.hideLoading()
                    postsListAdapter.submitList(resource.data)
                }
                Status.ERROR -> {
                    view.hideLoading()
                    Snackbar.make(
                        requireActivity().parentView,
                        getString(R.string.internet_connection_has_been_lost),
                        5000
                    ).setAnchorView(requireActivity().parentView.bottom_nav).show()
                }
                Status.LOADING -> showLoading()
                Status.OFFLINE -> {
                }
            }
        })
    }


    override fun onSwipeRefresh() {
        super.onSwipeRefresh()
        vm.getPosts()
    }

    override fun onRetryClicked() {
        super.onRetryClicked()
        vm.getPosts()
    }

    override fun showLoading() {
        super.showLoading()
        showLoader()
        ThreadUtil.runOnUiThread { binding.mainLayoutDisplay.mSwipeRefresh.isRefreshing = true }
    }

    override fun hideLoading() {
        showMainLayout()
        ThreadUtil.runOnUiThread { binding.mainLayoutDisplay.mSwipeRefresh.isRefreshing = false }
    }


}


