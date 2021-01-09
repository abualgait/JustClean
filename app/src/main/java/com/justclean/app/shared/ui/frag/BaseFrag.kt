/*
 * Created by  Muhammad Sayed  on 1/9/21 9:37 AM
 * Copyright (c) JustClean. All rights reserved.
 */

package com.justclean.app.shared.ui.frag

import android.content.BroadcastReceiver
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.justclean.app.shared.ui.activity.BaseActivity
import com.justclean.app.shared.ui.view.BaseView
import com.justclean.app.shared.util.FlashbarUtil
import com.justclean.app.shared.vm.BaseViewModel
import kotlinx.android.synthetic.main.app_loading_screen.*
import kotlinx.android.synthetic.main.app_no_internet_connection.*
import kotlinx.android.synthetic.main.app_no_result_found.*
import kotlinx.android.synthetic.main.app_server_error.*
import kotlinx.android.synthetic.main.frag_main_posts.*
import kotlinx.android.synthetic.main.fragment_posts.*
import java.util.*

abstract class BaseFrag<VM : BaseViewModel, B : ViewDataBinding> : Fragment(), BaseView<VM, B> {

    final override lateinit var binding: B
    lateinit var view: BaseView<*, *>
    protected open fun setupUi() {}
    protected open fun setupFont() {}
    protected open fun doOnViewCreated() {}
    protected open fun doOnCreateView(savedInstanceState: Bundle?) {}
    protected fun doOnResume() {}

    open var hasBackNavigation = false
    open var hasSwipeRefresh = false


    /*Recycler View Data*/

    protected var mList: ArrayList<Any>? = null
    protected var bmList: List<Any>? = null

    protected var loadMore: Boolean = false
    protected var offset: Int? = 0
    protected var total: Int? = 0
    protected var last_page: Int? = 0


    /*Search passing text handling */
    open var mRegistrationBroadcastReceiver: BroadcastReceiver? = null
    open var text: String? = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            view = this
        } catch (e: Exception) {

        }

    }

    fun showErrorMessage(message: String?) {
        FlashbarUtil.show(activity = activity()!!, message = message!!)

        hideLoading()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        binding.lifecycleOwner = this
        try {
            doOnCreateView(savedInstanceState)

        } catch (e: Exception) {

        }
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            setupUi()
            setupFont()
            doOnViewCreated()
            setupSwipeRefresh()

        } catch (e: Exception) {

        }

    }

    /**
     * Designed to be overridden in Fragments that implement [HasSwipeRefresh]
     */
    protected open fun onSwipeRefresh() {}

    protected open fun onRetryClicked() {}

    override fun onResume() {
        super.onResume()
        try {
            doOnResume()
        } catch (e: Exception) {

        }
    }

    override fun activity(): BaseActivity<*, *>? {
        return activity as? BaseActivity<*, *>
    }


    override fun baseViewModel(): BaseViewModel? {
        return vm
    }


    private fun setupSwipeRefresh() {
        if (hasSwipeRefresh) {
            if (mSwipeRefresh == null)
                throw IllegalStateException("The fragment implements `HasSwipeRefresh` but no SwipeRefreshLayout found")
            mSwipeRefresh.setOnRefreshListener { this.onSwipeRefresh() }
        } else {
            if (mSwipeRefresh == null) return
            mSwipeRefresh.isEnabled = false
        }
    }

    fun showLoader() {
        mViewFlipper?.displayedChild = mViewFlipper!!.indexOfChild(relLoadingScreen)
    }

    fun showMainLayout() {
        mViewFlipper?.displayedChild = mViewFlipper!!.indexOfChild(main_layout_display)
    }

    fun showServerError() {
        mViewFlipper?.displayedChild = mViewFlipper!!.indexOfChild(linServerError)
    }

    fun showOfflineMode() {
        mViewFlipper?.displayedChild = mViewFlipper!!.indexOfChild(linOfflineScreen)
        btnRetry.setOnClickListener { this.onRetryClicked() }
    }

    fun showEmptyData() {
        mViewFlipper?.displayedChild = mViewFlipper!!.indexOfChild(linEmptyData)
    }


}