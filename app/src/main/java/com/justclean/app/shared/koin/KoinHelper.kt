/*
 * Created by  Muhammad Sayed  on 1/9/21 9:37 AM
 * Copyright (c) JustClean. All rights reserved.
 */

package com.justclean.app.shared.koin


import android.content.Context

import com.justclean.app.ui.navhostactivity.favourite.favouriteFragVmModule
import com.justclean.app.ui.navhostactivity.navHostActivityModule
import com.justclean.app.ui.navhostactivity.post.postFragVmModule
import com.justclean.app.ui.navhostactivity.postdetails.postDetailsFragVmModule

import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class KoinHelper {
    companion object {
        fun start(context: Context) {
            startKoin {
                androidContext(context)
                modules(
                    listOf(
                        appModule,
                        postFragVmModule,
                        navHostActivityModule,
                        favouriteFragVmModule,
                        postDetailsFragVmModule

                    )
                )
            }
        }
    }
}