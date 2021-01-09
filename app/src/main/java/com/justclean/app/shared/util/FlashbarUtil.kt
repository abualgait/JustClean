/*
 * Created by  Muhammad Sayed  on 1/9/21 9:37 AM
 * Copyright (c) JustClean. All rights reserved.
 */

package com.justclean.app.shared.util

import android.app.Activity
import androidx.annotation.ColorRes
import com.andrognito.flashbar.Flashbar
import com.andrognito.flashbar.anim.FlashAnim
import com.justclean.app.R


object FlashbarUtil {

    fun show(
        message: String,
        @ColorRes color: Int = R.color.colorPrimary,
        activity: Activity
    ) {
        try {
            Flashbar.Builder(activity)
                .message(message)
                .gravity(Flashbar.Gravity.TOP)
                .duration(6000)
                .messageAppearance(R.style.TextStyleFlashBar)
                .backgroundColorRes(color)
                .dismissOnTapOutside()
                .enableSwipeToDismiss()
                .enterAnimation(
                    FlashAnim.with(activity)
                    .animateBar()
                    .duration(750)
                    .alpha()
                    .overshoot())
                .exitAnimation(FlashAnim.with(activity)
                    .animateBar()
                    .duration(400)
                    .alpha()
                    .overshoot())
                .build()
                .show()
        } catch (e: Exception) {
            e.printStackTrace()

        }

    }
}
