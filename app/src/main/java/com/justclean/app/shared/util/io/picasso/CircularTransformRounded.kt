/*
 * Created by  Muhammad Sayed  on 1/9/21 9:37 AM
 * Copyright (c) JustClean. All rights reserved.
 */
package com.justclean.app.shared.util.io.picasso

import android.graphics.*
import androidx.annotation.ColorInt
import com.squareup.picasso.Transformation

class CircularTransformRounded(
    @ColorInt val borderColor: Int,
    @ColorInt val innerColor: Int,
    borderRaduis: Int = 5
) : Transformation {
    private val BORDER_COLOR = borderColor
    private val INNER_COLOR = innerColor
    private val BORDER_RADIUS = borderRaduis
    override fun transform(source: Bitmap): Bitmap {
        val size = Math.min(source.width, source.height)
        val x = (source.width - size) / 2
        val y = (source.height - size) / 2
        val squaredBitmap = Bitmap.createBitmap(source, x, y, size, size)
        if (squaredBitmap != source) {
            source.recycle()
        }
        val bitmap = Bitmap.createBitmap(size, size, source.config)
        val canvas = Canvas(bitmap)
        val paint = Paint()
        val shader =
            BitmapShader(squaredBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        paint.shader = shader
        paint.isAntiAlias = true

        val r = size / 2f

        // Prepare the background
        val paintBg = Paint()
        paintBg.color = BORDER_COLOR
        paintBg.isAntiAlias = true
        // Draw the background circle
        canvas.drawCircle(r, r, r, paintBg)

        // Prepare the inner background
        val paintInnerBg = Paint()
        paintInnerBg.color = INNER_COLOR
        paintInnerBg.isAntiAlias = true
        // Draw the background circle
        canvas.drawCircle(r, r, r - BORDER_RADIUS, paintInnerBg)

        // Draw the image smaller than the background so a little border will be seen
        canvas.drawCircle(r, r, r - BORDER_RADIUS, paint)
        squaredBitmap.recycle()
        return bitmap
    }

    override fun key(): String {
        return "circle"
    }
}