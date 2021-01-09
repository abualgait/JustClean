/*
 * Created by  Muhammad Sayed  on 1/9/21 9:37 AM
 * Copyright (c) JustClean. All rights reserved.
 */

package com.justclean.app.ui.navhostactivity.postdetails

import android.view.View
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.justclean.app.R
import com.justclean.app.databinding.FragmentDetailsPostBinding
import com.justclean.app.shared.adapters.CommentsListAdapter
import com.justclean.app.shared.data.model.Status
import com.justclean.app.shared.data.model.posts.PostsResponseItem
import com.justclean.app.shared.data.model.posts.comments.CommentsResponseItem
import com.justclean.app.shared.ext.onClicked
import com.justclean.app.shared.ui.frag.BaseFrag
import com.justclean.app.shared.util.ThreadUtil
import kotlinx.android.synthetic.main.activity_nav_host.*
import org.koin.android.viewmodel.ext.android.viewModel

class PostDetailsFrag : BaseFrag<PostDetailsFragVm, FragmentDetailsPostBinding>() {


    override val vm: PostDetailsFragVm by viewModel()
    override var layoutId: Int = R.layout.fragment_details_post

    private val commentsListAdapter: CommentsListAdapter by lazy {
        CommentsListAdapter(activity()!!, ::onItemClick)
    }

    private fun onItemClick(item: CommentsResponseItem, view: View, position: Int) {

    }

    override fun doOnViewCreated() {
        super.doOnViewCreated()
        hasSwipeRefresh = true
        binding.apply {
            viewmodel = vm
            adapter = commentsListAdapter

        }
        if (arguments != null) {
            vm.getCommentsByPostId(PostDetailsFragArgs.fromBundle(requireArguments()).postId)
            vm.setData(PostDetailsFragArgs.fromBundle(requireArguments()).postItem)
        }
        handleObservers()

        binding.mainLayoutDisplay.addToFav.onClicked {
            if (arguments != null) {
                vm.addPostToFav(
                    PostsResponseItem.map(
                        PostDetailsFragArgs.fromBundle(
                            requireArguments()
                        ).postItem
                    )
                )
            }
        }
    }

    private fun handleObservers() {

        vm.response.observe(viewLifecycleOwner, Observer { resource ->
            when (resource.status) {
                Status.COMPLETE -> hideLoading()
                Status.SUCCESS -> commentsListAdapter.submitList(resource.data)
                Status.ERROR -> {
                    Snackbar.make(
                        requireActivity().parentView,
                        getString(R.string.internet_connection_has_been_lost),
                        5000
                    ).setAnchorView(binding.mainLayoutDisplay.addToFav).show()
                }
                Status.LOADING -> showLoading()
                Status.OFFLINE -> {
                }
            }
        })
    }


    override fun onSwipeRefresh() {
        super.onSwipeRefresh()
        if (arguments != null)
            vm.getCommentsByPostId(PostDetailsFragArgs.fromBundle(requireArguments()).postId)
    }

    override fun onRetryClicked() {
        super.onRetryClicked()
        if (arguments != null)
            vm.getCommentsByPostId(PostDetailsFragArgs.fromBundle(requireArguments()).postId)
    }

    override fun showLoading() {
        super.showLoading()
        ThreadUtil.runOnUiThread { binding.mainLayoutDisplay.mSwipeRefresh.isRefreshing = true }
    }

    override fun hideLoading() {
        ThreadUtil.runOnUiThread { binding.mainLayoutDisplay.mSwipeRefresh.isRefreshing = false }
    }


}


