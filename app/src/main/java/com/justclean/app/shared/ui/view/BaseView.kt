/*
 * Created by  Muhammad Sayed  on 1/9/21 9:37 AM
 * Copyright (c) JustClean. All rights reserved.
 */

package com.justclean.app.shared.ui.view


import android.content.Context
import androidx.databinding.ViewDataBinding
import com.justclean.app.shared.ui.activity.BaseActivity
import com.justclean.app.shared.vm.BaseViewModel

interface BaseView<V : BaseViewModel, B : ViewDataBinding> {

    val vm: V

    val layoutId: Int

    var binding: B

    fun baseViewModel(): BaseViewModel?

    fun activity(): BaseActivity<*, *>?

    fun context(): Context? {
        return activity()
    }


    fun showLoading() {}

    fun hideLoading() {}


}
