package com.google.mlkit.vision.demo.kotlin

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Rect
import android.graphics.RectF
import android.util.AttributeSet
import android.widget.LinearLayout
import com.google.mlkit.vision.demo.R
import com.google.mlkit.vision.demo.kotlin.ExtensionUtils.dpToPx
import com.google.mlkit.vision.demo.kotlin.ExtensionUtils.getCompactColor

internal open class RectangleOverlayView : LinearLayout {
    private var bitmap: Bitmap? = null
    private var mHoleWidth = 0
    private var mHoleHeight = 0
    private var boundingBox: Rect = Rect()
    private var errorView = false
    private val ERROR_STATE = 1
    private val DEFAULT_STATE = 0

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initView(context, attrs)
    }

    private fun initView(context: Context, attrs: AttributeSet?) {
        val typedArray: TypedArray =
            context.obtainStyledAttributes(attrs, R.styleable.RectangleOverlayViewPay)
        mHoleWidth =
            typedArray.getDimensionPixelOffset(R.styleable.RectangleOverlayViewPay_hole_width, 0)
        mHoleHeight =
            typedArray.getDimensionPixelOffset(R.styleable.RectangleOverlayViewPay_hole_height, 0)
        errorView = typedArray.getInt(
            R.styleable.RectangleOverlayViewPay_error_state,
            DEFAULT_STATE
        ) == ERROR_STATE
        typedArray.recycle()
    }


    override fun dispatchDraw(canvas: Canvas) {
        super.dispatchDraw(canvas)
        if (bitmap == null) {
            createWindowFrame()
        }
        canvas.drawBitmap(bitmap!!, 0f, 0f, null)
    }

    private fun createWindowFrame() {
        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val osCanvas = Canvas(bitmap!!)
        val outerRectangle = RectF(0F, 0f, width.toFloat(), height.toFloat())
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = context.getCompactColor(R.color.image_overlay_color_pay)
        osCanvas.drawRect(outerRectangle, paint)

        paint.color = Color.TRANSPARENT
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_OUT)
        val centerX = (width / 2).toFloat()
        val centerY = (height / 2).toFloat()
        val widthHalf = mHoleWidth / 2
        val heightHalf = mHoleHeight / 2

        val rect = Rect(
            (centerX - widthHalf).toInt(),
            (centerY - heightHalf).toInt(),
            (centerX + widthHalf).toInt(),
            (centerY + heightHalf).toInt()
        )
        osCanvas.drawRoundRect(
            RectF(rect),
            context.dpToPx(10).toFloat(),
            context.dpToPx(10).toFloat(),
            paint
        )

        paint.style = Paint.Style.STROKE
        paint.color = if (errorView) Color.RED else Color.WHITE
        paint.strokeWidth = context.dpToPx(3).toFloat()
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_OVER)
        osCanvas.drawRoundRect(
            RectF(rect),
            context.dpToPx(10).toFloat(),
            context.dpToPx(10).toFloat(),
            paint
        )
        this.boundingBox = rect
    }

    override fun isInEditMode(): Boolean {
        return true
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        bitmap = null
    }

    fun getBoundingBox(): Rect {
        return boundingBox
    }
}