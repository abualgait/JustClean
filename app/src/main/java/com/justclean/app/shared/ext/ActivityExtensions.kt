/*
 * Created by  Muhammad Sayed  on 1/9/21 9:37 AM
 * Copyright (c) JustClean. All rights reserved.
 */

package com.justclean.app.shared.ext

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.justclean.app.R


fun <T : ViewDataBinding> Activity.setDataBindingContentView(@LayoutRes res: Int): T {
    return DataBindingUtil.setContentView(this, res)
}

fun Activity?.addFragment(
    @IdRes id: Int = R.id.container,
    fragment: Fragment,
    tag: String? = null,
    addToBackStack: Boolean = true
) {
    val compatActivity = this as? AppCompatActivity ?: return
    compatActivity.supportFragmentManager.beginTransaction()
        .apply {
            add(id, fragment, tag)
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            if (addToBackStack) {
                addToBackStack(null)
            }
            commit()
        }
}

fun Activity?.replaceFragment(
    @IdRes id: Int = R.id.container,
    fragment: Fragment,
    tag: String? = null,
    bundle: Bundle? = null,
    addAnimation: Boolean = false,
    addToBackStack: Boolean = false
) {
    val compatActivity = this as? AppCompatActivity ?: return
    compatActivity.supportFragmentManager.beginTransaction()
        .apply {
            if (addAnimation)
                setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            fragment.apply {
                arguments = bundle
            }
            replace(id, fragment, tag)
            if (addToBackStack) {
                addToBackStack(null)
            }
            commit()
        }
}

fun Fragment?.replaceFragment(
    @IdRes id: Int = R.id.container,
    fragment: Fragment,
    tag: String? = null,
    addToBackStack: Boolean = false
) {
    val compatActivity = this?.activity as? AppCompatActivity ?: return
    this.childFragmentManager.beginTransaction()
        .apply {
            replace(id, fragment, tag)
            if (addToBackStack) {
                addToBackStack(null)
            }
            commit()
        }
}


fun Fragment.removeFragment() {
    val fragmentTransaction: FragmentTransaction = fragmentManager?.beginTransaction()!!
    fragmentTransaction.remove(this)
    fragmentTransaction.commit()
}


fun Activity.setupDialogStuff(
    isCancelable: Boolean = false,
    view: View,
    dialog: AlertDialog,
    callback: (() -> Unit)? = null
) {
    if (isDestroyed || isFinishing) {
        return
    }

    dialog.apply {
        setView(view)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setCanceledOnTouchOutside(isCancelable)
        show()
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
    callback?.invoke()
}
