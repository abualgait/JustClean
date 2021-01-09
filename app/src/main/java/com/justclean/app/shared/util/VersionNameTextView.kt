/*
 * Created by  Muhammad Sayed  on 1/9/21 9:37 AM
 * Copyright (c) JustClean. All rights reserved.
 */
package com.justclean.app.shared.util

import android.content.Context
import android.content.pm.PackageManager
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView


class VersionNameTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    init {
        text = installedVersion
    }

    val installedVersion: CharSequence
        get() = try {
            val packageInfo =
                context!!.packageManager.getPackageInfo(context!!.packageName, 0)
            packageInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            ""
        }
}