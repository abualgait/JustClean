/*
 * Created by  Muhammad Sayed  on 1/9/21 9:37 AM
 * Copyright (c) JustClean. All rights reserved.
 */

package com.justclean.app.shared.util

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.children
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener
import com.justclean.app.R

class IndicatorBottomNavigationView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.bottomNavigationStyle
) : BottomNavigationView(context, attrs, defStyleAttr) {

    private val indicator: View = View(context).apply {
        layoutParams = LayoutParams(30, 5)
        background = ContextCompat.getDrawable(context, R.drawable.indicator_bottom_navigation)
        setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary))
    }

    init {
        addView(indicator)
    }

    var animateIndicator = true

    override fun setOnNavigationItemSelectedListener(listener: OnNavigationItemSelectedListener?) {
        OnNavigationItemSelectedListener { selectedItem ->

            menu.children
                .first { item ->
                    item.itemId == selectedItem.itemId
                }
                .itemId
                .let {
                    findViewById<View>(it)
                }
                .let { view ->
                    this.post {
                        indicator.layoutParams = LayoutParams(30, 5)

                        if (animateIndicator) {
                            indicator
                                .animate()
                                .x(view.x)
                                .start()
                        } else {
                            indicator.x = view.x
                        }

                        indicator.y = view.y
                    }
                }

            listener?.onNavigationItemSelected(selectedItem) ?: false
        }.let {
            super.setOnNavigationItemSelectedListener(it)
        }
    }
}