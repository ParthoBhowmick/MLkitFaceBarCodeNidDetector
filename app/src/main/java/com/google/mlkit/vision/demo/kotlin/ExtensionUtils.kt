package com.google.mlkit.vision.demo.kotlin

import android.content.Context
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

/**
 * @author partho
 * @since 2/1/22
 */
object ExtensionUtils {

    fun Context.dpToPx(dp: Int): Int {
        return (dp * (resources.displayMetrics.densityDpi / 160f)).toInt()
    }

    internal fun Context.getCompactColor(@ColorRes colorId: Int): Int {
        return ContextCompat.getColor(this, colorId)
    }
}