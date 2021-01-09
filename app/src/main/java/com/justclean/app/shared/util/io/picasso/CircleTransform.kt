/*
 * Created by  Muhammad Sayed  on 1/9/21 9:37 AM
 * Copyright (c) JustClean. All rights reserved.
 */

package com.justclean.app.shared.util.io.picasso

import android.content.Context
import android.graphics.*
import com.squareup.picasso.Transformation
import com.justclean.app.shared.util.io.BitmapUtil
import com.justclean.app.shared.util.reportAndPrint


class CircleTransform(val context: Context) : Transformation {

    private val BORDER_COLOR = Color.RED
    private val BORDER_RADIUS = 5

    override fun transform(source: Bitmap): Bitmap {
        try {

            val size = Math.min(source.width, source.height)

            val x = (source.width - size) / 2
            val y = (source.height - size) / 2

            val squaredBitmap = Bitmap.createBitmap(source, x, y, size, size)

            if (squaredBitmap == null) {
                source.recycle()
                return BitmapUtil.empty()
            }

            if (squaredBitmap != source) {
                source.recycle()
            }

            val bitmap = Bitmap.createBitmap(size, size, source.config)

            if (bitmap == null) {
                source.recycle()
                return BitmapUtil.empty()
            }

            val canvas = Canvas(bitmap)

            val paint = Paint()
            val shader = BitmapShader(
                squaredBitmap,
                Shader.TileMode.CLAMP,
                Shader.TileMode.CLAMP
            )
            paint.shader = shader
            paint.isAntiAlias = true


            val r = size / 2f

            // Prepare the background
            val paintBg = Paint()
            paintBg.color = BORDER_COLOR
            paintBg.isAntiAlias = true

            // Draw the background circle
            canvas.drawCircle(r, r, r, paint)

            // Draw the image smaller than the background so a little border will be seen
            canvas.drawCircle(r, r, r - BORDER_RADIUS, paint)

            squaredBitmap.recycle()

            return bitmap
        } catch (e: Exception) {
            e.reportAndPrint()
            source.recycle()
            return BitmapUtil.empty()
        }

    }

    override fun key(): String {
        return "circle"
    }
}
