/*
 * Created by  Muhammad Sayed  on 1/9/21 9:37 AM
 * Copyright (c) JustClean. All rights reserved.
 */

package com.justclean.app.shared.ui.frag

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import com.justclean.app.shared.ui.activity.BaseActivity
import com.justclean.app.shared.ui.view.BaseView
import com.justclean.app.shared.util.CrashlyticsUtil
import com.justclean.app.shared.vm.BaseViewModel


abstract class BaseDialogFrag<VM : BaseViewModel, B : ViewDataBinding> : DialogFragment(),
    BaseView<VM, B> {
    final override lateinit var binding: B

    var isShown: Boolean = false
        protected set


    protected var isDismissed: Boolean = false

    private lateinit var activity: BaseActivity<*, *>

    protected open val isCanceledOnTouchOutside: Boolean
        get() = false

    private var onDismissListener: (() -> Unit)? = null


    protected open fun setupUi() {}
    protected open fun doOnViewCreated() {}
    protected open fun doOnCreateView(savedInstanceState: Bundle?) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            activity = getActivity() as BaseActivity<*, *>

        } catch (e: Exception) {
            CrashlyticsUtil.logAndPrint(e)
        }

        isShown = true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.setCanceledOnTouchOutside(isCanceledOnTouchOutside)
        try {
            setupUi()
            doOnViewCreated()
        } catch (e: Exception) {
            CrashlyticsUtil.logAndPrint(e)
        }

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


    protected fun transparentWindow(): Boolean {
        return true
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        val w = dialog.window
        // Hide title
        if (w != null) {
            w.requestFeature(Window.FEATURE_NO_TITLE)
            if (transparentWindow())
                w.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        return dialog
    }

    override fun activity(): BaseActivity<*, *> {
        return activity
    }


    override fun baseViewModel(): BaseViewModel? {
        return vm
    }


    fun onDismissListener(callback: () -> Unit): BaseDialogFrag<VM, B> {
        this.onDismissListener = callback
        return this
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        if (onDismissListener != null)
            onDismissListener!!.invoke()
    }

    override fun dismiss() {
        super.dismiss()
        isShown = false
        isDismissed = true
    }

    override fun getContext(): Context? {
        return activity
    }


}
