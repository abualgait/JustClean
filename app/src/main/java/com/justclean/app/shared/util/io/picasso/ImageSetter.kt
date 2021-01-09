/*
 * Created by  Muhammad Sayed  on 1/9/21 9:37 AM
 * Copyright (c) JustClean. All rights reserved.
 */

package com.justclean.app.shared.util.io.picasso


import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import com.justclean.app.R
import com.justclean.app.shared.util.io.app.MyApp.Companion.applicationContext
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

object ImageSetter {

    fun loadImageWithProgressBar(
        mUrl: String?,
        mImageView: ImageView?,
        mProgressBar: ProgressBar,
        @DrawableRes placeholder: Int = R.drawable.placeholder
    ) {
        if (!TextUtils.isEmpty(mUrl)) {
            Picasso.get().load(mUrl).fit().centerInside().error(placeholder)
                .into(mImageView, object :
                    Callback {
                    override fun onError(e: Exception?) {
                        mImageView?.setImageResource(placeholder)
                        mProgressBar.visibility = View.GONE
                    }

                    override fun onSuccess() {
                        mProgressBar.visibility = View.GONE
                    }
                })
        } else {
            mImageView?.setImageResource(placeholder)
        }

    }

    fun loadImage(mUrl: String?, mImageView: ImageView?) {
        if (!TextUtils.isEmpty(mUrl)) {
            Picasso.get()
                .load(mUrl)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .fit().centerInside()

                .into(mImageView)
        } else {
            mImageView?.setImageResource(R.drawable.placeholder)
        }
    }

    fun round(
        mUrl: String?,
        mImageView: ImageView?,
        @ColorInt borderColor: Int = applicationContext().resources.getColor(R.color.justclean),
        @ColorInt innerColor: Int = applicationContext().resources.getColor(R.color.white),
        @ColorInt borderRaduis: Int = 5
    ) {

        if (!TextUtils.isEmpty(mUrl)) {
            Picasso.get()
                .load(mUrl)
                .placeholder(R.drawable.rounded_placeholder)
                .error(R.drawable.rounded_placeholder)
                .fit().centerInside()
                .transform(CircularTransformRounded(borderColor, innerColor, borderRaduis))
                .into(mImageView)
        } else {
            mImageView?.setImageResource(R.drawable.rounded_placeholder)
        }
    }


}
