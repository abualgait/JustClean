/*
 * Created by  Muhammad Sayed  on 1/9/21 9:37 AM
 * Copyright (c) JustClean. All rights reserved.
 */

package com.justclean.app.shared.ui.activity


import android.app.ProgressDialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.multidex.MultiDex
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.justclean.app.R
import com.justclean.app.shared.ext.onClicked
import com.justclean.app.shared.ui.view.BaseView
import com.justclean.app.shared.util.CrashlyticsUtil
import com.justclean.app.shared.util.FlashbarUtil
import com.justclean.app.shared.util.SharedPref
import com.justclean.app.shared.util.reportAndPrint
import com.justclean.app.shared.vm.BaseViewModel
import kotlinx.android.synthetic.main.app_loading_screen.*
import kotlinx.android.synthetic.main.app_no_internet_connection.*
import kotlinx.android.synthetic.main.app_no_result_found.*
import kotlinx.android.synthetic.main.app_server_error.*
import kotlinx.android.synthetic.main.fragment_posts.*


abstract class BaseActivity<VM : BaseViewModel, B : ViewDataBinding>
    : AppCompatActivity(), BaseView<VM, B> {


    override lateinit var binding: B

    lateinit var pref: SharedPref


    open var hasSwipeRefresh = false
    open var hasBackNavigation = false
    open var hasTransparentNavigation = false


    private var mProgressBar: RelativeLayout? = null
    private var mProgressDialog: ProgressDialog? = null
    private var mFrameLayout: FrameLayout? = null

    /*Recycler View Data*/
    protected var loadMore: Boolean = false
    protected var offset: Int? = 0
    protected var total: Int? = 0

    /*lifecycle methods*/
    protected open fun doOnCreate() {}
    protected open fun doOnCreate(savedInstanceState: Bundle?) {}

    protected open fun onSwipeRefresh() {}
    protected open fun onRetryClicked() {}


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupTransitionAnimation()
        try {

            binding = DataBindingUtil.setContentView(this, layoutId)
            binding.lifecycleOwner = this

            vm.view = this

            doOnCreate()
            doOnCreate(savedInstanceState)
            setupSwipeRefresh()

        } catch (e: Exception) {
            e.reportAndPrint()
            CrashlyticsUtil.logAndPrint(e)
        }

    }

    fun showErrorMessage(message: String?) {
        FlashbarUtil.show(activity = activity(), message = message!!)
        hideLoading()
    }

    private fun setupTransitionAnimation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

    }

    override fun onPause() {
        super.onPause()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        try {

        } catch (e: Exception) {
            //CrashlyticsUtil.logAndPrint(e)
        }

    }

    override fun context(): Context {
        return this
    }

    override fun activity(): BaseActivity<*, *> {
        return this
    }

    override fun baseViewModel(): BaseViewModel? {
        return vm
    }


    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(newBase)
        MultiDex.install(this)
    }

    private fun setupSwipeRefresh() {
        val mSwipeRefresh: SwipeRefreshLayout = findViewById(R.id.mSwipeRefresh)
            ?: throw IllegalStateException("The activity implements `HasSwipeRefresh` but no SwipeRefreshLayout found!")
        if (hasSwipeRefresh) {
            mSwipeRefresh.setOnRefreshListener { this.onSwipeRefresh() }
        } else {
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
